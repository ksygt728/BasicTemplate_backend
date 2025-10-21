package com.basic.app.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.basic.app.api.ModelMapperUtils;
import com.basic.app.api.PageResponse;
import com.basic.app.dto.requestDto.ComCodeDReqDto;
import com.basic.app.dto.requestDto.ComCodeMReqDto;
import com.basic.app.dto.requestDto.ComCodeTReqDto;
import com.basic.app.dto.requestDto.specialDto.CodeSearchFormReqDto;
import com.basic.app.dto.responseDto.ComCodeDResDto;
import com.basic.app.dto.responseDto.ComCodeMResDto;
import com.basic.app.dto.responseDto.ComCodeTResDto;
import com.basic.app.dto.responseDto.specialDto.CodeSearchFormResDto;
import com.basic.app.dto.responseDto.specialDto.ComCodeAttributesAndValues;
import com.basic.app.dto.responseDto.specialDto.ComCodeInfo;
import com.basic.app.dto.responseDto.specialDto.GroupCodeInfo;
import com.basic.app.entity.ComCodeD;
import com.basic.app.entity.ComCodeM;
import com.basic.app.entity.ComCodeT;
import com.basic.app.entity.compositeKey.ComCodeDId;
import com.basic.app.entity.compositeKey.ComCodeTId;
import com.basic.app.exception.ErrorCode;
import com.basic.app.exception.customException.BusinessException;
import com.basic.app.exception.customException.NotFoundException;
import com.basic.app.repository.CodeDRepository;
import com.basic.app.repository.CodeMRepository;
import com.basic.app.repository.CodeTRepository;
import com.basic.app.repository.jooqRepository.CodeJooqRepository;
import com.basic.app.service.interfaces.CodeService;
import com.basic.app.util.Status;

import lombok.extern.log4j.Log4j2;

/**
 * @파일명 : CodeServiceImpl.java
 * @설명 : 공통코드 관련 서비스 구현체
 * @작성자 : 김승연
 * @작성일 : 2025.09.05
 * @변경이력 :
 *       2025.09.05 김승연 최초 생성
 */
@Log4j2
@Transactional
@Service
public class CodeServiceImpl implements CodeService {

  @Autowired
  private CodeJooqRepository codeJooqRepository;

  @Autowired
  private CodeMRepository codeMRepository;

  @Autowired
  private CodeTRepository codeTRepository;

  @Autowired
  private CodeDRepository codeDRepository;

  @Override
  public Map<String, Object> findAllCodeMWithConditions(CodeSearchFormReqDto codeSearchFormReqDto, Pageable pageable) {

    Map<String, Object> data = new HashMap<>();

    // 1. 조건에 맞는 인터페이스 조회
    Page<CodeSearchFormResDto> codeDtoList = codeJooqRepository.findAllCodeMWithConditions(
        codeSearchFormReqDto,
        pageable);

    // 2. Page -> PageResponse 변환(이미 DTO로 변환된 상태이므로 추가 변환은 필요 없음)
    PageResponse<CodeSearchFormResDto> pagedCodeDtoList = ModelMapperUtils.map(codeDtoList);

    // 3. 결과를 Map에 담아 반환
    data.put("data", pagedCodeDtoList);

    return data;

  }

