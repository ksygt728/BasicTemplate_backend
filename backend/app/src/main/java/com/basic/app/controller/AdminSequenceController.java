/**
 * @파일명   : AdminSequenceController.java
 * @설명     : 시퀀스(일련번호) 관리 기능 제공 컨트롤러
 * @작성자   : 김승연
 * @작성일   : 2025.07.23
 * @변경이력 :
 *   2025.07.23     김승연       최초 생성
 */

package com.basic.app.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.basic.app.api.ApiResponse;
import com.basic.app.dto.group.CreateGroup;
import com.basic.app.dto.group.UpdateGroup;
import com.basic.app.dto.requestDto.ChaebunReqDto;
import com.basic.app.service.interfaces.SequenceService;

@RestController
@RequestMapping("/admin/sequence")
public class AdminSequenceController {

  @Autowired
  private SequenceService sequenceService;

  /* [REQ_ADM_053] [화면 : 시스템 관리 > 채번관리] [기능 : 채번 리스트 조회] */
  @GetMapping
  public ResponseEntity<ApiResponse<Map<String, Object>>> findAllSequenceForAdmin() {
    Map<String, Object> data = sequenceService.findAllSequenceForAdmin();
    return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(data));
  }

  /* [REQ_ADM_053_2] [화면 : 시스템 관리 > 채번관리] [기능 : 채번 조회] */
  @GetMapping("/{seqId}")
  public ResponseEntity<ApiResponse<Map<String, Object>>> findBySequenceForAdmin(@PathVariable String seqId) {
    Map<String, Object> data = sequenceService.findBySequenceForAdmin(seqId);
    return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(data));
  }

  /* [REQ_ADM_054] [화면 : 시스템 관리 > 채번관리] [기능 : 채번 추가] */
  @PostMapping
  public ResponseEntity<ApiResponse<Map<String, Object>>> insertSequenceForAdmin(
      @Validated(CreateGroup.class) ChaebunReqDto chaebun) {
    Map<String, Object> data = sequenceService.insertSequenceForAdmin(chaebun);
    return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(data));
  }

  /* [REQ_ADM_055] [화면 : 시스템 관리 > 채번관리] [기능 : 채번 수정] */
  @PutMapping
  public ResponseEntity<ApiResponse<Map<String, Object>>> updateSequenceForAdmin(
      @Validated(UpdateGroup.class) ChaebunReqDto chaebun) {
    Map<String, Object> data = sequenceService.updateSequenceForAdmin(chaebun);
    return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(data));
  }

  /* [REQ_ADM_056] [화면 : 시스템 관리 > 채번관리] [기능 : 채번 삭제] */
  @DeleteMapping("/{seqId}")
  public ResponseEntity<ApiResponse<Map<String, Object>>> deleteSequenceForAdmin(@PathVariable String seqId) {
    Map<String, Object> data = sequenceService.deleteSequenceForAdmin(seqId);
    return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(data));
  }
}