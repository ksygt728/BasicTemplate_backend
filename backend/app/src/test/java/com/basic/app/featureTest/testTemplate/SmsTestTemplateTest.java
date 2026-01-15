package com.basic.app.featureTest.testTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.basic.app.api.ResponseApi;
import com.basic.app.featureTest.testcases.sms.SmsFormatTestCasesSearchAll;
import com.basic.app.featureTest.testcases.sms.SmsTestCasesForDelete;
import com.basic.app.featureTest.testcases.sms.SmsTestCasesForHistory;
import com.basic.app.featureTest.testcases.sms.SmsTestCasesForInsert;
import com.basic.app.featureTest.testcases.sms.SmsTestCasesForSearch;
import com.basic.app.featureTest.testcases.sms.SmsTestCasesForUpdate;
import com.basic.app.util.TestCaseDetail;
import com.basic.app.util.TestCaseDetailSearchForm;
import com.basic.app.util.TestUtils;
import com.fasterxml.jackson.databind.JsonNode;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@AutoConfigureMockMvc
@Log4j2
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // 클래스 단위로 테스트 인스턴스 생성
@Transactional
@Sql(scripts = {
    "classpath:sql/test-data/sms/sms-test-data.sql"
}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@Sql(scripts = {
    "classpath:sql/test-data/sms/cleanup-test-data.sql"
}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_CLASS)
public class SmsTestTemplateTest {

  @Autowired
  private MockMvc mockMvc;

  long startTime;

  @BeforeAll
  void setUpOnce() {
    log.info("\n📦 [CBMS-TEST] ***[테스트 전체 시작]*** {}", this.getClass().getName());
    // DB 스키마 초기화나 공통 설정 작업
  }

  @BeforeEach
  void beforeEach(TestInfo testInfo) {

    log.info("\n\n🔄 [CBMS-TEST] ***[테스트 시작]*** " + testInfo.getDisplayName());
    startTime = System.nanoTime();
  }

  @AfterEach
  void afterEach(TestInfo testInfo) {

    long endTime = System.nanoTime();
    long durationMs = (endTime - startTime) / 1_000_000;
    log.info("\n✅ [CBMS-TEST] ***[테스트 종료]*** (실행 시간: {} ms)\n", durationMs);

  }

  @AfterAll
  void cleanUpOnce() {
    log.info("\n🧹 [CBMS-TEST] ***[테스트 전체 종료]*** {}", this.getClass().getName());

  }

  /*************************************
   * [테스트 케이스]
   * 
   *************************************/
  @TestTemplate
  @ExtendWith(SmsTestCasesForSearch.class)
  @DisplayName("1. SMS_단건_조회")
  void SMS_단건_조회(TestCaseDetail<?> testCaseDetail) throws Exception {

    if (testCaseDetail.getUrl().equals("N/A")) {

      processNoneTestCase(testCaseDetail);
    } else {

      /* 1. given */
      String url = testCaseDetail.getUrl();
      String testCaseName = testCaseDetail.getTestName();
      Object testData = testCaseDetail.getTestData();
      ResponseApi<?> expected = testCaseDetail.getExpected();
      ResultMatcher httpStatus = testCaseDetail.getHttpStatus();

      /* 2. when */
      TestUtils.showLogTestCaseStart(testCaseName);

      MvcResult actual = mockMvc.perform(get(url)
          .contentType(MediaType.APPLICATION_FORM_URLENCODED)
          .header("test-token", true))
          .andExpect(httpStatus)
          .andReturn();

      /* 3. then */
      JsonNode expectedToJson = TestUtils.apiReponseToJsonNode(expected);
      JsonNode actualToJson = TestUtils.mvcResultToJsonNode(actual);

      TestUtils.showLogTestCaseEnd(testData, expectedToJson, actualToJson);

      assertThat(actualToJson).isEqualTo(expectedToJson);
    }
  }

  @TestTemplate
  @ExtendWith(SmsTestCasesForHistory.class)
  @DisplayName("2. SMS_히스토리_조회")
  void SMS_히스토리_조회(TestCaseDetailSearchForm<?, ?> testCaseDetail) throws Exception {

    if (testCaseDetail.getUrl().equals("N/A")) {
      processNoneTestCase(testCaseDetail);

    } else {

      /* 1. given */

      String url = testCaseDetail.getUrl();
      String testCaseName = testCaseDetail.getTestName();
      Object testData = testCaseDetail.getTestData();
      ResponseApi<?> expected = testCaseDetail.getExpected();
      ResultMatcher httpStatus = testCaseDetail.getHttpStatus();
      PageRequest pageRequest = testCaseDetail.getPageRequest();

      /* 2. when */
      TestUtils.showLogTestCaseStart(testCaseName);

      // 파라미터 변환
      MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
      multiValueMap.add("page", String.valueOf(pageRequest.getPageNumber()));
      multiValueMap.add("size", String.valueOf(pageRequest.getPageSize()));

      // Sort 파라미터를 개별적으로 처리
      if (pageRequest.getSort().isSorted()) {
        pageRequest.getSort().forEach(order -> {
          multiValueMap.add("sort", order.getProperty() + "," + order.getDirection().name().toLowerCase());
        });
      }

      MvcResult actual = mockMvc.perform(get(url)
          .contentType(MediaType.APPLICATION_FORM_URLENCODED)
          .header("test-token", true)
          .params(multiValueMap))
          .andExpect(httpStatus)
          .andReturn();

      /* 3. then */
      JsonNode expectedToJson = TestUtils.apiReponseToJsonNode(expected);
      JsonNode actualToJson = TestUtils.mvcResultToJsonNode(actual);

      TestUtils.showLogTestCaseEnd(testData, expectedToJson, actualToJson);

      assertThat(actualToJson).isEqualTo(expectedToJson);
    }
  }

  @TestTemplate
  @ExtendWith(SmsFormatTestCasesSearchAll.class)
  @DisplayName("3. SMS_N건_조회")
  void SMS_N건_조회(TestCaseDetailSearchForm<?, ?> testCaseDetail) throws Exception {

    if (testCaseDetail.getUrl().equals("N/A")) {
      processNoneTestCase(testCaseDetail);

    } else {

      /* 1. given */

      String url = testCaseDetail.getUrl();
      String testCaseName = testCaseDetail.getTestName();
      Object testData = testCaseDetail.getTestData();
      ResponseApi<?> expected = testCaseDetail.getExpected();
      ResultMatcher httpStatus = testCaseDetail.getHttpStatus();
      PageRequest pageRequest = testCaseDetail.getPageRequest();

      /* 2. when */
      TestUtils.showLogTestCaseStart(testCaseName);

      // 파라미터 변환
      MultiValueMap<String, String> multiValueMap = TestUtils.dtoToMultiValueMap(testData);
      multiValueMap.add("page", String.valueOf(pageRequest.getPageNumber()));
      multiValueMap.add("size", String.valueOf(pageRequest.getPageSize()));

      // Sort 파라미터를 개별적으로 처리
      if (pageRequest.getSort().isSorted()) {
        pageRequest.getSort().forEach(order -> {
          multiValueMap.add("sort", order.getProperty() + "," + order.getDirection().name().toLowerCase());
        });
      }

      MvcResult actual = mockMvc.perform(get(url)
          .contentType(MediaType.APPLICATION_FORM_URLENCODED)
          .header("test-token", true)
          .params(multiValueMap))
          .andExpect(httpStatus)
          .andReturn();

      /* 3. then */
      JsonNode expectedToJson = TestUtils.apiReponseToJsonNode(expected);
      JsonNode actualToJson = TestUtils.mvcResultToJsonNode(actual);

      TestUtils.showLogTestCaseEnd(testData, expectedToJson, actualToJson);

      assertThat(actualToJson).isEqualTo(expectedToJson);
    }
  }

  @TestTemplate
  @ExtendWith(SmsTestCasesForInsert.class)
  @DisplayName("4. SMS_추가")
  void SMS_추가(TestCaseDetail<?> testCaseDetail) throws Exception {

    if (testCaseDetail.getUrl().equals("N/A")) {

      processNoneTestCase(testCaseDetail);
    } else {

      /* 1. given */
      String url = testCaseDetail.getUrl();
      String testCaseName = testCaseDetail.getTestName();
      Object testData = testCaseDetail.getTestData();
      ResponseApi<?> expected = testCaseDetail.getExpected();
      ResultMatcher httpStatus = testCaseDetail.getHttpStatus();

      /* 2. when */
      TestUtils.showLogTestCaseStart(testCaseName);

      String jsonContent = TestUtils.objectToJson(testData);

      MvcResult actual = mockMvc.perform(
          post(url)
              .contentType(MediaType.APPLICATION_JSON)
              .header("test-token", true)
              .content(jsonContent))
          .andExpect(httpStatus)
          .andReturn();

      /* 3. then */
      JsonNode expectedToJson = TestUtils.apiReponseToJsonNode(expected);
      JsonNode actualToJson = TestUtils.mvcResultToJsonNode(actual);

      TestUtils.showLogTestCaseEnd(testData, expectedToJson, actualToJson);

      assertThat(actualToJson).isEqualTo(expectedToJson);
    }
  }

  @TestTemplate
  @ExtendWith(SmsTestCasesForUpdate.class)
  @DisplayName("5. SMS_수정")
  void SMS_수정(TestCaseDetail<?> testCaseDetail) throws Exception {

    if (testCaseDetail.getUrl().equals("N/A")) {

      processNoneTestCase(testCaseDetail);
    } else {

      /* 1. given */
      String url = testCaseDetail.getUrl();
      String testCaseName = testCaseDetail.getTestName();
      Object testData = testCaseDetail.getTestData();
      ResponseApi<?> expected = testCaseDetail.getExpected();
      ResultMatcher httpStatus = testCaseDetail.getHttpStatus();

      /* 2. when */
      TestUtils.showLogTestCaseStart(testCaseName);

      String jsonContent = TestUtils.objectToJson(testData);

      MvcResult actual = mockMvc.perform(
          put(url)
              .contentType(MediaType.APPLICATION_JSON)
              .header("test-token", true)
              .content(jsonContent))
          .andExpect(httpStatus)
          .andReturn();

      /* 3. then */
      JsonNode expectedToJson = TestUtils.apiReponseToJsonNode(expected);
      JsonNode actualToJson = TestUtils.mvcResultToJsonNode(actual);

      TestUtils.showLogTestCaseEnd(testData, expectedToJson, actualToJson);

      assertThat(actualToJson).isEqualTo(expectedToJson);
    }
  }

  @TestTemplate
  @ExtendWith(SmsTestCasesForDelete.class)
  @DisplayName("6. SMS_삭제")
  void SMS_삭제(TestCaseDetail<?> testCaseDetail) throws Exception {

    if (testCaseDetail.getUrl().equals("N/A")) {
      processNoneTestCase(testCaseDetail);
    } else {

      /* 1. given */
      String url = testCaseDetail.getUrl();
      String testCaseName = testCaseDetail.getTestName();
      Object testData = testCaseDetail.getTestData();
      ResponseApi<?> expected = testCaseDetail.getExpected();
      ResultMatcher httpStatus = testCaseDetail.getHttpStatus();

      /* 2. when */
      TestUtils.showLogTestCaseStart(testCaseName);

      MvcResult actual = mockMvc.perform(
          delete(url)
              .contentType(MediaType.APPLICATION_FORM_URLENCODED)
              .header("test-token", true))
          .andExpect(httpStatus)
          .andReturn();

      /* 3. then */
      JsonNode expectedToJson = TestUtils.apiReponseToJsonNode(expected);
      JsonNode actualToJson = TestUtils.mvcResultToJsonNode(actual);

      TestUtils.showLogTestCaseEnd(testData, expectedToJson, actualToJson);

      assertThat(actualToJson).isEqualTo(expectedToJson);
    }
  }

  void processNoneTestCase(TestCaseDetail<?> testCaseDetail) throws Exception {
    assertThat(testCaseDetail.getUrl()).isEqualTo("N/A");
    TestUtils.showLogNoneTestCaseEnd("N/A", "N/A", "N/A");

  }
}