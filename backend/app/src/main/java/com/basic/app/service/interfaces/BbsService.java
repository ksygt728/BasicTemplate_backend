
package com.basic.app.service.interfaces;

import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.basic.app.auth.CustomUserDetails;
import com.basic.app.dto.requestDto.BbsCommentReqDto;
import com.basic.app.dto.requestDto.BbsReqDto;

/**
 * @파일명 : BbsService.java
 * @설명 : 게시판 관련 서비스 인터페이스
 * @작성자 : 김승연
 * @작성일 : 2025.08.6
 * @변경이력 :
 *       2025.08.6 김승연 최초 생성
 */

public interface BbsService {

  /**
   * @기능 : 관리자용 게시글 전체 목록 조회 (페이징)
   * @param bbsReqDto 게시글 검색 조건 DTO
   * @param pageable  페이징 정보
   * @return 게시글 목록 정보가 담긴 Map
   */
  Map<String, Object> findAllBbsForAdmin(BbsReqDto bbsReqDto, Pageable pageable);

  /**
   * @기능 : 관리자용 특정 게시글 상세 조회
   * @param bbsId 게시글 ID
   * @return 게시글 상세 정보가 담긴 Map
   */
  Map<String, Object> findByBbsForAdmin(String bbsId);

  /**
   * @기능 : 관리자용 게시글 등록
   * @param bbsReqDto 게시글 등록 요청 DTO
   * @param user      현재 사용자 정보
   * @return 등록 결과 정보가 담긴 Map
   */
  Map<String, Object> insertBbsForAdmin(BbsReqDto bbsReqDto, CustomUserDetails user);

  /**
   * @기능 : 관리자용 게시글 수정
   * @param bbsReqDto 게시글 수정 요청 DTO
   * @return 수정 결과 정보가 담긴 Map
   */
  Map<String, Object> updateBbsForAdmin(BbsReqDto bbsReqDto);

  /**
   * @기능 : 관리자용 게시글 삭제
   * @param bbsId 게시글 ID
   * @return 삭제 결과 정보가 담긴 Map
   */
  Map<String, Object> deleteBbsForAdmin(String bbsId);

  /**
   * @기능 : 관리자용 특정 게시글의 댓글 목록 조회 (페이징)
   * @param bbsId    게시글 ID
   * @param pageable 페이징 정보
   * @return 댓글 목록 정보가 담긴 Map
   */
  Map<String, Object> findAllBbsCommentForAdmin(String bbsId, Pageable pageable);

  /**
   * @기능 : 관리자용 특정 댓글 상세 조회
   * @param commentId 댓글 ID
   * @return 댓글 상세 정보가 담긴 Map
   */
  Map<String, Object> findByBbsCommentForAdmin(String commentId);

  /**
   * @기능 : 관리자용 댓글 등록
   * @param bbsCommentReqDto 댓글 등록 요청 DTO
   * @param user             현재 사용자 정보
   * @return 등록 결과 정보가 담긴 Map
   */
  Map<String, Object> insertBbsCommentForAdmin(BbsCommentReqDto bbsCommentReqDto, CustomUserDetails user);

  /**
   * @기능 : 관리자용 댓글 수정
   * @param bbsCommentReqDto 댓글 수정 요청 DTO
   * @param user             현재 사용자 정보
   * @return 수정 결과 정보가 담긴 Map
   */
  Map<String, Object> updateBbsCommentForAdmin(BbsCommentReqDto bbsCommentReqDto, CustomUserDetails user);

  /**
   * @기능 : 관리자용 댓글 삭제
   * @param commentId 댓글 ID
   * @return 삭제 결과 정보가 담긴 Map
   */
  Map<String, Object> deleteBbsCommentForAdmin(String commentId);

}
