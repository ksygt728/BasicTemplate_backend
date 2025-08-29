
/**
 * @파일명   : UserController.java
 * @설명     : 사용자 컨트롤러
 * @작성자   : 김승연
 * @작성일   : 2025.07.31
 * @변경이력 :
 *   2025.07.31     김승연       최초 생성
 */

package com.basic.app.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.basic.app.annotation.SwaggerCommonResponseApi;
import com.basic.app.api.ResponseApi;
import com.basic.app.dto.responseDto.UserResDto;
import com.basic.app.service.interfaces.SharedService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Tag(name = "SharedController", description = "시스템 공유 API")
@RestController
@RequestMapping("/api/shared")
public class SharedController {

  @Autowired
  private SharedService sharedService;

  /* [-] [화면 : -] [기능 : 다국어 목록 가져오기] */
  @Operation(summary = "[-] [화면 : -] [기능 : 다국어 목록 가져오기]", description = "다국어 목록을 가져옵니다.")
  @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = UserResDto.class)))
  @SwaggerCommonResponseApi
  @GetMapping("/mulLang")
  public ResponseEntity<ResponseApi<Map<String, Object>>> getMulLangList(
      @RequestParam(defaultValue = "ko") String localeText) {
    Map<String, Object> data = sharedService.getMulLangList(localeText);

    return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.success(data));
  }

}