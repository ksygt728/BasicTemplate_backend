package com.basic.app.featureTest.testcases.role;

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
import com.basic.app.dto.requestDto.RoleUserReqDto;
import com.basic.app.dto.responseDto.RoleUserResDto;
import com.basic.app.dto.responseDto.RoleResDto;
import com.basic.app.exception.ErrorCode;
import com.basic.app.util.TestCaseDetail;

/**
 * @파일명 : RoleUserTestCasesForInsert.java
 * @설명 : 사용자별 권한 추가 테스트 케이스 클래스
 * @작성자 : 김승연
 * @작성일 : 2025.07.23
 * @변경이력 :
 *       2025.07.23 김승연 최초 생성
 */
public class RoleUserTestCasesForInsert implements TestTemplateInvocationContextProvider {

    private final String BASE_URL = "/admin/role/role-user";

    @Override
    public boolean supportsTestTemplate(ExtensionContext context) {
        return true;
    }

    @Override
    public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(ExtensionContext context) {

        List<TestCaseDetail<?>> testCases = new ArrayList<TestCaseDetail<?>>();

        /*
         * [TC_ID : TC-050] [TC명 : 사용자별 권한 추가1] [REQ_ID : REQ_ADM_050] [화면 : 권한 관리 >
         * 사용자별 권한] [기능 : 사용자별 권한 추가] [테스트항목 : 정상 등록] [테스트 상세 : ]
         */

        List<RoleUserReqDto> td1 = List.of(
                RoleUserReqDto.builder()
                        .roleCd("[JUnit]ROLE_DEVELOPER")
                        .userId("[JUnit]testuser002") // testuser002에게 개발자 권한 추가
                        .useYn("Y")
                        .build(),
                RoleUserReqDto.builder()
                        .roleCd("[JUnit]ROLE_VIEWER")
                        .userId("[JUnit]testuser003") // testuser003에게 조회자 권한 추가
                        .useYn("Y")
                        .build());

        List<RoleUserResDto> expected1 = List.of(
                RoleUserResDto.builder()
                        .userId("[JUnit]testuser002")
                        .useYn("Y")
                        .role(RoleResDto.builder()
                                .roleCd("[JUnit]ROLE_DEVELOPER")
                                .roleName("[JUnit]개발자")
                                .roleDesc("[JUnit]개발 업무 권한")
                                .build())
                        .build(),
                RoleUserResDto.builder()
                        .userId("[JUnit]testuser003")
                        .useYn("Y")
                        .role(RoleResDto.builder()
                                .roleCd("[JUnit]ROLE_VIEWER")
                                .roleName("[JUnit]조회자")
                                .roleDesc("[JUnit]읽기 전용 권한")
                                .build())
                        .build());

        testCases.add(
                new TestCaseDetail<>(BASE_URL, "정상 추가", Map.of("roleUsers", td1),
                        ResponseApi.success(Map.of("data", expected1)), status().isOk()));

        /*
         * [TC_ID : TC-051] [TC명 : 사용자별 권한 추가2] [REQ_ID : REQ_ADM_050] [화면 : 권한 관리 >
         * 사용자별 권한] [기능 : 사용자별 권한 추가] [테스트항목 : 필수값 누락] [테스트 상세 : ]
         */

        List<RoleUserReqDto> td2 = List.of(
                RoleUserReqDto.builder()
                        // roleCd 누락
                        .userId("[JUnit]testuser002")
                        .useYn("Y")
                        .build());

        testCases.add(
                new TestCaseDetail<>(BASE_URL, "필수값 누락 - roleCd", Map.of("roleUsers", td2),
                        ResponseApi.fail(ErrorCode.VALIDATION_ERROR_CLIENT, "권한코드는 필수입니다."),
                        status().is4xxClientError()));

        List<RoleUserReqDto> td3 = List.of(
                RoleUserReqDto.builder()
                        .roleCd("[JUnit]ROLE_DEVELOPER")
                        // userId 누락
                        .useYn("Y")
                        .build());

        testCases.add(
                new TestCaseDetail<>(BASE_URL, "필수값 누락 - userId", Map.of("roleUsers", td3),
                        ResponseApi.fail(ErrorCode.VALIDATION_ERROR_CLIENT, "사용자아이디는 필수입니다."),
                        status().is4xxClientError()));

        List<RoleUserReqDto> td4 = List.of(
                RoleUserReqDto.builder()
                        .roleCd("[JUnit]ROLE_DEVELOPER")
                        .userId("[JUnit]testuser002")
                        // useYn 누락
                        .build());

        testCases.add(
                new TestCaseDetail<>(BASE_URL, "필수값 누락 - useYn", Map.of("roleUsers", td4),
                        ResponseApi.fail(ErrorCode.VALIDATION_ERROR_CLIENT, "사용여부는 필수입니다."),
                        status().is4xxClientError()));

        /*
         * [TC_ID : TC-052] [TC명 : 사용자별 권한 추가3] [REQ_ID : REQ_ADM_050] [화면 : 권한 관리 >
         * 사용자별 권한] [기능 : 사용자별 권한 추가] [테스트항목 : 존재하는 권한 등록 시도] [테스트 상세 : ]
         */

        List<RoleUserReqDto> td5 = List.of(
                RoleUserReqDto.builder()
                        .roleCd("[JUnit]ROLE_ADMIN") // testuser001이 이미 가지고 있는 권한
                        .userId("[JUnit]testuser001")
                        .useYn("Y")
                        .build());

        testCases.add(
                new TestCaseDetail<>(BASE_URL, "이미 존재하는 권한 등록 시도", Map.of("roleUsers", td5),
                        ResponseApi.fail(ErrorCode.OBJECT_IS_EXISTED), status().is4xxClientError()));

        /*
         * [TC_ID : TC-053] [TC명 : 사용자별 권한 추가4] [REQ_ID : REQ_ADM_050] [화면 : 권한 관리 >
         * 사용자별 권한] [기능 : 사용자별 권한 추가] [테스트항목 : 존재하지 않는 권한코드] [테스트 상세 : ]
         */

        List<RoleUserReqDto> td6 = List.of(
                RoleUserReqDto.builder()
                        .roleCd("[JUnit]ROLE_NOT_EXIST") // 존재하지 않는 권한
                        .userId("[JUnit]testuser002")
                        .useYn("Y")
                        .build());

        testCases.add(
                new TestCaseDetail<>(BASE_URL, "존재하지 않는 권한코드", Map.of("roleUsers", td6),
                        ResponseApi.fail(ErrorCode.OBJECT_NOT_FOUND, "[JUnit]ROLE_NOT_EXIST"),
                        status().is4xxClientError()));

        /*
         * [TC_ID : TC-054] [TC명 : 사용자별 권한 추가5] [REQ_ID : REQ_ADM_050] [화면 : 권한 관리 >
         * 사용자별 권한] [기능 : 사용자별 권한 추가] [테스트항목 : 비즈니스로직 케이스] [테스트 상세 : ]
         */
        // 해당 케이스 미존재
        testCases.add(new TestCaseDetail<>("N/A", "비즈니스로직 케이스", null, null, status().isOk()));

        /*
         * [TC_ID : TC-055] [TC명 : 사용자별 권한 추가6] [REQ_ID : REQ_ADM_050] [화면 : 권한 관리 >
         * 사용자별 권한] [기능 : 사용자별 권한 추가] [테스트항목 : 잘못된 형식 입력] [테스트 상세 : ]
         */
        List<RoleUserReqDto> td7 = List.of(
                RoleUserReqDto.builder()
                        .roleCd("[JUnit]ROLE_DEVELOPER")
                        .userId("[JUnit]testuser002")
                        .useYn("") // 빈 문자열
                        .build());

        testCases.add(
                new TestCaseDetail<>(BASE_URL + "/", "잘못된 형식 입력", Map.of("roleUsers", td7),
                        ResponseApi.fail(ErrorCode.PAGE_NOT_FOUND), status().is4xxClientError()));

        // 각 케이스에 대한 InvocationContext 생성
        return testCases.stream().map(testCase -> new TestTemplateInvocationContext() {
            @Override
            public String getDisplayName(int invocationIndex) {
                return "[" + invocationIndex + "] " + "사용자별 권한 추가 : " + (testCase.getTestName());
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