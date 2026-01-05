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
import com.basic.app.featureTest.testcases.role.RoleMenuTestCasesForSearch;
import com.basic.app.featureTest.testcases.role.RoleMenuTestCasesForUpdate;
import com.basic.app.featureTest.testcases.role.RoleTestCasesForDelete;
import com.basic.app.featureTest.testcases.role.RoleTestCasesForInsert;
import com.basic.app.featureTest.testcases.role.RoleTestCasesForSearch;
import com.basic.app.featureTest.testcases.role.RoleTestCasesForUpdate;
import com.basic.app.featureTest.testcases.role.RoleTestCasesSearchAll;
import com.basic.app.featureTest.testcases.role.RoleUserTestCasesForDelete;
import com.basic.app.featureTest.testcases.role.RoleUserTestCasesForInsert;
import com.basic.app.featureTest.testcases.role.RoleUserTestCasesForSearch;
import com.basic.app.featureTest.testcases.role.RoleUserTestCasesForUserSearch;
import com.basic.app.util.TestCaseDetail;
import com.basic.app.util.TestCaseDetailSearchForm;
import com.basic.app.util.TestUtils;
import com.fasterxml.jackson.databind.JsonNode;

import lombok.extern.log4j.Log4j2;

/**
 * @파일명 : RoleTestTemplate.java
 * @설명 : 권한 정보 통합 테스트 템플릿 클래스
 * @작성자 : 김승연
 * @작성일 : 2025.07.23
 * @변경이력 :
 *       2025.07.23 김승연 최초 생성
 */
@SpringBootTest
@AutoConfigureMockMvc
@Log4j2
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // 클래스 단위로 테스트 인스턴스 생성
@Transactional
@Sql(scripts = {
    "classpath:sql/test-data/auth/auth-data.sql",
    "classpath:sql/test-data/role/role-test-data.sql"
}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@Sql(scripts = {
    "classpath:sql/test-data/role/cleanup-test-data.sql",
    "classpath:sql/test-data/auth/cleanup-test-data.sql"
}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_CLASS)
public class RoleTestTemplateTest {

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
  @ExtendWith(RoleTestCasesForSearch.class)
  @DisplayName("1. 권한_단건_조회")
  void 권한_단건_조회(TestCaseDetail<?> testCaseDetail) throws Exception {

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
  @ExtendWith(RoleTestCasesSearchAll.class)
  @DisplayName("2. 권한_N건_조회")
  void 권한_N건_조회(TestCaseDetailSearchForm<?, ?> testCaseDetail) throws Exception {

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
      multiValueMap.add("sort", pageRequest.getSort().toString());

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
  @ExtendWith(RoleTestCasesForInsert.class)
  @DisplayName("3. 권한_추가")
  void 권한_추가(TestCaseDetail<?> testCaseDetail) throws Exception {

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
  @ExtendWith(RoleTestCasesForUpdate.class)
  @DisplayName("4. 권한_수정")
  void 권한_수정(TestCaseDetail<?> testCaseDetail) throws Exception {

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
  @ExtendWith(RoleTestCasesForDelete.class)
  @DisplayName("5. 권한_삭제")
  void 권한_삭제(TestCaseDetail<?> testCaseDetail) throws Exception {

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

  @TestTemplate
  @ExtendWith(RoleMenuTestCasesForSearch.class)
  @DisplayName("6. 권한별_메뉴_조회")
  void 권한별_메뉴_조회(TestCaseDetail<?> testCaseDetail) throws Exception {

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
  @ExtendWith(RoleMenuTestCasesForUpdate.class)
  @DisplayName("7. 권한별_메뉴_수정")
  void 권한별_메뉴_수정(TestCaseDetail<?> testCaseDetail) throws Exception {

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
  @ExtendWith(RoleUserTestCasesForUserSearch.class)
  @DisplayName("8. 사용자별_권한_사용자_조회")
  void 사용자별_권한_사용자_조회(TestCaseDetailSearchForm<?, ?> testCaseDetail) throws Exception {

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
      multiValueMap.add("sort", pageRequest.getSort().toString());

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
  @ExtendWith(RoleUserTestCasesForSearch.class)
  @DisplayName("9. 사용자별_권한_조회")
  void 사용자별_권한_조회(TestCaseDetailSearchForm<?, ?> testCaseDetail) throws Exception {

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

      // PathVariable 사용하는 경우이므로 파라미터는 페이징만
      MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<String, String>();
      multiValueMap.add("page", String.valueOf(pageRequest.getPageNumber()));
      multiValueMap.add("size", String.valueOf(pageRequest.getPageSize()));
      // multiValueMap.add("sort", pageRequest.getSort().toString());

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
  @ExtendWith(RoleUserTestCasesForInsert.class)
  @DisplayName("10. 사용자별_권한_추가")
  void 사용자별_권한_추가(TestCaseDetail<?> testCaseDetail) throws Exception {

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
  @ExtendWith(RoleUserTestCasesForDelete.class)
  @DisplayName("11. 사용자별_권한_삭제")
  void 사용자별_권한_삭제(TestCaseDetail<?> testCaseDetail) throws Exception {

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
          delete(url)
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

  void processNoneTestCase(TestCaseDetail<?> testCaseDetail) throws Exception {
    assertThat(testCaseDetail.getUrl()).isEqualTo("N/A");
    TestUtils.showLogNoneTestCaseEnd("N/A", "N/A", "N/A");

  }
}