package com.basic.app.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.basic.app.dto.requestDto.MenuReqDto;
import com.basic.app.dto.responseDto.MenuResDto;
import com.basic.app.entity.Menu;
import com.basic.app.exception.ErrorCode;
import com.basic.app.exception.customException.BusinessException;
import com.basic.app.exception.customException.NotFoundException;
import com.basic.app.repository.MenuRepository;
import com.basic.app.repository.jooqRepository.MenuJooqRepository;
import com.basic.app.service.interfaces.MenuService;
import com.basic.app.util.Status;

/**
 * @파일명 : MenuServiceImpl.java
 * @설명 : 메뉴 관련 서비스 구현체
 * @작성자 : 김승연
 * @작성일 : 2025.09.05
 * @변경이력 :
 *       2025.09.05 김승연 최초 생성
 */
@Transactional
@Service
public class MenuServiceImpl implements MenuService {

  @Autowired
  private MenuRepository menuRepository;

  @Autowired
  private MenuJooqRepository menuJooqRepository;

  /**
   * @기능 : 관리자용 메뉴 전체 목록 조회 (트리 구조)
   * @param menuReqDto 메뉴 검색 조건 DTO
   * @param pageable   페이징 정보
   * @return 메뉴 트리 구조가 담긴 Map
   */
  @Override
  public Map<String, Object> findAllMenuForAdmin(MenuReqDto menuReqDto, Pageable pageable) {

    Map<String, Object> data = new HashMap<>();

    // List<Menu> menuEntityList =
    // menuRepository.findAllByStsOrderByMenuLvAscOrderNumAsc(Status.POSITIVE);

    // // Entity -> DTO 변환
    // List<MenuResDto> menuResDtoList = menuEntityList.stream()
    // .map(entity -> entity.toDto(entity))
    // .toList();

    Page<MenuResDto> menuResDtoList = menuJooqRepository.findAllMenuWithConditions(menuReqDto, pageable);

    // 메뉴코드 → Menu 매핑
    Map<String, MenuResDto> menuMap = menuResDtoList.getContent().stream()
        .collect(Collectors.toMap(MenuResDto::getMenuCd, m -> m));

    // 최상위 메뉴 리스트
    List<MenuResDto> menuTree = new ArrayList<>();

    for (MenuResDto menu : menuResDtoList) {
      if (menu.getUpperMenu() != null) {
        MenuResDto parent = menuMap.get(menu.getUpperMenu());
        if (parent != null) {
          parent.getChildMenus().add(menu);
        }
      } else {
        // 상위 메뉴 없으면 최상위 메뉴로 추가
        menuTree.add(menu);
      }
    }

    // 3. 결과를 Map에 담아 반환
    data.put("data", menuTree);
    return data;
  }

  /**
   * @기능 : 관리자용 특정 메뉴 상세 조회
   * @param menuCd 메뉴 코드
   * @return 메뉴 상세 정보가 담긴 Map
   */
  @Override
  public Map<String, Object> findByMenuForAdmin(String menuCd) {

    Map<String, Object> data = new HashMap<>();

    // 1. 조회(만약 인터페이스가 존재하지 않으면 null 반환)
    // 2. Entity -> DTO 변환
    MenuResDto menuResDto = menuRepository.findById(menuCd)
        .filter(entity -> entity.getSts().equals(Status.POSITIVE))
        .map(entity -> entity.toDto(entity))
        .orElseThrow(() -> new NotFoundException(ErrorCode.OBJECT_NOT_FOUND));

    // 3. 결과를 Map에 담아 반환
    data.put("data", menuResDto);
    return data;
  }

