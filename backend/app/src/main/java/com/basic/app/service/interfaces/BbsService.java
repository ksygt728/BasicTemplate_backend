package com.basic.app.service.interfaces;

import java.util.Map;

import com.basic.app.entity.Bbs;

public interface BbsService {

  Map<String, Object> findAllBbsForAdmin();

  Map<String, Object> findByBbsForAdmin(String bbsId);

  Map<String, Object> insertBbsForAdmin(Bbs bbs);

  Map<String, Object> updateBbsForAdmin(Bbs bbs);

  Map<String, Object> deleteBbsForAdmin(String bbsId);

}