  @Override
  public Map<String, Object> findAllCodeRowMWithConditions(CodeSearchFormReqDto codeSearchFormReqDto,
      Pageable pageable) {

    Map<String, Object> data = new HashMap<>();

    // 1. 조건에 맞는 인터페이스 조회
    Page<CodeSearchFormResDto> codeDtoList = codeJooqRepository.findAllCodeMWithConditions(
        codeSearchFormReqDto,
        pageable);

    // 2. Page에서 List 꺼내기
    List<CodeSearchFormResDto> codeRows = codeDtoList.getContent();

    // 3. 상세코드(DTL_CD) 기준 그룹화
    Map<String, GroupCodeInfo> pivotMap = new LinkedHashMap<>();
    for (CodeSearchFormResDto row : codeRows) {

      GroupCodeInfo group = pivotMap.computeIfAbsent(row.getGrpCd(), k -> {

        GroupCodeInfo groupMap = GroupCodeInfo.builder()
            .grpCdType(row.getGrpCdType())
            .grpCd(row.getGrpCd())
            .grpNm(row.getGrpNm())
            .build();

        return groupMap;
      });

      // 행 추가
      List<ComCodeInfo> comCodeList = group.getComCodeInfo();

      ComCodeInfo comCodeInfo = null;

      if (row.getDtlCd() != null) {
        comCodeInfo = comCodeList.stream()
            .filter(info -> Objects.equals(info.getDtlCd(), row.getDtlCd()))
            .findFirst()
            .orElse(null);
      }

      if (comCodeInfo == null) {
        comCodeInfo = ComCodeInfo.builder()
            .dtlCd(row.getDtlCd())
            .useYn(row.getUseYn())
            .dtlOrderNum(row.getCodeDOrderNum())
            .build();

        comCodeList.add(comCodeInfo);
      }

      // 열 추가
      ComCodeAttributesAndValues comCodeAttributes = ComCodeAttributesAndValues.builder()
          .attrCd(row.getAttrCd())
          .attrNm(row.getAttrNm())
          .dtlNm(row.getDtlNm())
          .attrOrderNum(row.getCodeTOrderNum())
          .build();

      comCodeInfo.getCodeAttributes().add(comCodeAttributes);

      // Map<String, ComCodeInfo> comCodeList =
      // group.getComCodeInfo().stream().collect(Collectors.toMap(
      // ComCodeInfo::getDtlCd,
      // comCodeInfo -> comCodeInfo,
      // (existing, replacement) -> existing, // 중복된 키는 기존 값을 유지
      // LinkedHashMap::new)); // 순서를 유지하기 위해 LinkedHashMap 사용
      // ComCodeInfo codeRow = comCodeList.computeIfAbsent(
      // row.getDtlCd() != null ? row.getDtlCd()
      // : "UNKNOWN_" +
      // System.currentTimeMillis(),
      // k -> {
      // ComCodeInfo attrMap = ComCodeInfo.builder()
      // .dtlCd(row.getDtlCd())
      // .useYn(row.getUseYn())
      // .dtlOrderNum(row.getCodeDOrderNum())
      // .build();

      // return attrMap;
      // });
      // codeRow.getCodeAttributes().add(
      // ComCodeAttributesAndValues.builder()
      // .attrCd(row.getAttrCd())
      // .attrNm(row.getAttrNm())
      // .dtlNm(row.getDtlNm())
      // .build());

      // comCodeList.put(codeRow.getDtlCd(), codeRow);
      // group.setComCodeInfo(new ArrayList<>(comCodeList.values()));
      // }
    }

    // pivotMap의 values를 List<GroupCodeInfo> 형태로 받을 수 있습니다.
    List<GroupCodeInfo> result = new ArrayList<>(pivotMap.values());

    // 5. 기존 Page 객체 유지하면서 finalList를 content로 덮어쓰기
    Page<GroupCodeInfo> pivotPage = new PageImpl<>(result);

    // 6. PageResponse로 변환 (ModelMapperUtils 사용)
    PageResponse<GroupCodeInfo> pagedCodeDtoList = ModelMapperUtils.map(pivotPage);

    // 7. 결과를 Map에 담아 반환
    data.put("data", pagedCodeDtoList);

    return data;

  }