  /**
   * @기능 : 관리자용 메뉴 신규 등록
   * @param menuReqDto 메뉴 등록 요청 DTO
   * @return 등록된 메뉴 정보가 담긴 Map
   */
  @Override
  public Map<String, Object> insertMenuForAdmin(MenuReqDto menuReqDto) {

    Map<String, Object> data = new HashMap<>();

    // 1. DTO -> Entity 변환
    Menu menuEntity = menuReqDto.toEntity(Menu.class);

    // 2. ID로 기존 엔티티 조회 & 부모키 조회
    menuRepository.findById(menuEntity.getMenuCd())
        .filter(entity -> entity.getSts().equals(Status.POSITIVE))
        .ifPresent(entity -> {
          throw new BusinessException(ErrorCode.OBJECT_IS_EXISTED, menuEntity.getMenuCd());
        });

    Menu parentMenu = menuRepository.findById(menuReqDto.getUpperMenu())
        .filter(entity -> entity.getSts().equals(Status.POSITIVE))
        .orElseThrow(() -> new NotFoundException(ErrorCode.OBJECT_NOT_FOUND, menuReqDto.getUpperMenu()));

    menuEntity.setMenuLv(parentMenu.getMenuLv() + 1);
    menuEntity.setUpperMenu(parentMenu);

    // 3. DTO -> Entity 후 데이터 저장
    Menu savedMenuEntity = menuRepository.save(menuEntity);

    // 4. Entity -> DTO 변환
    // 5. 결과를 Map에 담아 반환
    data.put("data", savedMenuEntity.toDto(savedMenuEntity));
    return data;
  }

  /**
   * @기능 : 관리자용 메뉴 정보 수정
   * @param menuReqDto 메뉴 수정 요청 DTO
   * @return 수정된 메뉴 정보가 담긴 Map
   */
  @Override
  public Map<String, Object> updateMenuForAdmin(MenuReqDto menuReqDto) {

    Map<String, Object> data = new HashMap<>();

    // 1. DTO -> Entity 변환
    Menu menuEntity = menuReqDto.toEntity(Menu.class);

    // 2. ID로 기존 엔티티 조회
    menuRepository.findById(menuEntity.getMenuCd())
        .filter(entity -> entity.getSts().equals(Status.POSITIVE))
        .orElseThrow(() -> new NotFoundException(ErrorCode.OBJECT_NOT_FOUND, menuEntity.getMenuCd()));

    Menu parentMenu = menuRepository.findById(menuReqDto.getUpperMenu())
        .filter(entity -> entity.getSts().equals(Status.POSITIVE))
        .orElseThrow(() -> new NotFoundException(ErrorCode.OBJECT_NOT_FOUND, menuReqDto.getUpperMenu()));

    // 3. 엔티티 수정 & 저장(자동)
    menuEntity.setMenuLv(parentMenu.getMenuLv() + 1);
    menuEntity.setUpperMenu(parentMenu);

    Menu savedMenuEntity = menuRepository.save(menuEntity);

    // 4. Entity -> DTO 변환
    // 5. 결과를 Map에 담아 반환
    data.put("data", savedMenuEntity.toDto(savedMenuEntity));

    return data;
  }

  /**
   * @기능 : 관리자용 메뉴 삭제 (하위 메뉴 포함)
   * @param menuCd 삭제할 메뉴 코드
   * @return 삭제 성공 메시지가 담긴 Map
   */
  @Override
  public Map<String, Object> deleteMenuForAdmin(String menuCd) {
    Map<String, Object> data = new HashMap<>();

    // 1. ID로 인터페이스 조회(만약 인터페이스가 존재하지 않으면 NotFoundException 발생)
    menuRepository.findById(menuCd)
        .filter(entity -> entity.getSts().equals(Status.POSITIVE))
        .orElseThrow(() -> new NotFoundException(ErrorCode.OBJECT_NOT_FOUND, menuCd));

    // 하위메뉴 포함 삭제
    menuRepository.deleteMenuAndSubmenus(menuCd, Status.NAGATIVE);

    // 3. 결과를 Map에 담아 반환
    data.put("data", "success");
    return data;

  }

}
