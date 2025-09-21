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
 * @파일명 : MenuTestCasesForUpdate.java
 * @설명 : 메뉴 정보 수정 테스트 케이스 클래스
 * @작성자 : 김승연
 * @작성일 : 2025.01.07
 * @변경이력 :
 *       2025.01.07 김승연 최초 생성
 */
public class MenuTestCasesForUpdate implements TestTemplateInvocationContextProvider {

  private final String BASE_URL = "/admin/menu";

  @Override
  public boolean supportsTestTemplate(ExtensionContext context) {
    return true;
  }

  @Override
  public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(ExtensionContext context) {

    List<TestCaseDetail<?>> testCases = new ArrayList<>();

    /*
     * [TC_ID : TC-040] [TC명 : 메뉴 정보 수정1] [REQ_ID : REQ_ADM_040] [화면 : 권한 관리 > 메뉴
     * 관리]
     * [기능 : 메뉴 수정] [테스트항목 : 정상 수정] [테스트 상세 : ]
     */

    MenuReqDto td1 = MenuReqDto.builder()
        .menuCd("[JUnit]SYS001_001")
        .menuNm("[JUnit]메뉴관리_수정")
        .upperMenu("[JUnit]SYS001") // 하위 메뉴로 변경
        .menuLv(2)
        .useYn("Y")
        .menuUrl("/admin/menu/modified")
        .orderNum(1)
        .build();

    MenuResDto ed1 = MenuResDto.builder()
        .menuCd("[JUnit]SYS001_001")
        .menuNm("[JUnit]메뉴관리_수정")
        .upperMenu("[JUnit]SYS001") // 하위 메뉴로 변경
        .menuLv(2)
        .useYn("Y")
        .menuUrl("/admin/menu/modified")
        .orderNum(1)
        .childMenus(new ArrayList<>())
        .build();

    testCases.add(
        new TestCaseDetail<>(BASE_URL, "정상 수정", td1,
            ResponseApi.success(Map.of("data", ed1)), status().isOk()));

    /*
     * [TC_ID : TC-041] [TC명 : 메뉴 정보 수정2] [REQ_ID : REQ_ADM_040] [화면 : 권한 관리 > 메뉴
     * 관리]
     * [기능 : 메뉴 수정] [테스트항목 : 필수값 누락] [테스트 상세 : ]
     */

    // menuCd 누락
    MenuReqDto td2 = MenuReqDto.builder()
        // menuCd 누락
        .menuNm("[JUnit]시스템관리_수정")
        .upperMenu("[JUnit]SYS001")
        .menuLv(1)
        .useYn("Y")
        .menuUrl("/admin/system")
        .orderNum(1)
        .build();

    testCases.add(
        new TestCaseDetail<>(BASE_URL, "필수값 누락 - menuCd", td2,
            ResponseApi.fail(ErrorCode.VALIDATION_ERROR_CLIENT, "메뉴코드는 필수입니다."), status().is4xxClientError()));

    // menuNm 누락
    MenuReqDto td3 = MenuReqDto.builder()
        .menuCd("[JUnit]SYS001")
        // menuNm 누락
        .upperMenu("[JUnit]SYS001")
        .menuLv(1)
        .useYn("Y")
        .menuUrl("/admin/system")
        .orderNum(1)
        .build();

    testCases.add(
        new TestCaseDetail<>(BASE_URL, "필수값 누락 - menuNm", td3,
            ResponseApi.fail(ErrorCode.VALIDATION_ERROR_CLIENT, "메뉴명은 필수입니다."), status().is4xxClientError()));

    // upperMenu 누락
    MenuReqDto td4 = MenuReqDto.builder()
        .menuCd("[JUnit]SYS001")
        .menuNm("[JUnit]시스템관리_수정")
        // upperMenu 누락
        .menuLv(1)
        .useYn("Y")
        .menuUrl("/admin/system")
        .orderNum(1)
        .build();

    testCases.add(
        new TestCaseDetail<>(BASE_URL, "필수값 누락 - upperMenu", td4,
            ResponseApi.fail(ErrorCode.VALIDATION_ERROR_CLIENT, "상위메뉴코드는 필수입니다."), status().is4xxClientError()));

    // useYn 누락
    MenuReqDto td5 = MenuReqDto.builder()
        .menuCd("[JUnit]SYS001")
        .menuNm("[JUnit]시스템관리_수정")
        .upperMenu("[JUnit]USR001")
        .menuLv(1)
        // useYn 누락
        .menuUrl("/admin/system")
        .orderNum(1)
        .build();

    testCases.add(
        new TestCaseDetail<>(BASE_URL, "필수값 누락 - useYn", td5,
            ResponseApi.fail(ErrorCode.VALIDATION_ERROR_CLIENT, "사용여부는 필수입니다."), status().is4xxClientError()));

    // orderNum 최소값 미만
    MenuReqDto td6 = MenuReqDto.builder()
        .menuCd("[JUnit]SYS001")
        .menuNm("[JUnit]시스템관리_수정")
        .upperMenu("[JUnit]USR001")
        .menuLv(1)
        .useYn("Y")
        .menuUrl("/admin/system")
        .orderNum(0) // 최소값 1 미만
        .build();

    testCases.add(
        new TestCaseDetail<>(BASE_URL, "필수값 오류 - orderNum 최소값", td6,
            ResponseApi.fail(ErrorCode.VALIDATION_ERROR_CLIENT, "정렬순서는 최소 1 이상이어야 합니다."), status().is4xxClientError()));

    /*
     * [TC_ID : TC-042] [TC명 : 메뉴 정보 수정3] [REQ_ID : REQ_ADM_040] [화면 : 권한 관리 > 메뉴
     * 관리]
     * [기능 : 메뉴 수정] [테스트항목 : 존재하지 않는 ID 수정 시도] [테스트 상세 : ]
     */

    MenuReqDto td7 = MenuReqDto.builder()
        .menuCd("[JUnit]NOT_EXIST")
        .menuNm("[JUnit]존재하지않는메뉴")
        .upperMenu("[JUnit]SYS001")
        .menuLv(1)
        .useYn("Y")
        .menuUrl("/not/exist")
        .orderNum(1)
        .build();

    testCases.add(
        new TestCaseDetail<>(BASE_URL, "존재하지 않는 ID 수정 시도", td7,
            ResponseApi.fail(ErrorCode.OBJECT_NOT_FOUND, "[JUnit]NOT_EXIST"), status().is4xxClientError()));

    /*
     * [TC_ID : TC-043] [TC명 : 메뉴 정보 수정4] [REQ_ID : REQ_ADM_040] [화면 : 권한 관리 > 메뉴
     * 관리]
     * [기능 : 메뉴 수정] [테스트항목 : 비즈니스로직 케이스] [테스트 상세 : ]
     */

    // 해당 케이스 미존재
    testCases.add(new TestCaseDetail<>("N/A", "비즈니스로직 케이스", null, null, status().isOk()));

    /*
     * [TC_ID : TC-044] [TC명 : 메뉴 정보 수정5] [REQ_ID : REQ_ADM_040] [화면 : 권한 관리 > 메뉴
     * 관리]
     * [기능 : 메뉴 수정] [테스트항목 : 잘못된 형식 입력] [테스트 상세 : ]
     */

    MenuReqDto td8 = MenuReqDto.builder()
        .menuCd("[JUnit]SYS001")
        .menuNm("") // 빈 문자열
        .upperMenu(null)
        .menuLv(1)
        .useYn("Y")
        .menuUrl("/admin/system")
        .orderNum(1)
        .build();

    testCases.add(
        new TestCaseDetail<>(BASE_URL + "/", "잘못된 형식 입력", td8,
            ResponseApi.fail(ErrorCode.PAGE_NOT_FOUND), status().is4xxClientError()));

    // 각 케이스에 대한 InvocationContext 생성
    return testCases.stream().map(testCase -> new TestTemplateInvocationContext() {
      @Override
      public String getDisplayName(int invocationIndex) {
        return "[" + invocationIndex + "] " + "메뉴 수정 : " + (testCase.getTestName());
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
