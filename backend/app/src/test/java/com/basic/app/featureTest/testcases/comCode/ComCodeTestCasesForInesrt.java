package com.basic.app.featureTest.testcases.comCode;

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
import com.basic.app.dto.requestDto.ComCodeDReqDto;
import com.basic.app.dto.requestDto.ComCodeMReqDto;
import com.basic.app.dto.requestDto.ComCodeTReqDto;
import com.basic.app.dto.responseDto.ComCodeDResDto;
import com.basic.app.dto.responseDto.ComCodeMResDto;
import com.basic.app.dto.responseDto.ComCodeTResDto;
import com.basic.app.exception.ErrorCode;
import com.basic.app.util.TestCaseDetail;

public class ComCodeTestCasesForInesrt implements TestTemplateInvocationContextProvider {
        private final String BASE_URL = "/admin/code";

        @Override
        public boolean supportsTestTemplate(ExtensionContext context) {
                return true;
        }

        @Override
        public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(ExtensionContext context) {
                List<TestCaseDetail<?>> testCases = new ArrayList<TestCaseDetail<?>>();

                /*
                 * TC_ID : TC-042
                 * TC명 : 그뤂 코드 추가1
                 * REQ_ID : REQ_ADM_010
                 * 화면 : 기준 정보 > 코드관리
                 * 기능 : 그뤂 코드 추가
                 * 테스트항목 : 정상 등록
                 * 테스트 상세 :
                 */
                ComCodeMReqDto td1 = ComCodeMReqDto.builder()
                                .grpCd("[JUnit]APPROVAL_STATUS_NEW")
                                .grpCdType("WORKFLOW")
                                .grpNm("승인 상태")
                                .build();
                ComCodeMResDto ed1 = ComCodeMResDto.builder()
                                .grpCd("[JUnit]APPROVAL_STATUS_NEW")
                                .grpCdType("WORKFLOW")
                                .grpNm("승인 상태")
                                .comCodeTs(new ArrayList<ComCodeTResDto>())
                                .build();
                testCases.add(
                                new TestCaseDetail<>(BASE_URL + "/group", "[그뤂코드] 정상 등록", td1,
                                                ResponseApi.success(Map.of("data", ed1)), status().isOk()));

                /*
                 * TC_ID : TC-043
                 * TC명 : 그뤂 코드 추가2
                 * REQ_ID : REQ_ADM_010
                 * 화면 : 기준 정보 > 코드관리
                 * 기능 : 그뤂 코드 추가
                 * 테스트항목 : 필수값 누락
                 * 테스트 상세 :
                 */
                ComCodeMReqDto td2 = ComCodeMReqDto.builder()
                                .grpCdType("WORKFLOW")
                                .grpNm("승인 상태")
                                .build();
                testCases.add(
                                new TestCaseDetail<>(BASE_URL + "/group", "[그뤂코드] 필수값 누락 : 그뤂코드 누락", td2,
                                                ResponseApi.fail(ErrorCode.VALIDATION_ERROR_CLIENT, "그뤂코드는 필수입니다."),
                                                status().is4xxClientError()));

                ComCodeMReqDto td3 = ComCodeMReqDto.builder()
                                .grpCd("[JUnit]APPROVAL_STATUS_NEW")
                                .grpNm("승인 상태")
                                .build();
                testCases.add(
                                new TestCaseDetail<>(BASE_URL + "/group", "[그뤂코드] 필수값 누락 : 그뤂타입 누락", td3,
                                                ResponseApi.fail(ErrorCode.VALIDATION_ERROR_CLIENT, "그뤂코드유형은 필수입니다."),
                                                status().is4xxClientError()));

                ComCodeMReqDto td4 = ComCodeMReqDto.builder()
                                .grpCd("[JUnit]APPROVAL_STATUS_NEW")
                                .grpCdType("WORKFLOW")
                                .build();
                testCases.add(
                                new TestCaseDetail<>(BASE_URL + "/group", "[그뤂코드] 필수값 누락 : 그뤂명 누락", td4,
                                                ResponseApi.fail(ErrorCode.VALIDATION_ERROR_CLIENT, "그뤂코드명은 필수입니다."),
                                                status().is4xxClientError()));

                /*
                 * TC_ID : TC-044
                 * TC명 : 그뤂 코드 추가3
                 * REQ_ID : REQ_ADM_010
                 * 화면 : 기준 정보 > 코드관리
                 * 기능 : 그뤂 코드 추가
                 * 테스트항목 : 존재하는 ID 등록 시도
                 * 테스트 상세 :
                 */
                ComCodeMReqDto td5 = ComCodeMReqDto.builder()
                                .grpCd("[JUnit]APPROVAL_STATUS")
                                .grpCdType("WORKFLOW")
                                .grpNm("승인 상태")
                                .build();
                testCases.add(
                                new TestCaseDetail<>(BASE_URL + "/group", "[그뤂코드] 존재하는 ID 등록 시도", td5,
                                                ResponseApi.fail(ErrorCode.OBJECT_IS_EXISTED),
                                                status().is4xxClientError()));

                /*
                 * TC_ID : TC-045
                 * TC명 : 그뤂 코드 추가4
                 * REQ_ID : REQ_ADM_010
                 * 화면 : 기준 정보 > 코드관리
                 * 기능 : 그뤂 코드 추가
                 * 테스트항목 : 비즈니스로직 케이스
                 * 테스트 상세 :
                 */
                // 해당 케이스 미존재
                testCases.add(new TestCaseDetail<>("N/A", "비즈니스로직 케이스", null, null, status().isOk()));

                /*
                 * TC_ID : TC-046
                 * TC명 : 그뤂 코드 추가5
                 * REQ_ID : REQ_ADM_010
                 * 화면 : 기준 정보 > 코드관리
                 * 기능 : 그뤂 코드 추가
                 * 테스트항목 : 잘못된 형식 입력
                 * 테스트 상세 :
                 */

                ComCodeMReqDto td21 = ComCodeMReqDto.builder()
                                .grpCd("[JUnit]APPROVAL_STATUS_NEW")
                                .grpCdType("WORKFLOW")
                                .grpNm("승인 상태")
                                .build();
                testCases.add(
                                new TestCaseDetail<>(BASE_URL + "/", "[그뤂코드] 잘못된 형식 입력",
                                                td21,
                                                ResponseApi.fail(ErrorCode.PAGE_NOT_FOUND),
                                                status().is4xxClientError()));
                /*
                 * TC_ID : TC-062
                 * TC명 : 속성 코드 추가1
                 * REQ_ID : REQ_ADM_014
                 * 화면 : 기준 정보 > 코드관리
                 * 기능 : 속성 코드 추가
                 * 테스트항목 : 정상 등록
                 * 테스트 상세 :
                 */
                ComCodeTReqDto td6 = ComCodeTReqDto.builder()
                                .grpCd("[JUnit]APPROVAL_STATUS")
                                .attrCd("[JUnit]LEVEL_NEW")
                                .attrNm("승인 상태추가 테스트")
                                .orderNum(5)
                                .build();

                ComCodeTResDto ed6 = ComCodeTResDto.builder()
                                .attrCd("[JUnit]LEVEL_NEW")
                                .attrNm("승인 상태추가 테스트")
                                .orderNum(5)
                                .comCodeDs(new ArrayList<ComCodeDResDto>())
                                .build();

                testCases.add(
                                new TestCaseDetail<>(BASE_URL + "/attribute", "[속성코드] 정상 등록", td6,
                                                ResponseApi.success(Map.of("data", ed6)), status().isOk()));

                /*
                 * TC_ID : TC-063
                 * TC명 : 속성 코드 추가2
                 * REQ_ID : REQ_ADM_014
                 * 화면 : 기준 정보 > 코드관리
                 * 기능 : 속성 코드 추가
                 * 테스트항목 : 필수값 누락
                 * 테스트 상세 :
                 */

                ComCodeTReqDto td7 = ComCodeTReqDto.builder()
                                .attrCd("[JUnit]LEVEL_NEW")
                                .attrNm("승인 상태추가 테스트")
                                .orderNum(5)
                                .build();
                testCases.add(
                                new TestCaseDetail<>(BASE_URL + "/attribute", "[속성코드] 필수값 누락 | 그뤂코드 누락", td7,
                                                ResponseApi.fail(ErrorCode.VALIDATION_ERROR_CLIENT, "그뤂코드는 필수입니다."),
                                                status().is4xxClientError()));

                ComCodeTReqDto td8 = ComCodeTReqDto.builder()
                                .grpCd("[JUnit]APPROVAL_STATUS")
                                .attrNm("승인 상태추가 테스트")
                                .orderNum(5)
                                .build();
                testCases.add(
                                new TestCaseDetail<>(BASE_URL + "/attribute", "[속성코드] 필수값 누락 | 속성코드 누락", td8,
                                                ResponseApi.fail(ErrorCode.VALIDATION_ERROR_CLIENT, "속성코드는 필수입니다."),
                                                status().is4xxClientError()));

                ComCodeTReqDto td9 = ComCodeTReqDto.builder()
                                .grpCd("[JUnit]APPROVAL_STATUS")
                                .attrCd("[JUnit]LEVEL_NEW")
                                .orderNum(5)
                                .build();
                testCases.add(
                                new TestCaseDetail<>(BASE_URL + "/attribute", "[속성코드] 필수값 누락 | 속성명 누락", td9,
                                                ResponseApi.fail(ErrorCode.VALIDATION_ERROR_CLIENT, "속성명은 필수입니다."),
                                                status().is4xxClientError()));

                ComCodeTReqDto td10 = ComCodeTReqDto.builder()
                                .grpCd("[JUnit]APPROVAL_STATUS")
                                .attrCd("[JUnit]LEVEL_NEW")
                                .attrNm("승인 상태추가 테스트")
                                .build();
                testCases.add(
                                new TestCaseDetail<>(BASE_URL + "/attribute", "[속성코드] 필수값 누락 | 정렬순서 누락", td10,
                                                ResponseApi.fail(ErrorCode.VALIDATION_ERROR_CLIENT,
                                                                "정렬순서는 1 이상 필수입니다."),
                                                status().is4xxClientError()));

                /*
                 * TC_ID : TC-064
                 * TC명 : 속성 코드 추가3
                 * REQ_ID : REQ_ADM_014
                 * 화면 : 기준 정보 > 코드관리
                 * 기능 : 속성 코드 추가
                 * 테스트항목 : 존재하는 ID 등록 시도
                 * 테스트 상세 :
                 */
                ComCodeTReqDto td20 = ComCodeTReqDto.builder()
                                .grpCd("[JUnit]APPROVAL_STATUS")
                                .attrCd("[JUnit]PRIORITY")
                                .attrNm("승인 상태추가 테스트")
                                .orderNum(5)
                                .build();
                testCases.add(
                                new TestCaseDetail<>(BASE_URL + "/attribute", "[속성코드] 존재하는 ID 등록 시도", td20,
                                                ResponseApi.fail(ErrorCode.OBJECT_IS_EXISTED),
                                                status().is4xxClientError()));

                /*
                 * TC_ID : TC-065
                 * TC명 : 속성 코드 추가4
                 * REQ_ID : REQ_ADM_014
                 * 화면 : 기준 정보 > 코드관리
                 * 기능 : 속성 코드 추가
                 * 테스트항목 : 비즈니스로직 케이스
                 * 테스트 상세 :
                 */
                ComCodeTReqDto td23 = ComCodeTReqDto.builder()
                                .grpCd("[JUnit]APPROVAL_STATUS_IS_NOT_EXIST") // 그뤂코드가 없는 경우
                                .attrCd("[JUnit]PRIORITY")
                                .attrNm("승인 상태추가 테스트")
                                .orderNum(5)
                                .build();
                testCases.add(
                                new TestCaseDetail<>(BASE_URL + "/attribute", "[속성코드] 비즈니스로직 케이스 | 그뤂코드가 없는 경우", td23,
                                                ResponseApi.fail(ErrorCode.OBJECT_NOT_FOUND),
                                                status().is4xxClientError()));

                /*
                 * TC_ID : TC-066
                 * TC명 : 속성 코드 추가5
                 * REQ_ID : REQ_ADM_014
                 * 화면 : 기준 정보 > 코드관리
                 * 기능 : 속성 코드 추가
                 * 테스트항목 : 잘못된 형식 입력
                 * 테스트 상세 :
                 */
                ComCodeTReqDto td22 = ComCodeTReqDto.builder()
                                .grpCd("[JUnit]APPROVAL_STATUS")
                                .attrCd("[JUnit]LEVEL_NEW")
                                .attrNm("승인 상태추가 테스트")
                                .build();
                testCases.add(
                                new TestCaseDetail<>(BASE_URL + "/", "[속성코드] 잘못된 형식 입력",
                                                td22,
                                                ResponseApi.fail(ErrorCode.PAGE_NOT_FOUND),
                                                status().is4xxClientError()));

                /*
                 * TC_ID : TC-082
                 * TC명 : 상세코드 추가1
                 * REQ_ID : REQ_ADM_018
                 * 화면 : 기준 정보 > 코드관리
                 * 기능 : 상세코드 추가
                 * 테스트항목 : 정상 등록
                 * 테스트 상세 :
                 */
                ComCodeDReqDto td11 = ComCodeDReqDto.builder()
                                .grpCd("[JUnit]APPROVAL_STATUS")
                                .attrCd("[JUnit]STATUS")
                                .dtlCd("[JUnit]APPROVED_NEW")
                                .dtlNm("승인완료")
                                .useYn("Y")
                                .orderNum(1)
                                .build();

                ComCodeDResDto ed11 = ComCodeDResDto.builder()
                                .dtlCd("[JUnit]APPROVED_NEW")
                                .dtlNm("승인완료")
                                .useYn("Y")
                                .orderNum(1)
                                .build();
                testCases.add(
                                new TestCaseDetail<>(BASE_URL + "/detail", "[상세코드] 정상 등록", td11,
                                                ResponseApi.success(Map.of("data", ed11)), status().isOk()));

                /*
                 * TC_ID : TC-083
                 * TC명 : 상세코드 추가2
                 * REQ_ID : REQ_ADM_018
                 * 화면 : 기준 정보 > 코드관리
                 * 기능 : 상세코드 추가
                 * 테스트항목 : 필수값 누락
                 * 테스트 상세 :
                 */

                ComCodeDReqDto td12 = ComCodeDReqDto.builder()
                                .attrCd("[JUnit]STATUS")
                                .dtlCd("[JUnit]APPROVED_NEW")
                                .dtlNm("승인완료")
                                .useYn("Y")
                                .orderNum(1)
                                .build();
                testCases.add(
                                new TestCaseDetail<>(BASE_URL + "/detail", "[상세코드] 필수값 누락 | grpCd 누락", td12,
                                                ResponseApi.fail(ErrorCode.VALIDATION_ERROR_CLIENT, "그뤂코드는 필수입니다."),
                                                status().is4xxClientError()));

                // 2. attrCd 누락
                ComCodeDReqDto td13 = ComCodeDReqDto.builder()
                                .grpCd("[JUnit]APPROVAL_STATUS")
                                .dtlCd("[JUnit]APPROVED_NEW")
                                .dtlNm("승인완료")
                                .useYn("Y")
                                .orderNum(1)
                                .build();
                testCases.add(
                                new TestCaseDetail<>(BASE_URL + "/detail", "[상세코드] 필수값 누락 | attrCd 누락", td13,
                                                ResponseApi.fail(ErrorCode.VALIDATION_ERROR_CLIENT, "속성코드는 필수입니다."),
                                                status().is4xxClientError()));

                // 3. dtlCd 누락
                ComCodeDReqDto td14 = ComCodeDReqDto.builder()
                                .grpCd("[JUnit]APPROVAL_STATUS")
                                .attrCd("[JUnit]STATUS")
                                .dtlNm("승인완료")
                                .useYn("Y")
                                .orderNum(1)
                                .build();
                testCases.add(
                                new TestCaseDetail<>(BASE_URL + "/detail", "[상세코드] 필수값 누락 | dtlCd 누락", td14,
                                                ResponseApi.fail(ErrorCode.VALIDATION_ERROR_CLIENT, "상세코드는 필수입니다."),
                                                status().is4xxClientError()));

                // 4. dtlNm 누락
                ComCodeDReqDto td15 = ComCodeDReqDto.builder()
                                .grpCd("[JUnit]APPROVAL_STATUS")
                                .attrCd("[JUnit]STATUS")
                                .dtlCd("[JUnit]APPROVED_NEW")
                                .useYn("Y")
                                .orderNum(1)
                                .build();
                testCases.add(
                                new TestCaseDetail<>(BASE_URL + "/detail", "[상세코드] 필수값 누락 | dtlNm 누락", td15,
                                                ResponseApi.fail(ErrorCode.VALIDATION_ERROR_CLIENT, "상세코드명 필수입니다."),
                                                status().is4xxClientError()));

                // 5. useYn 누락
                ComCodeDReqDto td16 = ComCodeDReqDto.builder()
                                .grpCd("[JUnit]APPROVAL_STATUS")
                                .attrCd("[JUnit]STATUS")
                                .dtlCd("[JUnit]APPROVED_NEW")
                                .dtlNm("승인완료")
                                .orderNum(1)
                                .build();
                testCases.add(
                                new TestCaseDetail<>(BASE_URL + "/detail", "[상세코드] 필수값 누락 | useYn 누락", td16,
                                                ResponseApi.fail(ErrorCode.VALIDATION_ERROR_CLIENT, "사용여부는 필수입니다."),
                                                status().is4xxClientError()));

                // 6. orderNum 누락
                ComCodeDReqDto td17 = ComCodeDReqDto.builder()
                                .grpCd("[JUnit]APPROVAL_STATUS")
                                .attrCd("[JUnit]STATUS")
                                .dtlCd("[JUnit]APPROVED_NEW")
                                .dtlNm("승인완료")
                                .useYn("Y")
                                .build();
                testCases.add(
                                new TestCaseDetail<>(BASE_URL + "/detail", "[상세코드] 필수값 누락 | orderNum 누락", td17,
                                                ResponseApi.fail(ErrorCode.VALIDATION_ERROR_CLIENT, "정렬순서는 1이상 필수입니다."),
                                                status().is4xxClientError()));

                /*
                 * TC_ID : TC-084
                 * TC명 : 상세코드 추가3
                 * REQ_ID : REQ_ADM_018
                 * 화면 : 기준 정보 > 코드관리
                 * 기능 : 상세코드 추가
                 * 테스트항목 : 존재하는 ID 등록 시도
                 * 테스트 상세 :
                 */
                ComCodeDReqDto td18 = ComCodeDReqDto.builder()
                                .grpCd("[JUnit]APPROVAL_STATUS")
                                .attrCd("[JUnit]STATUS")
                                .dtlCd("[JUnit]APPROVED")
                                .dtlNm("승인완료")
                                .useYn("Y")
                                .orderNum(1)
                                .build();
                testCases.add(
                                new TestCaseDetail<>(BASE_URL + "/detail", "[상세코드] 존재하는 ID 등록 시도", td18,
                                                ResponseApi.fail(ErrorCode.OBJECT_IS_EXISTED),
                                                status().is4xxClientError()));
                /*
                 * TC_ID : TC-085
                 * TC명 : 상세코드 추가4
                 * REQ_ID : REQ_ADM_018
                 * 화면 : 기준 정보 > 코드관리
                 * 기능 : 상세코드 추가
                 * 테스트항목 : 비즈니스로직 케이스
                 * 테스트 상세 :
                 */
                ComCodeDReqDto td25 = ComCodeDReqDto.builder()
                                .grpCd("[JUnit]APPROVAL_STATUS_NOT_EXIST") // 그뤂코드가 없는 경우
                                .attrCd("[JUnit]STATUS")
                                .dtlCd("[JUnit]APPROVED")
                                .dtlNm("승인완료")
                                .useYn("Y")
                                .orderNum(1)
                                .build();
                testCases.add(
                                new TestCaseDetail<>(BASE_URL + "/detail", "[상세코드] 비즈니스로직 케이스 | 그뤂코드가 없는데 등록하는 경우",
                                                td25,
                                                ResponseApi.fail(ErrorCode.OBJECT_NOT_FOUND),
                                                status().is4xxClientError()));

                ComCodeDReqDto td27 = ComCodeDReqDto.builder()
                                .grpCd("[JUnit]APPROVAL_STATUS")
                                .attrCd("[JUnit]STATUS_NOT_EXIST") // 속성코드가 없는 경우
                                .dtlCd("[JUnit]APPROVED")
                                .dtlNm("승인완료")
                                .useYn("Y")
                                .orderNum(1)
                                .build();
                testCases.add(
                                new TestCaseDetail<>(BASE_URL + "/detail", "[상세코드] 비즈니스로직 케이스 | 속성코드가 없는데 등록하는 경우",
                                                td27,
                                                ResponseApi.fail(ErrorCode.OBJECT_NOT_FOUND),
                                                status().is4xxClientError()));
                /*
                 * /*
                 * TC_ID : TC-086
                 * TC명 : 상세코드 추가5
                 * REQ_ID : REQ_ADM_018
                 * 화면 : 기준 정보 > 코드관리
                 * 기능 : 상세코드 추가
                 * 테스트항목 : 잘못된 형식 입력
                 * 테스트 상세 :
                 */
                ComCodeDReqDto td26 = ComCodeDReqDto.builder()
                                .attrCd("[JUnit]STATUS")
                                .dtlCd("[JUnit]APPROVED_NEW")
                                .dtlNm("승인완료")
                                .useYn("Y")
                                .orderNum(1)
                                .build();
                testCases.add(
                                new TestCaseDetail<>(BASE_URL + "/", "[상세코드] 잘못된 형식 입력",
                                                td26,
                                                ResponseApi.fail(ErrorCode.PAGE_NOT_FOUND),
                                                status().is4xxClientError()));

                // 각 케이스에 대한 InvocationContext 생성
                return testCases.stream().map(testCase -> new TestTemplateInvocationContext() {
                        @Override
                        public String getDisplayName(int invocationIndex) {
                                return "[" + invocationIndex + "] " + "코드 추가 : " + (testCase.getTestName());
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