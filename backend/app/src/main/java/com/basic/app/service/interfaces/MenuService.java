
package com.basic.app.service.interfaces;

import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.basic.app.dto.requestDto.MenuReqDto;

/**
 * @파일명 : MenuService.java
 * @설명 : 메뉴 관련 서비스 인터페이스
 * @작성자 : 김승연
 * @작성일 : 2025.08.6
 * @변경이력 :
 *       2025.08.6 김승연 최초 생성
 */
public interface MenuService {

  /**
   * @기능 : 관리자용 메뉴 전체 목록 조회 (페이징)
   * @param menuReqDto 메뉴 검색 조건 DTO
   * @param pageable   페이징 정보
   * @return 메뉴 목록 정보가 담긴 Map
   */
  Map<String, Object> findAllMenuForAdmin(MenuReqDto menuReqDto, Pageable pageable);

  /**
   * @기능 : 관리자용 특정 메뉴 상세 조회
   * @param menuCd 메뉴 코드
   * @return 메뉴 상세 정보가 담긴 Map
   */
  Map<String, Object> findByMenuForAdmin(String menuCd);

  /**
   * @기능 : 관리자용 메뉴 등록
   * @param menuReqDto 메뉴 등록 요청 DTO
   * @return 등록 결과 정보가 담긴 Map
   */
  Map<String, Object> insertMenuForAdmin(MenuReqDto menuReqDto);

  /**
   * @기능 : 관리자용 메뉴 수정
   * @param menuReqDto 메뉴 수정 요청 DTO
   * @return 수정 결과 정보가 담긴 Map
   */
  Map<String, Object> updateMenuForAdmin(MenuReqDto menuReqDto);

  /**
   * @기능 : 관리자용 메뉴 삭제
   * @param menuCd 메뉴 코드
   * @return 삭제 결과 정보가 담긴 Map
   */
  Map<String, Object> deleteMenuForAdmin(String menuCd);

}
