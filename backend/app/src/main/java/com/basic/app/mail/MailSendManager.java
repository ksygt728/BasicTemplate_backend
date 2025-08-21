/**
 * @파일명   : MailSendManager.java
 * @설명     : 실제 메일 발송 클래스
 *      [1. 메일을 서비스단에서 직접 실시간으로 보내는 경우] : 서비스단에서 비즈니스처리와 동시에 실시간으로 메일을 보내야 하는 경우 사용. MailH에 이력을 남김 ==> 메일을 보내고 이력저장
 *      [2. 메일을 배치로 보내는 경우] : 서비스단에서는 MailH에 이력만 남겨놓고 quartz 배치에서 주기적으로 메일을 전송하는 경우 사용. MailH에 이력을 남김 ==> 이력을 남기고 보냄
 *      [3. 메일 이력을 직접 실행해서 전송하는 경우] : 전송또는 미전송 된 메일을 재전송하고 싶은경우 사용(관리자). MailH에 이력을 남김 ==> 이력을 남기고 보냄
 *      [4. 메일을 이력에 쌓아놓고 배치주기로 메일 전송] : 2번을 수행하기 위해 메일을 insert함. 이력을 남기고 보내는 로직이기 떄문에, 직접 에러를 return ==> 이력을 남기고 보내는데 그 자체가 오류라면 의미가 없어서 직접 return
 * @작성자   : 김승연
 * @작성일   : 2025.08.19
 * @변경이력 :
 *   2025.08.19     김승연       최초 생성
 */
package com.basic.app.mail;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.basic.app.entity.MailH;
import com.basic.app.entity.MailM;
import com.basic.app.exception.ErrorCode;
import com.basic.app.exception.customException.BusinessException;
import com.basic.app.exception.customException.NotFoundException;
import com.basic.app.exception.customException.SystemErrorException;
import com.basic.app.repository.MailHRepository;
import com.basic.app.repository.MailMRepository;
import com.basic.app.service.specialService.TemplateRenderer;
import com.basic.app.util.Status;

import freemarker.core.InvalidReferenceException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Transactional
@Component
public class MailSendManager {

  @Autowired
  private JavaMailSender mailSender;

  @Autowired
  private TemplateRenderer templateRenderer;

  @Autowired
  private MailMRepository mailMRepository;

  @Autowired
  private MailHRepository mailHRepository;

  @Value("${spring.mail.username}")
  private String senderMailAddress;

  @Value("${spring.mail.alias}")
  private String senderMailAlias;

  @Value("${spring.mail.max-mail-queue}")
  private int maxQueueSize;

