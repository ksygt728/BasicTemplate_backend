package com.basic.app.service.interfaces;

import java.util.Map;

import com.basic.app.entity.Menu;

public interface MenuService {

  Map<String, Object> findAllMenuForAdmin();

  Map<String, Object> findByMenuForAdmin(String menuCd);

  Map<String, Object> insertMenuForAdmin(Menu menu);

  Map<String, Object> updateMenuForAdmin(Menu menu);

  Map<String, Object> deleteMenuForAdmin(String menuCd);

}
