
package com.basic.app.service.interfaces;

import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.basic.app.dto.requestDto.ComCodeDReqDto;
import com.basic.app.dto.requestDto.ComCodeMReqDto;
import com.basic.app.dto.requestDto.ComCodeTReqDto;
import com.basic.app.dto.requestDto.specialDto.CodeSearchFormReqDto;

/**
 * @파일명 : CodeService.java
 * @설명 : 공통코드 관련 서비스 인터페이스
 * @작성자 : 김승연
 * @작성일 : 2025.08.6
 * @변경이력 :
 *       2025.08.6 김승연 최초 생성
 */

public interface CodeService {

  /**
   * @기능 : 관리자용 그룹코드 전체 목록 조회
   * @return 그룹코드 목록 정보가 담긴 Map
   */
  Map<String, Object> findAllGroupCodeForAdmin();

  /**
   * @기능 : 관리자용 특정 그룹코드 상세 조회
   * @param grpCd 그룹코드
   * @return 그룹코드 상세 정보가 담긴 Map
   */
  Map<String, Object> findByGroupCodeForAdmin(String grpCd);

  /**
   * @기능 : 관리자용 그룹코드 등록
   * @param comCodeM 그룹코드 등록 요청 DTO
   * @return 등록 결과 정보가 담긴 Map
   */
  Map<String, Object> insertGroupCodeForAdmin(ComCodeMReqDto comCodeM);

  /**
   * @기능 : 관리자용 그룹코드 수정
   * @param comCodeM 그룹코드 수정 요청 DTO
   * @return 수정 결과 정보가 담긴 Map
   */
  Map<String, Object> updateGroupCodeForAdmin(ComCodeMReqDto comCodeM);

  /**
   * @기능 : 관리자용 그룹코드 삭제
   * @param grpCd 그룹코드
   * @return 삭제 결과 정보가 담긴 Map
   */
  Map<String, Object> deleteGroupCodeForAdmin(String grpCd);

  /**
   * @기능 : 관리자용 속성코드 전체 목록 조회
   * @return 속성코드 목록 정보가 담긴 Map
   */
  Map<String, Object> findAllAttrCodeForAdmin();

  /**
   * @기능 : 관리자용 특정 속성코드 상세 조회
   * @param attrCd 속성코드
   * @return 속성코드 상세 정보가 담긴 Map
   */
  Map<String, Object> findByAttrCodeForAdmin(String attrCd);

  /**
   * @기능 : 관리자용 속성코드 등록
   * @param comCodeT 속성코드 등록 요청 DTO
   * @return 등록 결과 정보가 담긴 Map
   */
  Map<String, Object> insertAttrCodeForAdmin(ComCodeTReqDto comCodeT);

  /**
   * @기능 : 관리자용 속성코드 수정
   * @param comCodeT 속성코드 수정 요청 DTO
   * @return 수정 결과 정보가 담긴 Map
   */
  Map<String, Object> updateAttrCodeForAdmin(ComCodeTReqDto comCodeT);

  /**
   * @기능 : 관리자용 속성코드 삭제
   * @param grpCd  그룹코드
   * @param attrCd 속성코드
   * @return 삭제 결과 정보가 담긴 Map
   */
  Map<String, Object> deleteAttrCodeForAdmin(String grpCd, String attrCd);

  /**
   * @기능 : 관리자용 상세코드 전체 목록 조회
   * @return 상세코드 목록 정보가 담긴 Map
   */
  Map<String, Object> findAllDetailCodeForAdmin();

  /**
   * @기능 : 관리자용 특정 상세코드 상세 조회
   * @param dtlCd 상세코드
   * @return 상세코드 상세 정보가 담긴 Map
   */
  Map<String, Object> findByDetailCodeForAdmin(String dtlCd);

  /**
   * @기능 : 관리자용 상세코드 등록
   * @param comCodeD 상세코드 등록 요청 DTO
   * @return 등록 결과 정보가 담긴 Map
   */
  Map<String, Object> insertDetailCodeForAdmin(ComCodeDReqDto comCodeD);

  /**
   * @기능 : 관리자용 상세코드 수정
   * @param comCodeD 상세코드 수정 요청 DTO
   * @return 수정 결과 정보가 담긴 Map
   */
  Map<String, Object> updateDetailCodeForAdmin(ComCodeDReqDto comCodeD);

  /**
   * @기능 : 관리자용 상세코드 삭제
   * @param grpCd  그룹코드
   * @param attrCd 속성코드
   * @param dtlCd  상세코드
   * @return 삭제 결과 정보가 담긴 Map
   */
  Map<String, Object> deleteDetailCodeForAdmin(String grpCd, String attrCd, String dtlCd);

  /**
   * @기능 : 조건별 그룹코드 목록 조회 (페이징)
   * @param reqDto   코드 검색 조건 DTO
   * @param pageable 페이징 정보
   * @return 그룹코드 목록 정보가 담긴 Map
   */
  Map<String, Object> findAllCodeMWithConditions(CodeSearchFormReqDto reqDto, Pageable pageable);

  /**
   * @기능 : 조건별 코드 행 목록 조회 (페이징)
   * @param reqDto   코드 검색 조건 DTO
   * @param pageable 페이징 정보
   * @return 코드 행 목록 정보가 담긴 Map
   */
  Map<String, Object> findAllCodeRowMWithConditions(CodeSearchFormReqDto reqDto, Pageable pageable);

}
