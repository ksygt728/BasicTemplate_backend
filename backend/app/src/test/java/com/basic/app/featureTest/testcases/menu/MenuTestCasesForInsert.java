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

import com.basic.app.api.ResponseApi;
import com.basic.app.dto.requestDto.MenuReqDto;
import com.basic.app.dto.responseDto.MenuResDto;
import com.basic.app.exception.ErrorCode;
import com.basic.app.util.TestCaseDetail;

/**
 * @파일명 : MenuTestCasesForInsert.java
 * @설명 : 메뉴 정보 추가 테스트 케이스 클래스
 * @작성자 : 김승연
 * @작성일 : 2025.01.07
 * @변경이력 :
 *       2025.01.07 김승연 최초 생성
 */
public class MenuTestCasesForInsert implements TestTemplateInvocationContextProvider {

  private final String BASE_URL = "/admin/menu";

  @Override
  public boolean supportsTestTemplate(ExtensionContext context) {
    return true;
  }

  @Override
  public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(ExtensionContext context) {

    List<TestCaseDetail<?>> testCases = new ArrayList<>();

    /*
     * [TC_ID : TC-039] [TC명 : 메뉴 추가1] [REQ_ID : REQ_ADM_039] [화면 : 권한 관리 > 메뉴 관리]
     * [기능 : 메뉴 추가] [테스트항목 : 정상 등록] [테스트 상세 : ]
     */

    MenuReqDto td1 = MenuReqDto.builder()
        .menuCd("[JUnit]NEW001")
        .menuNm("[JUnit]새로운메뉴")
        .upperMenu("[JUnit]SYS001")
        .menuLv(2)
        .useYn("Y")
        .menuUrl("/new/menu")
        .orderNum(10)
        .build();

    MenuResDto ed1 = MenuResDto.builder()
        .menuCd("[JUnit]NEW001")
        .menuNm("[JUnit]새로운메뉴")
        .upperMenu("[JUnit]SYS001")
        .menuLv(2)
        .useYn("Y")
        .menuUrl("/new/menu")
        .orderNum(10)
        .childMenus(new ArrayList<>())
        .build();

    testCases.add(
        new TestCaseDetail<>(BASE_URL, "정상 추가", td1,
            ResponseApi.success(Map.of("data", ed1)), status().isOk()));

    /*
     * [TC_ID : TC-040] [TC명 : 메뉴 추가2] [REQ_ID : REQ_ADM_039] [화면 : 권한 관리 > 메뉴 관리]
     * [기능 : 메뉴 추가] [테스트항목 : 필수값 누락] [테스트 상세 : ]
     */

    // menuNm 누락
    MenuReqDto td2 = MenuReqDto.builder()
        .menuCd("[JUnit]NEW002")
        // menuNm 누락
        .upperMenu("[JUnit]SYS001")
        .menuLv(2)
        .useYn("Y")
        .menuUrl("/new/menu")
        .orderNum(10)
        .build();

    testCases.add(
        new TestCaseDetail<>(BASE_URL, "필수값 누락 - menuNm", td2,
            ResponseApi.fail(ErrorCode.VALIDATION_ERROR_CLIENT, "메뉴명은 필수입니다."), status().is4xxClientError()));

    // upperMenu 누락
    MenuReqDto td2_2 = MenuReqDto.builder()
        .menuCd("[JUnit]NEW002_2")
        .menuNm("[JUnit]새로운메뉴2")
        // upperMenu 누락
        .menuLv(2)
        .useYn("Y")
        .menuUrl("/new/menu")
        .orderNum(10)
        .build();

    testCases.add(
        new TestCaseDetail<>(BASE_URL, "필수값 누락 - upperMenu", td2_2,
            ResponseApi.fail(ErrorCode.VALIDATION_ERROR_CLIENT, "상위메뉴코드는 필수입니다."), status().is4xxClientError()));

    // useYn 누락
    MenuReqDto td2_3 = MenuReqDto.builder()
        .menuCd("[JUnit]NEW002_3")
        .menuNm("[JUnit]새로운메뉴3")
        .upperMenu("[JUnit]SYS001")
        .menuLv(2)
        // useYn 누락
        .menuUrl("/new/menu")
        .orderNum(10)
        .build();

    testCases.add(
        new TestCaseDetail<>(BASE_URL, "필수값 누락 - useYn", td2_3,
            ResponseApi.fail(ErrorCode.VALIDATION_ERROR_CLIENT, "사용여부는 필수입니다."), status().is4xxClientError()));

    // orderNum 0 (최소값 미만)
    MenuReqDto td2_4 = MenuReqDto.builder()
        .menuCd("[JUnit]NEW002_4")
        .menuNm("[JUnit]새로운메뉴4")
        .upperMenu("[JUnit]SYS001")
        .menuLv(2)
        .useYn("Y")
        .menuUrl("/new/menu")
        .orderNum(0) // 최소값 1 미만
        .build();

    testCases.add(
        new TestCaseDetail<>(BASE_URL, "필수값 오류 - orderNum 최소값", td2_4,
            ResponseApi.fail(ErrorCode.VALIDATION_ERROR_CLIENT, "정렬순서는 최소 1 이상이어야 합니다."), status().is4xxClientError()));

    /*
     * [TC_ID : TC-041] [TC명 : 메뉴 추가3] [REQ_ID : REQ_ADM_039] [화면 : 권한 관리 > 메뉴 관리]
     * [기능 : 메뉴 추가] [테스트항목 : 존재하는 ID 등록 시도] [테스트 상세 : ]
     */

    MenuReqDto td3 = MenuReqDto.builder()
        .menuCd("[JUnit]SYS001") // 이미 존재하는 키
        .menuNm("[JUnit]중복메뉴")
        .upperMenu("[JUnit]SYS001")
        .menuLv(1)
        .useYn("Y")
        .menuUrl(null)
        .orderNum(1)
        .build();

    testCases.add(
        new TestCaseDetail<>(BASE_URL, "존재하는 ID 등록 시도", td3,
            ResponseApi.fail(ErrorCode.OBJECT_IS_EXISTED, "[JUnit]SYS001"), status().is4xxClientError()));

    /*
     * [TC_ID : TC-042] [TC명 : 메뉴 추가4] [REQ_ID : REQ_ADM_039] [화면 : 권한 관리 > 메뉴 관리]
     * [기능 : 메뉴 추가] [테스트항목 : 비즈니스로직 케이스] [테스트 상세 : ]
     */

    // 해당 케이스 미존재
    testCases.add(new TestCaseDetail<>("N/A", "비즈니스로직 케이스", null, null, status().isOk()));

    /*
     * [TC_ID : TC-043] [TC명 : 메뉴 추가5] [REQ_ID : REQ_ADM_039] [화면 : 권한 관리 > 메뉴 관리]
     * [기능 : 메뉴 추가] [테스트항목 : 잘못된 형식 입력] [테스트 상세 : ]
     */
    MenuReqDto td4 = MenuReqDto.builder()
        .menuCd("[JUnit]NEW004")
        .menuNm("") // 빈 문자열
        .upperMenu("[JUnit]SYS001")
        .menuLv(2)
        .useYn("Y")
        .menuUrl("/new/menu")
        .orderNum(10)
        .build();

    testCases.add(
        new TestCaseDetail<>(BASE_URL + "/", "잘못된 형식 입력", td4,
            ResponseApi.fail(ErrorCode.PAGE_NOT_FOUND), status().is4xxClientError()));

    // 각 케이스에 대한 InvocationContext 생성
    return testCases.stream().map(testCase -> new TestTemplateInvocationContext() {
      @Override
      public String getDisplayName(int invocationIndex) {
        return "[" + invocationIndex + "] " + "메뉴 추가 : " + (testCase.getTestName());
      }

      @Override
      public List<Extension> getAdditionalExtensions() {
        return List.of(new ParameterResolver() {
          @Override
          public boolean supportsParameter(ParameterContext parameterContext,
              ExtensionContext extensionContext) {
            Class<?> type = parameterContext.getParameter().getType();
            // TestCaseDetail만 지원
            return TestCaseDetail.class.isAssignableFrom(type);
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