  @Override
  public Map<String, Object> findAllGroupCodeForAdmin() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findAllGroupCodeForAdmin'");
  }

  @Override
  public Map<String, Object> findByGroupCodeForAdmin(String grpCd) {

    Map<String, Object> data = new HashMap<>();

    // 1. ID로 인터페이스 조회(만약 인터페이스가 존재하지 않으면 null 반환)
    // 2. Entity -> DTO 변환

    ComCodeMResDto comCodeMResDto = codeMRepository.findById(grpCd)
        .filter(entity -> entity.getSts().equals(Status.POSITIVE))
        .map(entity -> entity.toDto(entity))
        .orElseThrow(() -> new NotFoundException(ErrorCode.OBJECT_NOT_FOUND));

    // 3. 결과를 Map에 담아 반환
    data.put("data", comCodeMResDto);
    return data;

  }

  @Override
  public Map<String, Object> insertGroupCodeForAdmin(ComCodeMReqDto comCodeM) {

    Map<String, Object> data = new HashMap<>();

    // 1. DTO -> Entity 변환
    ComCodeM codeMEntity = comCodeM.toEntity(ComCodeM.class);

    // 2. ID로 기존 엔티티 조회
    codeMRepository.findById(codeMEntity.getGrpCd())
        .filter(entity -> entity.getSts().equals(Status.POSITIVE))
        .ifPresent(entity -> {
          throw new BusinessException(ErrorCode.OBJECT_IS_EXISTED);
        });

    // 3. DTO -> Entity 후 데이터 저장
    ComCodeM savedCodeMEntity = codeMRepository.save(codeMEntity);

    // 4. Entity -> DTO 변환
    // 5. 결과를 Map에 담아 반환
    data.put("data", savedCodeMEntity.toDto(ComCodeMResDto.class));
    return data;

  }

  @Override
  public Map<String, Object> updateGroupCodeForAdmin(ComCodeMReqDto comCodeM) {

    Map<String, Object> data = new HashMap<>();

    // 1. DTO -> Entity 변환
    ComCodeM codeMEntity = comCodeM.toEntity(ComCodeM.class);

    // 2. ID로 기존 엔티티 조회
    codeMRepository.findById(codeMEntity.getGrpCd())
        .filter(entity -> entity.getSts().equals(Status.POSITIVE))
        .orElseThrow(() -> new NotFoundException(ErrorCode.OBJECT_NOT_FOUND));

    // 3. 엔티티 수정 & 저장(자동)
    ComCodeM savedCodeMEntity = codeMRepository.save(codeMEntity);

    // 4. Entity -> DTO 변환
    // 5. 결과를 Map에 담아 반환
    data.put("data", savedCodeMEntity.toDto(ComCodeMResDto.class));

    return data;
  }

  @Override
  public Map<String, Object> deleteGroupCodeForAdmin(String grpCd) {
    Map<String, Object> data = new HashMap<>();

    // 1. ID로 인터페이스 조회(만약 인터페이스가 존재하지 않으면 NotFoundException 발생)
    ComCodeM codeMEntity = codeMRepository.findById(grpCd)
        .filter(entity -> entity.getSts().equals(Status.POSITIVE))
        .orElseThrow(() -> new NotFoundException(ErrorCode.OBJECT_NOT_FOUND));

    // 2. 상태를 'D'로 변경하여 삭제 처리(자동 save)
    // 그뤂코드에 해당하는 속성코드와 상세코드도 함께 삭제처리
    codeDRepository.findByGrpCd(grpCd)
        .stream().forEach(entity -> {
          entity.setSts(Status.NAGATIVE);
        });

    codeTRepository.findByGrpCd(grpCd)
        .stream().forEach(entity -> {
          entity.setSts(Status.NAGATIVE);
        });

    // 그뤂코드 삭제처리
    codeMEntity.setSts(Status.NAGATIVE);

    // 3. 결과를 Map에 담아 반환
    data.put("data", "success");
    return data;
  }

  @Override
  public Map<String, Object> findAllAttrCodeForAdmin() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findAllAttrCodeForAdmin'");
  }

  @Override
  public Map<String, Object> findByAttrCodeForAdmin(String attrCd) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findByAttrCodeForAdmin'");
  }

  @Override
  public Map<String, Object> insertAttrCodeForAdmin(ComCodeTReqDto comCodeT) {

    Map<String, Object> data = new HashMap<>();

    // 1. DTO -> Entity 변환
    ComCodeT codeTEntity = comCodeT.toEntity(comCodeT);

    // 2. ID로 기존 엔티티 조회
    // 그룹코드가 존재하는지 확인
    String grpCd = codeTEntity.getComCodeTId().getGrpCd(); // 그뤂코드 Key
    ComCodeTId comCodeTId = codeTEntity.getComCodeTId(); // 속성코드 Key

    ComCodeM codeMEntity = codeMRepository.findById(grpCd)
        .filter(entity -> entity.getSts().equals(Status.POSITIVE))
        .orElseThrow(() -> new NotFoundException(ErrorCode.OBJECT_NOT_FOUND));

    // 속성코드가 존재하는지 않는지 확인
    codeTRepository.findById(comCodeTId)
        .filter(entity -> entity.getSts().equals(Status.POSITIVE))
        .ifPresent(entity -> {
          throw new BusinessException(ErrorCode.OBJECT_IS_EXISTED);
        });

    // 3. DTO -> Entity 후 데이터 저장
    // 연관관계 설정
    codeTEntity.setComCodeM(codeMEntity);

    ComCodeT savedCodeTEntity = codeTRepository.save(codeTEntity);

    // 4. Entity -> DTO 변환
    // 5. 결과를 Map에 담아 반환
    data.put("data", savedCodeTEntity.toDto(savedCodeTEntity));
    return data;

  }

  @Override
  public Map<String, Object> updateAttrCodeForAdmin(ComCodeTReqDto comCodeT) {

    Map<String, Object> data = new HashMap<>();

    // 1. DTO -> Entity 변환
    ComCodeT codeTEntity = comCodeT.toEntity(comCodeT);

    // 2. ID로 기존 엔티티 조회
    // 그룹코드가 존재하는지 확인
    String grpCd = codeTEntity.getComCodeTId().getGrpCd(); // 그뤂코드 Key
    ComCodeTId comCodeTId = codeTEntity.getComCodeTId(); // 속성코드 Key

    codeMRepository.findById(grpCd)
        .filter(entity -> entity.getSts().equals(Status.POSITIVE))
        .orElseThrow(() -> new NotFoundException(ErrorCode.OBJECT_NOT_FOUND));

    // 속성코드가 존재하는지 확인
    codeTRepository.findById(comCodeTId)
        .filter(entity -> entity.getSts().equals(Status.POSITIVE))
        .orElseThrow(() -> new NotFoundException(ErrorCode.OBJECT_NOT_FOUND));

    // 3. 엔티티 수정 & 저장(자동)
    ComCodeT savedCodeTEntity = codeTRepository.save(codeTEntity);

    // 4. Entity -> DTO 변환
    // 5. 결과를 Map에 담아 반환
    data.put("data", savedCodeTEntity.toDto(savedCodeTEntity));

    return data;

  }

  @Override
  public Map<String, Object> deleteAttrCodeForAdmin(String grpCd, String attrCd) {
    Map<String, Object> data = new HashMap<>();

    // 1. ID로 인터페이스 조회(만약 인터페이스가 존재하지 않으면 NotFoundException 발생)
    // 복합키 생성
    ComCodeTId comCodeTId = ComCodeTId.builder()
        .grpCd(grpCd)
        .attrCd(attrCd)
        .build();

    ComCodeT codeTEntity = codeTRepository.findById(comCodeTId)
        .filter(entity -> entity.getSts().equals(Status.POSITIVE))
        .orElseThrow(() -> new NotFoundException(ErrorCode.OBJECT_NOT_FOUND));

    // 2. 상태를 'D'로 변경하여 삭제 처리(자동 save)
    // 속성코드에 해당하는 상세코드도 함께 삭제처리
    codeDRepository.findByComCodeTId(comCodeTId)
        .stream().forEach(entity -> {
          entity.setSts(Status.NAGATIVE);
        });

    // 속성코드 삭제처리
    codeTEntity.setSts(Status.NAGATIVE);

    // 3. 결과를 Map에 담아 반환
    data.put("data", "success");
    return data;

  }

  @Override
  public Map<String, Object> findAllDetailCodeForAdmin() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findAllDetailCodeForAdmin'");
  }

  @Override
  public Map<String, Object> findByDetailCodeForAdmin(String dtlCd) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findByDetailCodeForAdmin'");
  }

  @Override
  public Map<String, Object> insertDetailCodeForAdmin(ComCodeDReqDto comCodeD) {

    Map<String, Object> data = new HashMap<>();

    // 1. DTO -> Entity 변환
    ComCodeD codeDEntity = comCodeD.toEntity(comCodeD);

    // 2. ID로 기존 엔티티 조회
    String grpCd = codeDEntity.getComCodeDId().getComCodeTId().getGrpCd(); // 그뤂코드 Key
    ComCodeTId comCodeTId = codeDEntity.getComCodeDId().getComCodeTId(); // 속성코드 Key
    ComCodeDId comCodeDId = codeDEntity.getComCodeDId(); // 상세코드 Key

    // 그룹코드가 존재하는지 확인
    codeMRepository.findById(grpCd)
        .filter(entity -> entity.getSts().equals(Status.POSITIVE))
        .orElseThrow(() -> new NotFoundException(ErrorCode.OBJECT_NOT_FOUND));

    // 속성코드가 존재하는지 확인
    ComCodeT comCodeT = codeTRepository.findById(comCodeTId)
        .filter(entity -> entity.getSts().equals(Status.POSITIVE))
        .orElseThrow(() -> new NotFoundException(ErrorCode.OBJECT_NOT_FOUND));

    // 상세코드가 존재하는지 않는지 확인
    codeDRepository.findById(comCodeDId)
        .filter(entity -> entity.getSts().equals(Status.POSITIVE))
        .ifPresent(entity -> {
          throw new BusinessException(ErrorCode.OBJECT_IS_EXISTED);
        });

    // 3. DTO -> Entity 후 데이터 저장
    // 연관관계 설정
    codeDEntity.setComCodeT(comCodeT);

    ComCodeD savedCodeDEntity = codeDRepository.save(codeDEntity);

    // 4. Entity -> DTO 변환
    // 5. 결과를 Map에 담아 반환
    data.put("data", savedCodeDEntity.toDto(savedCodeDEntity));
    return data;

  }

  @Override
  public Map<String, Object> updateDetailCodeForAdmin(ComCodeDReqDto comCodeD) {

    Map<String, Object> data = new HashMap<>();

    // 1. DTO -> Entity 변환
    ComCodeD codeDEntity = comCodeD.toEntity(comCodeD);

    // 2. ID로 기존 엔티티 조회
    String grpCd = codeDEntity.getComCodeDId().getComCodeTId().getGrpCd(); // 그뤂코드 Key
    ComCodeTId comCodeTId = codeDEntity.getComCodeDId().getComCodeTId(); // 속성코드 Key
    ComCodeDId comCodeDId = codeDEntity.getComCodeDId(); // 상세코드 Key

    // 그룹코드가 존재하는지 확인
    codeMRepository.findById(grpCd)
        .filter(entity -> entity.getSts().equals(Status.POSITIVE))
        .orElseThrow(() -> new NotFoundException(ErrorCode.OBJECT_NOT_FOUND));

    // 속성코드가 존재하는지 확인
    codeTRepository.findById(comCodeTId)
        .filter(entity -> entity.getSts().equals(Status.POSITIVE))
        .orElseThrow(() -> new NotFoundException(ErrorCode.OBJECT_NOT_FOUND));

    // 상세코드가 존재하는지 확인(다른 Row에서 업데이트 되기 떄문에)
    // codeDRepository.findById(comCodeDId)
    // .filter(entity -> entity.getSts().equals(Status.POSITIVE))
    // .orElseThrow(() -> new NotFoundException(ErrorCode.OBJECT_NOT_FOUND));

    // 3. 엔티티 수정 & 저장(자동)
    ComCodeD savedCodeDEntity = codeDRepository.save(codeDEntity);

    // 4. Entity -> DTO 변환
    // 5. 결과를 Map에 담아 반환
    data.put("data", savedCodeDEntity.toDto(savedCodeDEntity));

    return data;
  }

  @Override
  public Map<String, Object> deleteDetailCodeForAdmin(String grpCd, String attrCd, String dtlCd) {
    Map<String, Object> data = new HashMap<>();

    // 1. ID로 인터페이스 조회(만약 인터페이스가 존재하지 않으면 NotFoundException 발생)
    // 복합키 생성
    ComCodeTId comCodeTId = ComCodeTId.builder()
        .grpCd(grpCd)
        .attrCd(attrCd)
        .build();

    ComCodeDId comCodeDId = ComCodeDId.builder()
        .comCodeTId(comCodeTId)
        .dtlCd(dtlCd)
        .build();

    ComCodeD codeDEntity = codeDRepository.findById(comCodeDId)
        .filter(entity -> entity.getSts().equals(Status.POSITIVE))
        .orElseThrow(() -> new NotFoundException(ErrorCode.OBJECT_NOT_FOUND));

    // 2. 상태를 'D'로 변경하여 삭제 처리(자동 save)
    codeDEntity.setSts(Status.NAGATIVE);

    // 3. 결과를 Map에 담아 반환
    data.put("data", "success");
    return data;

  }

}
