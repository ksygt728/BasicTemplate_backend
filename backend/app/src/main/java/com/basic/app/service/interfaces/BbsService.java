package com.basic.app.service.interfaces;

import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.basic.app.auth.CustomUserDetails;
import com.basic.app.dto.requestDto.BbsCommentReqDto;
import com.basic.app.dto.requestDto.BbsReqDto;

public interface BbsService {

  Map<String, Object> findAllBbsForAdmin(BbsReqDto bbsReqDto, Pageable pageable);

  Map<String, Object> findByBbsForAdmin(String bbsId);

  Map<String, Object> insertBbsForAdmin(BbsReqDto bbsReqDto, CustomUserDetails user);

  Map<String, Object> updateBbsForAdmin(BbsReqDto bbsReqDto);

  Map<String, Object> deleteBbsForAdmin(String bbsId);

  Map<String, Object> findAllBbsCommentForAdmin(String bbsId, Pageable pageable);

  Map<String, Object> findByBbsCommentForAdmin(String commentId);

  Map<String, Object> insertBbsCommentForAdmin(BbsCommentReqDto bbsCommentReqDto, CustomUserDetails user);

  Map<String, Object> updateBbsCommentForAdmin(BbsCommentReqDto bbsCommentReqDto, CustomUserDetails user);

  Map<String, Object> deleteBbsCommentForAdmin(String commentId);

}
