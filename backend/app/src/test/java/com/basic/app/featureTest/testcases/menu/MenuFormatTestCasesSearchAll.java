package com.basic.app.featureTest.testcases.menu;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.junit.jupiter.api.extension.Extension;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.junit.jupiter.api.extension.TestTemplateInvocationContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContextProvider;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.basic.app.api.ResponseApi;
import com.basic.app.dto.requestDto.MenuReqDto;
import com.basic.app.dto.responseDto.MenuResDto;
import com.basic.app.util.TestCaseDetailSearchForm;

/**
 * @파일명 : MenuFormatTestCasesSearchAll.java
 * @설명 : 메뉴 목록 조회 테스트 케이스
 * @작성자 : 김승연
 * @작성일 : 2025.01.07
 * @변경이력 :
 *       2025.01.07 김승연 최초 생성
 */
public class MenuFormatTestCasesSearchAll implements TestTemplateInvocationContextProvider {

  private final String BASE_URL = "/admin/menu";

  @Override
  public boolean supportsTestTemplate(ExtensionContext context) {
    return true;
  }

  @Override
  public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(ExtensionContext context) {

    List<TestCaseDetailSearchForm<MenuReqDto, PageRequest>> testCases = new ArrayList<>();

    /*
     * [TC_ID : TC-001] [TC명 : 메뉴 정보 조회1] [REQ_ID : REQ_ADM_038] [화면 : 권한 관리 > 메뉴
     * 관리]
     * [기능 : 메뉴 리스트 조회] [테스트항목 : [N건] 리스트 페이징 조회] [테스트 상세 : JUnit 테스트 메뉴만 조회]
     */

    MenuReqDto td1 = MenuReqDto.builder()
        .menuCd("[JUnit]") // JUnit 메뉴만 검색하도록 설정
        .menuNm("")
        .upperMenu("")
        .useYn("")
        .menuUrl("")
        .build();

    PageRequest pr1 = PageRequest.of(0, 2000, Sort.by("menuCd").ascending());

    // 실제 API 응답 구조에 맞게 수정 - 페이징된 content 배열
    List<MenuResDto> content = List.of(
        MenuResDto.builder()
            .menuCd("[JUnit]SYS001")
            .menuNm("[JUnit]시스템관리")
            .upperMenu(null)
            .menuLv(1)
            .useYn("Y")
            .menuUrl(null)
            .orderNum(1)
            .childMenus(new ArrayList<>())
            .build());

    // 실제 API 응답 구조:
    // {"success":true,"errorCode":null,"message":"success","data":{"data":{"content":[...],
    // "page":0, "size":2000, "totalElements":1, "totalPages":1, "first":true,
    // "last":true}}}
    Map<String, Object> pageData = Map.of(
        "content", content,
        "page", 0,
        "size", 2000,
        "totalElements", 1,
        "totalPages", 1,
        "first", true,
        "last", true);

    ResponseApi<?> ed1 = ResponseApi.success(Map.of("data", pageData));

    testCases.add(
        new TestCaseDetailSearchForm<>(
            BASE_URL + "/search", "정상조회(N건)", td1, ed1, status().isOk(), pr1));

    // 각 케이스에 대한 InvocationContext 생성
    return testCases.stream().map(testCase -> new TestTemplateInvocationContext() {
      @Override
      public String getDisplayName(int invocationIndex) {
        return "메뉴 조회 : [" + invocationIndex + "] " + (testCase.getTestName());
      }

      @Override
      public List<Extension> getAdditionalExtensions() {
        return List.of(new ParameterResolver() {
          @Override
          public boolean supportsParameter(ParameterContext parameterContext,
              ExtensionContext extensionContext) {
            Class<?> type = parameterContext.getParameter().getType();
            // TestCaseDetailSearchForm만 지원
            return TestCaseDetailSearchForm.class.isAssignableFrom(type);
          }

          @Override
          public Object resolveParameter(ParameterContext parameterContext,
              ExtensionContext extensionContext) {
            return testCase;
          }
        });
      }
    });
  }
}
