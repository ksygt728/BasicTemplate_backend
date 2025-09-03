package com.basic.app.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.basic.app.api.ModelMapperUtils;
import com.basic.app.api.PageResponse;
import com.basic.app.auth.CustomUserDetails;
import com.basic.app.dto.requestDto.BbsCommentReqDto;
import com.basic.app.dto.requestDto.BbsReqDto;
import com.basic.app.dto.responseDto.BbsCommentResDto;
import com.basic.app.dto.responseDto.BbsResDto;
import com.basic.app.entity.Bbs;
import com.basic.app.entity.BbsComment;
import com.basic.app.entity.User;
import com.basic.app.exception.ErrorCode;
import com.basic.app.exception.customException.BusinessException;
import com.basic.app.exception.customException.NotFoundException;
import com.basic.app.repository.BbsCommentRepository;
import com.basic.app.repository.BbsRepository;
import com.basic.app.repository.UserRepository;
import com.basic.app.repository.jooqRepository.BbsJooqRepository;
import com.basic.app.service.interfaces.BbsService;
import com.basic.app.util.Status;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@Transactional
public class BbsServiceImpl implements BbsService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private BbsRepository bbsRepository;

  @Autowired
  private BbsJooqRepository bbsJooqRepository;

  @Autowired
  private BbsCommentRepository bbsCommentRepository;

  @Override
  public Map<String, Object> findAllBbsForAdmin(BbsReqDto bbsReqDto, Pageable pageable) {

    Map<String, Object> data = new HashMap<>();

    // 1. 조건에 맞는 인터페이스 조회
    Page<BbsResDto> bbsDtoList = bbsJooqRepository.findAllBbsWithConditions(bbsReqDto,
        pageable);

    // 2. Page -> PageResponse 변환(이미 DTO로 변환된 상태이므로 추가 변환은 필요 없음)
    PageResponse<BbsResDto> pagedBbsDtoList = ModelMapperUtils.map(bbsDtoList);

    // 3. 결과를 Map에 담아 반환
    data.put("data", pagedBbsDtoList);

    return data;
  }

  @Override
  public Map<String, Object> findByBbsForAdmin(String bbsId) {
    Map<String, Object> data = new HashMap<>();

    // 1. ID로 인터페이스 조회(만약 인터페이스가 존재하지 않으면 null 반환)
    // 2. Entity -> DTO 변환
    BbsResDto bbsResDto = bbsRepository.findById(bbsId)
        .filter(entity -> entity.getSts().equals(Status.POSITIVE))
        .map(entity -> entity.toDto(BbsResDto.class))
        .orElseThrow(() -> new NotFoundException(ErrorCode.OBJECT_NOT_FOUND));

    // 3. 결과를 Map에 담아 반환
    data.put("data", bbsResDto);
    return data;

  }

  @Override
  public Map<String, Object> insertBbsForAdmin(BbsReqDto bbsReqDto, CustomUserDetails user) {

    Map<String, Object> data = new HashMap<>();

    // 1. DTO -> Entity 변환
    Bbs bbsEntity = bbsReqDto.toEntity(Bbs.class);

    // 2. ID로 기존 엔티티 조회 (bbsId는 자동 채번)
    // bbsRepository.findById(bbsEntity.getBbsId())
    // .filter(entity -> entity.getSts().equals(Status.POSITIVE))
    // .ifPresent(entity -> {
    // throw new BusinessException(ErrorCode.OBJECT_IS_EXISTED);
    // });

    User userEntity = userRepository.findById(user.getUsername())
        .filter(entity -> entity.getSts().equals(Status.POSITIVE))
        .orElseThrow(() -> new NotFoundException(ErrorCode.OBJECT_NOT_FOUND));

    // 3. DTO -> Entity 후 데이터 저장
    bbsEntity.setWritor(userEntity); // 작성자 설정

    Bbs savedBbsEntity = bbsRepository.save(bbsEntity);

    // 4. Entity -> DTO 변환
    // 5. 결과를 Map에 담아 반환
    data.put("data", savedBbsEntity.toDto(BbsResDto.class));
    return data;

  }

  @Override
  public Map<String, Object> updateBbsForAdmin(BbsReqDto bbsReqDto) {

    Map<String, Object> data = new HashMap<>();

    // 1. DTO -> Entity 변환
    Bbs bbsEntity = bbsReqDto.toEntity(Bbs.class);

    // 2. ID로 기존 엔티티 조회

    User newUserEntity = userRepository.findById(bbsReqDto.getWritor())
        .filter(entity -> entity.getSts().equals(Status.POSITIVE))
        .orElseThrow(() -> new NotFoundException(ErrorCode.OBJECT_NOT_FOUND));

    Bbs currentBbsEntity = bbsRepository.findById(bbsEntity.getBbsId())
        .filter(entity -> entity.getSts().equals(Status.POSITIVE))
        .orElseThrow(() -> new NotFoundException(ErrorCode.OBJECT_NOT_FOUND));

    String currentUserId = currentBbsEntity.getWritor().getUserId();
    String newUserId = newUserEntity.getUserId();
    if (currentUserId != newUserId) {
      throw new BusinessException(ErrorCode.BBS_WRITOR_NOT_MATCH);
    }

    bbsEntity.setWritor(newUserEntity); // 작성자 설정(변경 불가)
    bbsEntity.setWriteDate(currentBbsEntity.getWriteDate()); // 작성일 설정(변경 불가)

    // 3. 엔티티 수정 & 저장(자동)
    Bbs savedBbsEntity = bbsRepository.save(bbsEntity);

    // 4. Entity -> DTO 변환
    // 5. 결과를 Map에 담아 반환
    data.put("data", savedBbsEntity.toDto(BbsResDto.class));

    return data;

  }

  @Override
  public Map<String, Object> deleteBbsForAdmin(String bbsId) {
    Map<String, Object> data = new HashMap<>();

    // 1. ID로 인터페이스 조회(만약 인터페이스가 존재하지 않으면 NotFoundException 발생)
    Bbs bbsEntity = bbsRepository.findById(bbsId)
        .filter(entity -> entity.getSts().equals(Status.POSITIVE))
        .orElseThrow(() -> new NotFoundException(ErrorCode.OBJECT_NOT_FOUND));

    // 2. 논리 삭제 처리
    // 관련된 댓글들도 모두 논리 삭제 처리
    List<BbsComment> bbsCommentList = bbsCommentRepository.findAllByBbsIdAndSts(bbsId, Status.POSITIVE);

    bbsCommentList.forEach(entity -> {
      entity.setSts(Status.NAGATIVE);
    });

    bbsEntity.setSts(Status.NAGATIVE);

    // 3. 결과를 Map에 담아 반환
    data.put("data", "success");
    return data;

  }

  @Override
  public Map<String, Object> findAllBbsCommentForAdmin(String bbsId, Pageable pageable) {

    Map<String, Object> data = new HashMap<>();

    // 1. 조건에 맞는 인터페이스 조회
    Page<BbsComment> bbsCommentDtoList = bbsCommentRepository.findAllByBbsIdAndSts(bbsId, Status.POSITIVE, pageable);

    // 2. Page -> PageResponse 변환(이미 DTO로 변환된 상태이므로 추가 변환은 필요 없음)
    PageResponse<BbsCommentResDto> pagedBbsCommentDtoList = ModelMapperUtils.map(bbsCommentDtoList,
        BbsCommentResDto.class);

    // 3. 결과를 Map에 담아 반환
    data.put("data", pagedBbsCommentDtoList);

    return data;
  }

  @Override
  public Map<String, Object> findByBbsCommentForAdmin(String commentId) {
    Map<String, Object> data = new HashMap<>();

    // 1. ID로 인터페이스 조회(만약 인터페이스가 존재하지 않으면 null 반환)
    // 2. Entity -> DTO 변환
    BbsCommentResDto bbsCommentResDto = bbsCommentRepository.findById(commentId)
        .filter(entity -> entity.getSts().equals(Status.POSITIVE))
        .map(entity -> entity.toDto(BbsCommentResDto.class))
        .orElseThrow(() -> new NotFoundException(ErrorCode.OBJECT_NOT_FOUND));

    // 3. 결과를 Map에 담아 반환
    data.put("data", bbsCommentResDto);
    return data;

  }

  @Override
  public Map<String, Object> insertBbsCommentForAdmin(BbsCommentReqDto bbsCommentReqDto, CustomUserDetails user) {

    Map<String, Object> data = new HashMap<>();

    // 1. DTO -> Entity 변환(왜 안되지 ?)
    BbsComment bbsCommentEntity = bbsCommentReqDto.toEntity(BbsComment.class);

    // 2. ID로 기존 엔티티 조회
    Bbs bbsEntity = bbsRepository.findById(bbsCommentReqDto.getBbsId())
        .filter(entity -> entity.getSts().equals(Status.POSITIVE))
        .orElseThrow(() -> new NotFoundException(ErrorCode.OBJECT_NOT_FOUND));

    User userEntity = userRepository.findById(user.getUsername())
        .filter(entity -> entity.getSts().equals(Status.POSITIVE))
        .orElseThrow(() -> new NotFoundException(ErrorCode.OBJECT_NOT_FOUND));

    // 3. DTO -> Entity 후 데이터 저장

    bbsCommentEntity.setBbs(bbsEntity); // 게시판 설정
    bbsCommentEntity.setWritor(userEntity); // 작성자 설정

    BbsComment savedBbsCommentEntity = bbsCommentRepository.save(bbsCommentEntity);

    // 4. Entity -> DTO 변환
    // 5. 결과를 Map에 담아 반환
    data.put("data", savedBbsCommentEntity.toDto(BbsCommentResDto.class));
    return data;

  }

  @Override
  public Map<String, Object> updateBbsCommentForAdmin(BbsCommentReqDto bbsCommentReqDto, CustomUserDetails user) {

    Map<String, Object> data = new HashMap<>();

    // 1. DTO -> Entity 변환
    BbsComment bbsCommentEntity = bbsCommentReqDto.toEntity(BbsComment.class);

    // 2. ID로 기존 엔티티 조회
    bbsCommentRepository.findById(bbsCommentEntity.getCommentId())
        .filter(entity -> entity.getSts().equals(Status.POSITIVE))
        .orElseThrow(() -> new NotFoundException(ErrorCode.OBJECT_NOT_FOUND));

    Bbs bbsEntity = bbsRepository.findById(bbsCommentReqDto.getBbsId())
        .filter(entity -> entity.getSts().equals(Status.POSITIVE))
        .orElseThrow(() -> new NotFoundException(ErrorCode.OBJECT_NOT_FOUND));

    User newUserEntity = userRepository.findById(bbsCommentReqDto.getWritor())
        .filter(entity -> entity.getSts().equals(Status.POSITIVE))
        .orElseThrow(() -> new NotFoundException(ErrorCode.OBJECT_NOT_FOUND));

    if (!bbsCommentReqDto.getWritor().equals(user.getUsername())) {
      throw new BusinessException(ErrorCode.BBS_COMMENT_WRITOR_NOT_MATCH);
    }

    // 3. 엔티티 수정 & 저장(자동)
    bbsCommentEntity.setBbs(bbsEntity); // 게시판 설정(변경 불가)
    bbsCommentEntity.setWritor(newUserEntity); // 작성자 설정(변경 불가)
    BbsComment savedBbsCommentEntity = bbsCommentRepository.save(bbsCommentEntity);

    // 4. Entity -> DTO 변환
    // 5. 결과를 Map에 담아 반환
    data.put("data", savedBbsCommentEntity.toDto(BbsCommentResDto.class));

    return data;
  }

  @Override
  public Map<String, Object> deleteBbsCommentForAdmin(String commentId) {
    Map<String, Object> data = new HashMap<>();

    // 1. ID로 인터페이스 조회(만약 인터페이스가 존재하지 않으면 NotFoundException 발생)
    BbsComment bbsCommentEntity = bbsCommentRepository.findById(commentId)
        .filter(entity -> entity.getSts().equals(Status.POSITIVE))
        .orElseThrow(() -> new NotFoundException(ErrorCode.OBJECT_NOT_FOUND));

    // 2. 논리 삭제 처리
    bbsCommentEntity.setSts(Status.NAGATIVE);

    // 3. 결과를 Map에 담아 반환
    data.put("data", "success");
    return data;
  }

}