  /*
   * [테스트 케이스]
   * CASE1 템플릿 오기입 : InvalidReferenceException / 출력결과 / 에러로그 / 메일로그1개 남는지
   * CASE2 형식 Invalid : MailSendException / 출력결과 / 에러로그 / 메일로그1개 남는지
   * CASE3 템플릿 오기입 AND 배치 N실행: InvalidReferenceException / 출력결과 /에러로그 / 메일로그1개
   * CASE4 형식 Invalid AND 배치 N실행 : 오류가 발생하는 시점 이후 배치에 대해서는 메일을 전송하지 않음
   * CASE5 메일은 등록되었으나 실제 메일이 없는경우 : 해당 오류는 서버에서 잡을 수 없음 Gmail 보낸편지함에서 확인 가능
   */
  /* [1. 메일을 서비스단에서 직접 실시간으로 보내는 경우] */
  public void sendMail(String mailId, List<String> toEmails, Object params) throws Exception {

    /*
     * [step 1. 템플릿 조회]
     * 메일에 대한 정보는 하드코딩을 하고 관리자화면에서 직접 조작할 수 있음 / NotFoundExpection이아니라
     * BuisinessException으로 처리
     */
    MailM mailM = mailMRepository.findById(mailId)
        .filter(mail -> mail.getSts().equals(Status.POSITIVE))
        .orElseThrow(() -> new BusinessException(ErrorCode.MAIL_NOT_REGISTERD, mailId));

    String sccuess = "W"; // 메일 전송 성공 여부(기본값 : W - 대기)
    String errorMsg = null;
    String title = mailM.getTitle(); // 제목
    String content = mailM.getContent();
    List<String> toEmailsBatch = null; // 배치 전송을 위한 리스트

    /* [step 2. FreeMarker 파라미터 렌더링] */
    try {
      content = templateRenderer.render(content, params);
    } catch (InvalidReferenceException e) { // 예외처리 : FreeMarker 템플릿 렌더링 실패
      sccuess = "N";
      content = mailM.getContent(); // 작성사유 : 렌더링예외시 해당 catch문에 들어오는데 렌더링된 값(오류)를 저장하게되면 동일오류가 발생함 -> 실패시 원본 내용으로 설정
      errorMsg = """
          %s
          %s
          %s
          """
          .formatted(ErrorCode.MAIL_TEMPLATE_NOT_VALID.getMessage(), e.getMessage(), getStackTraceAsString(e));

      ;

    } finally {
      if (sccuess == "N") { // 실패시에만 로그를 남김 -> 성공은 뒤에서만
        /* 메일 발송 이력 저장 */
        MailH mailH = MailH.builder()
            .mailId(mailId)
            .fromAddr(senderMailAddress)
            .toAddr(String.join(",", toEmails))
            .title(title)
            .content(content)
            .success(sccuess)
            .errorMsg(errorMsg)
            .build();

        mailHRepository.save(mailH);
      }

    }

    /* step 3. [배치 사이즈로 잘라서 메일 전송 및 이력저장] */
    if (!toEmails.isEmpty() && sccuess.equals("W")) {

      for (int i = 0; i < toEmails.size(); i += maxQueueSize) {
        try {
          toEmailsBatch = toEmails.subList(i, Math.min(i + maxQueueSize, toEmails.size()));

          MimeMessage mimeMessage = mailSender.createMimeMessage();
          MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
          helper.setFrom(new InternetAddress(senderMailAddress, senderMailAlias)); // ADMIN 이름 표시
          helper.setTo(toEmailsBatch.toArray(new String[0]));
          helper.setSubject(title);
          helper.setText(content, true); // HTML

          mailSender.send(mimeMessage);

          errorMsg = null; // 성공시 실패 사유 초기화
          sccuess = "Y"; // 전송 성공

        } catch (MailSendException e2) {
          sccuess = "N"; // 전송 실패
          errorMsg = """
              %s
              %s
              %s
              """
              .formatted(ErrorCode.MAIL_FORMAT_INVALID.getMessage(), e2.getMessage(), getStackTraceAsString(e2));

        } catch (Exception e) {

          sccuess = "N"; // 전송 실패
          errorMsg = e.getMessage() + "\n" + getStackTraceAsString(e);

        } finally {
          /* step 4. 메일 발송 이력 저장 */
          MailH mailH = MailH.builder()
              .mailId(mailId)
              .fromAddr(senderMailAddress)
              .toAddr(String.join(",", toEmailsBatch))
              .title(title)
              .content(content)
              .success(sccuess)
              .errorMsg(errorMsg)
              .build();

          mailHRepository.save(mailH);

        }

      }
    }

  }

  /* 2. 메일을 배치로 보내는 경우 */
  public void sendMailToBatch() throws Exception {

    /* [step 1. 템플릿 조회] */

    List<MailH> mailHList = mailHRepository.findTop100ByStsAndSuccessOrderByCreateDateAsc(Status.POSITIVE, "W");

    /* step 2. [조회한 데이터를 메일로 전송 및 이력저장] */
    mailHList.forEach(mailH -> {
      try {

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        helper.setFrom(new InternetAddress(senderMailAddress, senderMailAlias)); // ADMIN 이름 표시
        helper.setTo(mailH.getToAddr().split(",")); // 쉼표로 분리된 이메일 주소를 배열로 변환
        helper.setSubject(mailH.getTitle());
        helper.setText(mailH.getContent(), true); // HTML

        mailSender.send(mimeMessage);

        mailH.setErrorMsg(null);// 성공시 실패 사유 초기화
        mailH.setSuccess("Y"); // 전송 성공

      } catch (MailSendException e2) {
        mailH.setSuccess("N"); // 전송 실패
        mailH.setErrorMsg("""
            %s
            %s
            %s
            """
            .formatted(ErrorCode.MAIL_FORMAT_INVALID.getMessage(), e2.getMessage(), getStackTraceAsString(e2)));
      } catch (Exception e) {

        mailH.setSuccess("N");
        mailH.setErrorMsg(e.getMessage() + "\n" + getStackTraceAsString(e));

      } finally {
        /* step 3. 메일 발송 이력 저장 */

        mailHRepository.save(mailH);

      }
    });

  }

