package com.basic.app.service.interfaces;

import java.util.Map;

import com.basic.app.dto.requestDto.ChaebunReqDto;

public interface SequenceService {

  Map<String, Object> findAllSequenceForAdmin();

  Map<String, Object> findBySequenceForAdmin(String seqId);

  Map<String, Object> insertSequenceForAdmin(ChaebunReqDto chaebun);

  Map<String, Object> updateSequenceForAdmin(ChaebunReqDto chaebun);

  Map<String, Object> deleteSequenceForAdmin(String seqId);

}
