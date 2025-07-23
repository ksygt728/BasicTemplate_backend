package com.basic.app.service.interfaces;

import java.util.Map;

import com.basic.app.entity.Chaebun;

public interface SequenceService {

  Map<String, Object> findAllSequenceForAdmin();

  Map<String, Object> findBySequenceForAdmin(String seqId);

  Map<String, Object> insertSequenceForAdmin(Chaebun chaebun);

  Map<String, Object> updateSequenceForAdmin(Chaebun chaebun);

  Map<String, Object> deleteSequenceForAdmin(String seqId);

}