  /* 3. 메일 이력을 직접 실행해서 전송하는 경우 */
  public void sendMailByLogId(String logId) throws Exception {

    /*
     * [step 1. 템플릿 조회]
     */
    MailH mailH = mailHRepository.findById(logId)
        .filter(entity -> entity.getSts().equals(Status.POSITIVE))
        .orElseThrow(() -> new NotFoundException(ErrorCode.OBJECT_NOT_FOUND, logId));

    /* step 2. [조회한 데이터를 메일로 전송 및 이력저장] */
    try {

      MimeMessage mimeMessage = mailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
      helper.setFrom(new InternetAddress(senderMailAddress, senderMailAlias)); // ADMIN 이름 표시
      helper.setTo(mailH.getToAddr().split(",")); // 쉼표로 분리된 이메일 주소를 배열로 변환
      helper.setSubject(mailH.getTitle());
      helper.setText(mailH.getContent(), true); // HTML

      mailSender.send(mimeMessage);

      mailH.setErrorMsg(null);// 성공시 실패 사유 초기화
      mailH.setSuccess("Y"); // 전송 성공

    } catch (MailSendException e2) {
      mailH.setSuccess("N"); // 전송 실패
      mailH.setErrorMsg("""
          %s
          %s
          %s
          """
          .formatted(ErrorCode.MAIL_FORMAT_INVALID.getMessage(), e2.getMessage(), getStackTraceAsString(e2)));
    } catch (Exception e) {

      mailH.setSuccess("N");
      mailH.setErrorMsg(e.getMessage() + "\n" + getStackTraceAsString(e));

    } finally {
      /* step 3. 메일 발송 이력 저장 */

      mailHRepository.save(mailH);

    }

  }

  /* [4. 메일을 이력에 쌓아놓고 배치주기로 메일 전송] */
  public void insertMailHistory(String mailId, List<String> toEmails, Object params) throws Exception {

    /*
     * [step 1. 템플릿 조회]
     * 메일에 대한 정보는 하드코딩을 하고 관리자화면에서 직접 조작할 수 있음 / NotFoundExpection이아니라
     * BuisinessException으로 처리
     */
    MailM mailM = mailMRepository.findById(mailId)
        .filter(mail -> mail.getSts().equals(Status.POSITIVE))
        .orElseThrow(() -> new BusinessException(ErrorCode.MAIL_NOT_REGISTERD, mailId));

    String title = mailM.getTitle(); // 제목
    String content = mailM.getContent();
    List<String> toEmailsBatch = null; // 배치 전송을 위한 리스트

    /* [step 2. FreeMarker 파라미터 렌더링] */
    try {
      content = templateRenderer.render(content, params);
    } catch (InvalidReferenceException e) { // 예외처리 : FreeMarker 템플릿 렌더링 실패
      throw new BusinessException(ErrorCode.MAIL_TEMPLATE_NOT_VALID, mailId);
    }

    /* step 3. [배치 사이즈로 잘라서 메일 전송 및 이력저장] */
    for (int i = 0; i < toEmails.size(); i += maxQueueSize) {
      try {
        toEmailsBatch = toEmails.subList(i, Math.min(i + maxQueueSize, toEmails.size()));

        MailH mailH = MailH.builder()
            .mailId(mailId)
            .fromAddr(senderMailAddress)
            .toAddr(String.join(",", toEmailsBatch))
            .title(title)
            .content(content)
            .success("W") // 전송 대기 상태로 저장
            .errorMsg(null)
            .build();

        mailHRepository.save(mailH);

      } catch (Exception e) {
        throw new SystemErrorException(e, ErrorCode.MAIL_SEND_ERROR, mailId);

      }

    }
  }

  private String getStackTraceAsString(Exception e) {
    StringWriter sw = new StringWriter();
    e.printStackTrace(new PrintWriter(sw));
    return sw.toString();
  }
}
