package com.basic.app.service.interfaces;

import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.basic.app.dto.requestDto.ChaebunReqDto;

public interface ChaebunService {

  Map<String, Object> findAllChaebunForAdmin(ChaebunReqDto chaebunReqDto, Pageable pageable);

  Map<String, Object> findByChaebunForAdmin(String seqId);

  Map<String, Object> insertChaebunForAdmin(ChaebunReqDto chaebunReqDto);

  Map<String, Object> updateChaebunForAdmin(ChaebunReqDto chaebunReqDto);

  Map<String, Object> deleteChaebunForAdmin(String seqId);

  String generateSeq(String seqId);
}
