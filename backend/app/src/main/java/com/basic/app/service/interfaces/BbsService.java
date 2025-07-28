package com.basic.app.service.interfaces;

import java.util.Map;

import com.basic.app.dto.requestDto.BbsReqDto;

public interface BbsService {

  Map<String, Object> findAllBbsForAdmin();

  Map<String, Object> findByBbsForAdmin(String bbsId);

  Map<String, Object> insertBbsForAdmin(BbsReqDto bbs);

  Map<String, Object> updateBbsForAdmin(BbsReqDto bbs);

  Map<String, Object> deleteBbsForAdmin(String bbsId);

}
