
package com.basic.app.sms;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.basic.app.entity.SmsH;
import com.basic.app.entity.SmsM;
import com.basic.app.exception.ErrorCode;
import com.basic.app.exception.customException.BusinessException;
import com.basic.app.repository.SmsHRepository;
import com.basic.app.repository.SmsMRepository;
import com.basic.app.util.Status;

import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;

@Transactional
@Component
public class SmsProvider {

  @Value("${spring.sms.provider}")
  private String PROVIDER;

  @Value("${spring.sms.from-phone}")
  private String from;

  @Autowired
  private SmsMRepository smsMRepository;

  @Autowired
  private SmsHRepository smsHRepository;

  private DefaultMessageService messageService;

  public SmsProvider(
      @Value("${spring.sms.api-key}") String API_KEY,
      @Value("${spring.sms.api-secret-key}") String API_SECRET_KEY,
      @Value("${spring.sms.api-domain}") String API_DOMAIN) {

    this.messageService = NurigoApp.INSTANCE.initialize(API_KEY, API_SECRET_KEY, API_DOMAIN);

  }

  public void sendSms(String smsId, String to, Map<String, Object> params) {

    SmsM smsM = smsMRepository.findById(smsId)
        .filter(entity -> entity.getSts().equals(Status.POSITIVE))
        .orElseThrow(() -> new BusinessException(ErrorCode.SMS_NOT_REGISTERD, smsId));

    String text = smsM.getText();
    boolean success = false;
    String errorMsg = null;

    try {

      // 변수 치환
      for (Map.Entry<String, Object> entry : params.entrySet()) {
        text = text.replace("{" + entry.getKey() + "}", entry.getValue().toString());
      }

      Message message = new Message();
      message.setFrom(from); // 발신자 번호
      message.setTo(to); // 수신자 번호
      message.setText(text); // 메시지 내용

      SingleMessageSentResponse response = messageService.sendOne(new SingleMessageSendingRequest(message));

      success = response.getStatusCode().equals("2000"); // 2000 = OK

    } catch (Exception e) {
      success = false;
      errorMsg = """
          %s
          %s
          %s
          """
          .formatted(ErrorCode.SMS_SEND_ERROR.getMessage(), e.getMessage(), getStackTraceAsString(e));

    } finally {
      // SMS 발송 이력 저장
      SmsH smsH = SmsH.builder()
          .smsId(smsId)
          .fromPhone(from)
          .toPhone(to)
          .text(text)
          .success(success ? "Y" : "N")
          .errorMsg(success ? null : errorMsg)
          .build();

      smsHRepository.save(smsH);
    }

  }

  private String getStackTraceAsString(Exception e) {
    StringWriter sw = new StringWriter();
    e.printStackTrace(new PrintWriter(sw));
    return sw.toString();
  }

}
