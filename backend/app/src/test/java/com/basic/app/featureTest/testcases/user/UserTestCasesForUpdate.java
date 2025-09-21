package com.basic.app.featureTest.testcases.user;

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
import com.basic.app.dto.requestDto.UserReqDto;
import com.basic.app.dto.responseDto.CompanyResDto;
import com.basic.app.dto.responseDto.DepartmentResDto;
import com.basic.app.dto.responseDto.UserResDto;
import com.basic.app.exception.ErrorCode;
import com.basic.app.util.TestCaseDetail;

public class UserTestCasesForUpdate implements TestTemplateInvocationContextProvider {
        private final String BASE_URL = "/admin/user";

        @Override
        public boolean supportsTestTemplate(ExtensionContext context) {
                return true;
        }

        @Override
        public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(ExtensionContext context) {

                List<TestCaseDetail<?>> testCases = new ArrayList<TestCaseDetail<?>>();

                /*
                 * [TC_ID : TC-007] [TC명 : 사용자 정보 수정1] [REQ_ID : REQ_ADM_003] [화면 : 조직 관리 > 사용자
                 * 관리] [기능 : 사용자 정보 수정] [테스트항목 : 정상 수정] [테스트 상세 : ]
                 */

                UserReqDto td1 = UserReqDto.builder()
                                .userId("[JUnit]admin001")
                                .name("[JUnit]김관리")
                                .phoneNum("010-1234-1234") // 번호 수정
                                .email("admin001@company.com")
                                .userType("CBMS")
                                .gender("M")
                                .build();

                UserResDto ed1 = UserResDto.builder()
                                .userId("[JUnit]admin001")
                                .name("[JUnit]김관리")
                                .phoneNum("010-1234-1234")
                                .email("admin001@company.com")
                                .role("ROLE_GUEST")
                                .userType("CBMS")
                                .gender("M")
                                .department(
                                                DepartmentResDto.builder()
                                                                .deptCode("[JUnit]21323243")
                                                                .deptNm("TEST회사")
                                                                .upperDeptCode("20000000")
                                                                .deptLv(1)
                                                                .useYn("Y")
                                                                .company(
                                                                                CompanyResDto.builder()
                                                                                                .companyCode("[JUnit]C100")
                                                                                                .companyName("CBMS회사")
                                                                                                .build())
                                                                .build()

                                )
                                .build();

                testCases.add(
                                new TestCaseDetail<>(BASE_URL, "정상 수정", td1,
                                                ResponseApi.success(Map.of("data", ed1)), status().isOk()));
                /*
                 * [TC_ID : TC-008] [TC명 : 사용자 정보 수정2] [REQ_ID : REQ_ADM_003] [화면 : 조직 관리 > 사용자
                 * 관리] [기능 : 사용자 정보 수정] [테스트항목 : 필수값 누락] [테스트 상세 : ]
                 */
                UserReqDto td2 = UserReqDto.builder()
                                .name("[JUnit]김관리")
                                .phoneNum("010-1234-1234") // 번호 수정
                                .email("admin001@company.com")
                                .userType("CBMS")
                                .gender("M")
                                .build();
                testCases.add(
                                new TestCaseDetail<>(BASE_URL, "필수값 누락 : 아이디 누락", td2,
                                                ResponseApi.fail(ErrorCode.VALIDATION_ERROR_CLIENT, "사용자아이디는 필수입니다."),
                                                status().is4xxClientError()));

                UserReqDto td3 = UserReqDto.builder()
                                .userId("[JUnit]admin001")
                                .phoneNum("010-1234-1234") // 번호 수정
                                .email("admin001@company.com")
                                .userType("CBMS")
                                .gender("M")
                                .build();
                testCases.add(
                                new TestCaseDetail<>(BASE_URL, "필수값 누락 : 이름 누락", td3,
                                                ResponseApi.fail(ErrorCode.VALIDATION_ERROR_CLIENT, "이름은 필수입니다."),
                                                status().is4xxClientError()));

                UserReqDto td4 = UserReqDto.builder()
                                .userId("[JUnit]admin001")
                                .name("[JUnit]김관리")
                                .email("admin001@company.com")
                                .userType("CBMS")
                                .gender("M")
                                .build();
                testCases.add(
                                new TestCaseDetail<>(BASE_URL, "필수값 누락 : 번호 누락", td4,
                                                ResponseApi.fail(ErrorCode.VALIDATION_ERROR_CLIENT, "전화번호는 필수입니다."),
                                                status().is4xxClientError()));

                UserReqDto td5 = UserReqDto.builder()
                                .userId("[JUnit]admin001")
                                .name("김관리")
                                .phoneNum("010-1234-1234") // 번호 수정
                                .userType("CBMS")
                                .gender("M")
                                .build();
                testCases.add(
                                new TestCaseDetail<>(BASE_URL, "필수값 누락 : 메일 누락", td5,
                                                ResponseApi.fail(ErrorCode.VALIDATION_ERROR_CLIENT, "이메일은 필수입니다."),
                                                status().is4xxClientError()));

                UserReqDto td6 = UserReqDto.builder()
                                .userId("[JUnit]admin001")
                                .name("[JUnit]김관리")
                                .phoneNum("010-1234-1234") // 번호 수정
                                .email("admin001@company.com")
                                .gender("M")
                                .build();
                testCases.add(
                                new TestCaseDetail<>(BASE_URL, "필수값 누락 : 사용자타입 누락", td6,
                                                ResponseApi.fail(ErrorCode.VALIDATION_ERROR_CLIENT, "사용자타입은 필수입니다."),
                                                status().is4xxClientError()));

                UserReqDto td7 = UserReqDto.builder()
                                .userId("[JUnit]admin001")
                                .name("[JUnit]김관리")
                                .phoneNum("010-1234-1234") // 번호 수정
                                .email("admin001@company.com")
                                .userType("CBMS")
                                .build();
                testCases.add(
                                new TestCaseDetail<>(BASE_URL, "필수값 누락 : 성별 누락", td7,
                                                ResponseApi.fail(ErrorCode.VALIDATION_ERROR_CLIENT, "성별은 필수입니다."),
                                                status().is4xxClientError()));

                /*
                 * [TC_ID : TC-009] [TC명 : 사용자 정보 수정3] [REQ_ID : REQ_ADM_003] [화면 : 조직 관리 > 사용자
                 * 관리] [기능 : 사용자 정보 수정] [테스트항목 : 존재하지 않는 ID 수정 시도] [테스트 상세 : ]
                 */
                UserReqDto td8 = UserReqDto.builder()
                                .userId("[JUnit]admin001_NOT_EXIST")
                                .name("[JUnit]김관리")
                                .phoneNum("010-1234-1234") // 번호 수정
                                .email("admin001@company.com")
                                .userType("CBMS")
                                .gender("M")
                                .build();
                testCases.add(
                                new TestCaseDetail<>(BASE_URL, "존재하지 않는 ID 수정 시도", td8,
                                                ResponseApi.fail(ErrorCode.OBJECT_NOT_FOUND),
                                                status().is4xxClientError()));

                /*
                 * [TC_ID : TC-010] [TC명 : 사용자 정보 수정4] [REQ_ID : REQ_ADM_003] [화면 : 조직 관리 > 사용자
                 * 관리] [기능 : 사용자 정보 수정] [테스트항목 : 비즈니스로직 케이스] [테스트 상세 : ]
                 */

                // 메일 형식에 맞지 않은 경우
                UserReqDto td9 = UserReqDto.builder()
                                .userId("[JUnit]admin001")
                                .name("[JUnit]김관리")
                                .phoneNum("010-1234-1234") // 번호 수정
                                .email("admin001company.com") // 메일 형식 오류
                                .userType("CBMS")
                                .gender("M")
                                .build();
                testCases.add(
                                new TestCaseDetail<>(BASE_URL, "비즈니스로직 케이스", td9,
                                                ResponseApi.fail(ErrorCode.VALIDATION_ERROR_CLIENT,
                                                                "유효한 이메일 형식이 아닙니다."),
                                                status().is4xxClientError()));
                /*
                 * [TC_ID : TC-011] [TC명 : 사용자 정보 수정5] [REQ_ID : REQ_ADM_003] [화면 : 조직 관리 > 사용자
                 * 관리] [기능 : 사용자 정보 수정] [테스트항목 : 잘못된 형식 입력] [테스트 상세 : ]
                 */
                testCases.add(
                                new TestCaseDetail<>(BASE_URL + "/", "잘못된 형식 입력",
                                                td6,
                                                ResponseApi.fail(ErrorCode.PAGE_NOT_FOUND),
                                                status().is4xxClientError()));

                // 각 케이스에 대한 InvocationContext 생성
                return testCases.stream().map(testCase -> new TestTemplateInvocationContext() {
                        @Override
                        public String getDisplayName(int invocationIndex) {
                                return "[" + invocationIndex + "] " + "사용자 수정 : " + (testCase.getTestName());
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