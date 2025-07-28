package com.basic.app.service.interfaces;

import java.util.Map;

import com.basic.app.dto.requestDto.MenuReqDto;

public interface MenuService {

  Map<String, Object> findAllMenuForAdmin();

  Map<String, Object> findByMenuForAdmin(String menuCd);

  Map<String, Object> insertMenuForAdmin(MenuReqDto menu);

  Map<String, Object> updateMenuForAdmin(MenuReqDto menu);

  Map<String, Object> deleteMenuForAdmin(String menuCd);

}
