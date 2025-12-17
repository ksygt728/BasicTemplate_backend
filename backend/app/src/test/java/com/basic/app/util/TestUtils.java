package com.basic.app.util;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Map;

import org.apache.kafka.common.annotation.InterfaceStability.Stable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.basic.app.api.ResponseApi;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class TestUtils {

  private static final ObjectMapper objectMapper = createObjectMapper();

  private static ObjectMapper createObjectMapper() {
    ObjectMapper mapper = new ObjectMapper();

    // Java 8 시간 모듈 등록
    JavaTimeModule timeModule = new JavaTimeModule();
    mapper.registerModule(timeModule);

    // 설정
    mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    mapper.enable(SerializationFeature.INDENT_OUTPUT); // 테스트 시 읽기 쉽게

    return mapper;
  }

  /**
   * @설명 : DTO -> MultiValueMap
   */
  public static MultiValueMap<String, String> dtoToMultiValueMap(Object dto) {
    MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
    Map<String, String> paramMap = objectMapper.convertValue(dto, new TypeReference<Map<String, String>>() {
    });
    paramMap.forEach(multiValueMap::add);
    return multiValueMap;
  }

  /**
   * @설명 : ResponseApi -> JsonNode
   */
  public static JsonNode apiReponseToJsonNode(ResponseApi<?> expected) throws Exception {
    String expectedJson = objectMapper.writeValueAsString(expected);
    return objectMapper.readTree(expectedJson);
  }

  /**
   * @설명 : MvcResult -> JsonNode
   */
  public static JsonNode mvcResultToJsonNode(MvcResult actual) throws Exception {
    String content = actual.getResponse().getContentAsString(StandardCharsets.UTF_8); // UTF-8 명시
    ObjectMapper objectMapper = new ObjectMapper();
    return objectMapper.readTree(content);
  }

  /**
   * @설명 : 테스트케이스별 시작로그 출력
   */
  public static void showLogTestCaseStart(String testCaseName) throws Exception {
    // log.info("🔄 [CBSK-TEST] 테스트 시작: " + testCaseName);

  }

  /**
   * @설명 : 테스트케이스별 종료로그 출력
   */
  public static void showLogTestCaseEnd(Object testData, JsonNode expectedToJson, JsonNode actualToJson)
      throws Exception {
    log.info("\n🧪 [CBSK-TEST] Test Data: {}", testData);
    log.info("\n☑️ [CBSK-TEST] Expected Response: {}", expectedToJson);
    log.info("\n🧠 [CBSK-TEST] Actual Response: {}", actualToJson);
  }

  /**
   * @설명 : 테스트케이스가 없는 경우 로그 출력
   */
  public static void showLogNoneTestCaseEnd(String testData, String expected, String actual) {
    log.info("\n🧪 [CBSK-TEST] Test Data: {}", testData);
    log.info("\n☑️ [CBSK-TEST] Expected Response: {}", expected);
    log.info("\n🧠 [CBSK-TEST] Actual Response: {}", actual);
  }

  /**
   * @설명 : Object -> JSON String
   */
  public static String objectToJson(Object obj) throws Exception {
    return objectMapper.writeValueAsString(obj);
  }

  /**
   * @설명 : Test 시 제외할 필드 설정
   */
  public static JsonNode ignoreFields(JsonNode testData, String... ignoreFields) {

    // 페이징된 목록 조회의 경우 content가 배열이므로 각 요소의 writeDate를 제거
    if (testData.has("data") && testData.get("data").has("data")
        && testData.get("data").get("data").has("content")) {

      JsonNode testDataContent = testData.get("data").get("data").get("content");

      // content가 배열인지 확인
      if (testDataContent.isArray()) {
        Arrays.stream(ignoreFields).forEach(filed -> {
          for (JsonNode item : testDataContent) {
            if (item.isObject()) {
              ((ObjectNode) item).remove(filed);
            }
          }

        });
      }
    }

    return testData;

  }

}
