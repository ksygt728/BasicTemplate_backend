package com.basic.app.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.basic.app.api.ModelMapperUtils;
import com.basic.app.api.PageResponse;
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

@Transactional
@Service
public class MenuServiceImpl implements MenuService {

  @Autowired
  private MenuRepository menuRepository;

  @Autowired
  private MenuJooqRepository menuJooqRepository;

  @Override
  public Map<String, Object> findAllMenuForAdmin(MenuReqDto menuReqDto, Pageable pageable) {

    Map<String, Object> data = new HashMap<>();
    // 1. 조건에 맞는 인터페이스 조회
    Page<MenuResDto> menuDtoList = menuJooqRepository
        .findAllMenuWithConditions(menuReqDto, pageable);
    // 2. Page -> PageResponse 변환(이미 DTO로 변환된 상태이므로 추가 변환은 필요 없음)

    PageResponse<MenuResDto> pagedmenuDtoList = ModelMapperUtils.map(menuDtoList,
        MenuResDto.class);

    // 3. 결과를 Map에 담아 반환
    data.put("data", pagedmenuDtoList);
    return data;
  }

  @Override
  public Map<String, Object> findByMenuForAdmin(String menuCd) {

    Map<String, Object> data = new HashMap<>();

    // 1. 조회(만약 인터페이스가 존재하지 않으면 null 반환)
    // 2. Entity -> DTO 변환
    MenuResDto menuResDto = menuRepository.findById(menuCd)
        .filter(entity -> entity.getSts().equals(Status.POSITIVE))
        .map(entity -> entity.toDto(MenuResDto.class))
        .orElseThrow(() -> new NotFoundException(ErrorCode.OBJECT_NOT_FOUND));

    // 3. 결과를 Map에 담아 반환
    data.put("data", menuResDto);
    return data;
  }

  @Override
  public Map<String, Object> insertMenuForAdmin(MenuReqDto menuReqDto) {

    Map<String, Object> data = new HashMap<>();

    // 1. DTO -> Entity 변환
    Menu menuEntity = menuReqDto.toEntity(Menu.class);

    // 2. ID로 기존 엔티티 조회
    menuRepository.findById(menuEntity.getMenuCd())
        .filter(entity -> entity.getSts().equals(Status.POSITIVE))
        .ifPresent(entity -> {
          throw new BusinessException(ErrorCode.OBJECT_IS_EXISTED);
        });

    // 3. DTO -> Entity 후 데이터 저장
    Menu savedMenuEntity = menuRepository.save(menuEntity);

    // 4. Entity -> DTO 변환
    // 5. 결과를 Map에 담아 반환
    data.put("data", savedMenuEntity.toDto(MenuResDto.class));
    return data;
  }

  @Override
  public Map<String, Object> updateMenuForAdmin(MenuReqDto menuReqDto) {

    Map<String, Object> data = new HashMap<>();

    // 1. DTO -> Entity 변환
    Menu menuEntity = menuReqDto.toEntity(Menu.class);

    // 2. ID로 기존 엔티티 조회
    menuRepository.findById(menuEntity.getMenuCd())
        .filter(entity -> entity.getSts().equals(Status.POSITIVE))
        .orElseThrow(() -> new NotFoundException(ErrorCode.OBJECT_NOT_FOUND));

    // 3. 엔티티 수정 & 저장(자동)
    Menu savedMenuEntity = menuRepository.save(menuEntity);

    // 4. Entity -> DTO 변환
    // 5. 결과를 Map에 담아 반환
    data.put("data", savedMenuEntity.toDto(MenuResDto.class));

    return data;
  }

  @Override
  public Map<String, Object> deleteMenuForAdmin(String menuCd) {
    Map<String, Object> data = new HashMap<>();

    // 1. ID로 인터페이스 조회(만약 인터페이스가 존재하지 않으면 NotFoundException 발생)
    menuRepository.findById(menuCd)
        .filter(entity -> entity.getSts().equals(Status.POSITIVE))
        .orElseThrow(() -> new NotFoundException(ErrorCode.OBJECT_NOT_FOUND));

    // 하위메뉴 포함 삭제
    menuRepository.deleteMenuAndSubmenus(menuCd, Status.NAGATIVE);

    // 3. 결과를 Map에 담아 반환
    data.put("data", "success");
    return data;

  }

}
