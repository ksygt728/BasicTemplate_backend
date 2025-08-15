package com.basic.app.service.interfaces;

import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.basic.app.dto.requestDto.ComCodeDReqDto;
import com.basic.app.dto.requestDto.ComCodeMReqDto;
import com.basic.app.dto.requestDto.ComCodeTReqDto;
import com.basic.app.dto.requestDto.specialDto.CodeSearchFormReqDto;

public interface CodeService {

  Map<String, Object> findAllGroupCodeForAdmin();

  Map<String, Object> findByGroupCodeForAdmin(String grpCd);

  Map<String, Object> insertGroupCodeForAdmin(ComCodeMReqDto comCodeM);

  Map<String, Object> updateGroupCodeForAdmin(ComCodeMReqDto comCodeM);

  Map<String, Object> deleteGroupCodeForAdmin(String grpCd);

  Map<String, Object> findAllAttrCodeForAdmin();

  Map<String, Object> findByAttrCodeForAdmin(String attrCd);

  Map<String, Object> insertAttrCodeForAdmin(ComCodeTReqDto comCodeT);

  Map<String, Object> updateAttrCodeForAdmin(ComCodeTReqDto comCodeT);

  Map<String, Object> deleteAttrCodeForAdmin(String attrCd);

  Map<String, Object> findAllDetailCodeForAdmin();

  Map<String, Object> findByDetailCodeForAdmin(String dtlCd);

  Map<String, Object> insertDetailCodeForAdmin(ComCodeDReqDto comCodeD);

  Map<String, Object> updateDetailCodeForAdmin(ComCodeDReqDto comCodeD);

  Map<String, Object> deleteDetailCodeForAdmin(String dtlCd);

  Map<String, Object> findAllCodeMWithConditions(CodeSearchFormReqDto reqDto, Pageable pageable);

  Map<String, Object> findAllCodeRowMWithConditions(CodeSearchFormReqDto reqDto, Pageable pageable);

}
