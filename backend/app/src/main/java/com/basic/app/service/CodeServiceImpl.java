package com.basic.app.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
import com.basic.app.dto.responseDto.specialDto.CodeSearchFormResDto;
import com.basic.app.dto.responseDto.specialDto.ComCodeAttributesAndValues;
import com.basic.app.dto.responseDto.specialDto.ComCodeInfo;
import com.basic.app.dto.responseDto.specialDto.GroupCodeInfo;
import com.basic.app.repository.CodeDRepository;
import com.basic.app.repository.CodeMRepository;
import com.basic.app.repository.CodeTRepository;
import com.basic.app.repository.jooqRepository.CodeJooqRepository;
import com.basic.app.service.interfaces.CodeService;

import lombok.extern.log4j.Log4j2;

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
            .filter(info -> info.getDtlCd().equals(row.getDtlCd()))
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
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findByGroupCodeForAdmin'");
  }

  @Override
  public Map<String, Object> insertGroupCodeForAdmin(ComCodeMReqDto comCodeM) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'insertGroupCodeForAdmin'");
  }

  @Override
  public Map<String, Object> updateGroupCodeForAdmin(ComCodeMReqDto comCodeM) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'updateGroupCodeForAdmin'");
  }

  @Override
  public Map<String, Object> deleteGroupCodeForAdmin(String grpCd) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'deleteGroupCodeForAdmin'");
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
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'insertAttrCodeForAdmin'");
  }

  @Override
  public Map<String, Object> updateAttrCodeForAdmin(ComCodeTReqDto comCodeT) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'updateAttrCodeForAdmin'");
  }

  @Override
  public Map<String, Object> deleteAttrCodeForAdmin(String attrCd) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'deleteAttrCodeForAdmin'");
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
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'insertDetailCodeForAdmin'");
  }

  @Override
  public Map<String, Object> updateDetailCodeForAdmin(ComCodeDReqDto comCodeD) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'updateDetailCodeForAdmin'");
  }

  @Override
  public Map<String, Object> deleteDetailCodeForAdmin(String dtlCd) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'deleteDetailCodeForAdmin'");
  }

}
