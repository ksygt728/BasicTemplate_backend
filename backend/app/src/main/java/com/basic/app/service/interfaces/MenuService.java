package com.basic.app.service.interfaces;

import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.basic.app.dto.requestDto.MenuReqDto;

public interface MenuService {

  Map<String, Object> findAllMenuForAdmin(MenuReqDto menuReqDto, Pageable pageable);

  Map<String, Object> findByMenuForAdmin(String menuCd);

  Map<String, Object> insertMenuForAdmin(MenuReqDto menuReqDto);

  Map<String, Object> updateMenuForAdmin(MenuReqDto menuReqDto);

  Map<String, Object> deleteMenuForAdmin(String menuCd);

}
