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
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;

import com.basic.app.api.ResponseApi;
import com.basic.app.featureTest.testcases.chaebun.ChaebunFormatTestCasesSearchAll;
import com.basic.app.featureTest.testcases.chaebun.ChaebunTestCasesForDelete;
import com.basic.app.featureTest.testcases.chaebun.ChaebunTestCasesForInsert;
import com.basic.app.featureTest.testcases.chaebun.ChaebunTestCasesForSearch;
import com.basic.app.featureTest.testcases.chaebun.ChaebunTestCasesForUpdate;
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
    "classpath:sql/test-data/chaebun/chaebun-data.sql"
}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@Sql(scripts = "classpath:sql/test-data/chaebun/cleanup-test-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_CLASS)
public class ChaebunTestTemplate {

  @Autowired
  private MockMvc mockMvc;

  long startTime;

  @BeforeAll
  void setUpOnce() {
    log.info("\n📦 [CBSK-TEST] ***[테스트 전체 시작]*** {}", this.getClass().getName());
    // DB 스키마 초기화나 공통 설정 작업
  }

  @BeforeEach
  void beforeEach(TestInfo testInfo) {

    log.info("\n\n🔄 [CBSK-TEST] ***[테스트 시작]*** " + testInfo.getDisplayName());
    startTime = System.nanoTime();
  }

  @AfterEach
  void afterEach(TestInfo testInfo) {

    long endTime = System.nanoTime();
    long durationMs = (endTime - startTime) / 1_000_000;
    log.info("\n✅ [CBSK-TEST] ***[테스트 종료]*** (실행 시간: {} ms)\n", durationMs);

  }

  @AfterAll
  void cleanUpOnce() {
    log.info("\n🧹 [CBSK-TEST] ***[테스트 전체 종료]*** {}", this.getClass().getName());

  }

  /*************************************
   * [테스트 케이스]
   * 
   *************************************/
  @TestTemplate
  @ExtendWith(ChaebunTestCasesForSearch.class)
  @DisplayName("1. 채번_단건_조회")
  void 채번_단건_조회(TestCaseDetail<?> testCaseDetail) throws Exception {

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

      assertThat(expectedToJson).isEqualTo(actualToJson);
    }
  }

  @TestTemplate
  @ExtendWith(ChaebunFormatTestCasesSearchAll.class)
  @DisplayName("2. 채번_N건_조회")
  void 채번_N건_조회(TestCaseDetailSearchForm<?, ?> testCaseDetail) throws Exception {

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

      assertThat(expectedToJson).isEqualTo(actualToJson);
    }
  }

  @TestTemplate
  @ExtendWith(ChaebunTestCasesForInsert.class)
  @DisplayName("3. 채번_추가")
  void 채번_추가(TestCaseDetail<?> testCaseDetail) throws Exception {

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

      MultiValueMap<String, String> multiValueMap = TestUtils.dtoToMultiValueMap(testData);

      MvcResult actual = mockMvc.perform(
          post(url)
              .contentType(MediaType.APPLICATION_FORM_URLENCODED)
              .header("test-token", true)
              .params(multiValueMap))
          .andExpect(httpStatus)
          .andReturn();

      /* 3. then */
      JsonNode expectedToJson = TestUtils.apiReponseToJsonNode(expected);
      JsonNode actualToJson = TestUtils.mvcResultToJsonNode(actual);

      TestUtils.showLogTestCaseEnd(testData, expectedToJson, actualToJson);

      assertThat(expectedToJson).isEqualTo(actualToJson);
    }
  }

  @TestTemplate
  @ExtendWith(ChaebunTestCasesForUpdate.class)
  @DisplayName("4. 채번_수정")
  void 채번_수정(TestCaseDetail<?> testCaseDetail) throws Exception {

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

      MultiValueMap<String, String> multiValueMap = TestUtils.dtoToMultiValueMap(testData);

      MvcResult actual = mockMvc.perform(
          put(url)
              .contentType(MediaType.APPLICATION_FORM_URLENCODED)
              .header("test-token", true)
              .params(multiValueMap))
          .andExpect(httpStatus)
          .andReturn();

      /* 3. then */
      JsonNode expectedToJson = TestUtils.apiReponseToJsonNode(expected);
      JsonNode actualToJson = TestUtils.mvcResultToJsonNode(actual);

      TestUtils.showLogTestCaseEnd(testData, expectedToJson, actualToJson);

      assertThat(expectedToJson).isEqualTo(actualToJson);
    }
  }

  @TestTemplate
  @ExtendWith(ChaebunTestCasesForDelete.class)
  @DisplayName("5. 채번_삭제")
  void 채번_삭제(TestCaseDetail<?> testCaseDetail) throws Exception {

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

      assertThat(expectedToJson).isEqualTo(actualToJson);
    }
  }

  void processNoneTestCase(TestCaseDetail<?> testCaseDetail) throws Exception {
    assertThat(testCaseDetail.getUrl()).isEqualTo("N/A");
    TestUtils.showLogNoneTestCaseEnd("N/A", "N/A", "N/A");

  }
}