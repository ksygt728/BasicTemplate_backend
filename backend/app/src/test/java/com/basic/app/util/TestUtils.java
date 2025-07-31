package com.basic.app.util;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.basic.app.api.ApiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class TestUtils {

  private static final ObjectMapper objectMapper = new ObjectMapper();

  // DTO -> MultiValueMap
  public static MultiValueMap<String, String> dtoToMultiValueMap(Object dto) {
    MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
    Map<String, String> paramMap = objectMapper.convertValue(dto, new TypeReference<Map<String, String>>() {
    });
    paramMap.forEach(multiValueMap::add);
    return multiValueMap;
  }

  // ApiResponse -> JsonNode
  public static JsonNode apiReponseToJsonNode(ApiResponse<?> expected) throws Exception {
    String expectedJson = objectMapper.writeValueAsString(expected);
    return objectMapper.readTree(expectedJson);
  }

  // MvcResult -> JsonNode
  public static JsonNode mvcResultToJsonNode(MvcResult actual) throws Exception {
    String content = actual.getResponse().getContentAsString();
    return objectMapper.readTree(content);
  }

  // 테스트케이스별 시작로그 출력
  public static void showLogTestCaseStart(String testCaseName) throws Exception {
    log.info("🔄 테스트 시작: " + testCaseName);

  }

  // 테스트케이스별 종료로그 출력
  public static void showLogTestCaseEnd(Object testData, JsonNode expectedToJson, JsonNode actualToJson)
      throws Exception {
    log.info("🧪 Test Data: {}", testData);
    log.info("☑️ Expected Response: {}", expectedToJson);
    log.info("🧠 Actual Response: {}", actualToJson);
  }

}
