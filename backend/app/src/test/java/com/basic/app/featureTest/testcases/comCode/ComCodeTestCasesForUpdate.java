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

public class ComCodeTestCasesForUpdate implements TestTemplateInvocationContextProvider {
        private final String BASE_URL = "/admin/code";

        @Override
        public boolean supportsTestTemplate(ExtensionContext context) {
                return true;
        }

        @Override
        public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(ExtensionContext context) {

                List<TestCaseDetail<?>> testCases = new ArrayList<TestCaseDetail<?>>();

                /*
                 * [TC_ID : TC-047] [TC명 : 그뤂 코드 수정1] [REQ_ID : REQ_ADM_011] [화면 : 기준 정보 > 코드
                 * 관리] [기능 : 그뤂 코드 수정] [테스트항목 : 정상 수정] [테스트 상세 : ]
                 */
                ComCodeMReqDto td1 = ComCodeMReqDto.builder()
                                .grpCd("[JUnit]ASSET_STATUS")
                                .grpCdType("COMMON")
                                .grpNm("승인 상태(수정)")
                                .build();
                ComCodeMResDto ed1 = ComCodeMResDto.builder()
                                .grpCd("[JUnit]ASSET_STATUS")
                                .grpCdType("COMMON")
                                .grpNm("승인 상태(수정)")
                                .comCodeTs(new ArrayList<ComCodeTResDto>())
                                .build();
                testCases.add(
                                new TestCaseDetail<>(BASE_URL + "/group", "[그뤂코드] 정상 수정", td1,
                                                ResponseApi.success(Map.of("data", ed1)), status().isOk()));
                /*
                 * [TC_ID : TC-048] [TC명 : 그뤂 코드 수정2] [REQ_ID : REQ_ADM_011] [화면 : 기준 정보 > 코드
                 * 관리] [기능 : 그뤂 코드 수정] [테스트항목 : 필수값 누락] [테스트 상세 : ]
                 */
                ComCodeMReqDto td2 = ComCodeMReqDto.builder()
                                .grpCdType("COMMON")
                                .grpNm("자산 상태(수정)")
                                .build();
                testCases.add(
                                new TestCaseDetail<>(BASE_URL + "/group", "[그뤂코드] 필수값 누락 : 그뤂코드 누락", td2,
                                                ResponseApi.fail(ErrorCode.VALIDATION_ERROR_CLIENT, "그뤂코드는 필수입니다."),
                                                status().is4xxClientError()));

                ComCodeMReqDto td3 = ComCodeMReqDto.builder()
                                .grpCd("[JUnit]ASSET_STATUS")
                                .grpNm("자산 상태(수정)")
                                .build();
                testCases.add(
                                new TestCaseDetail<>(BASE_URL + "/group", "[그뤂코드] 필수값 누락 : 그뤂타입 누락", td3,
                                                ResponseApi.fail(ErrorCode.VALIDATION_ERROR_CLIENT, "그뤂코드유형은 필수입니다."),
                                                status().is4xxClientError()));

                ComCodeMReqDto td4 = ComCodeMReqDto.builder()
                                .grpCd("[JUnit]ASSET_STATUS")
                                .grpCdType("COMMON_UPDATE")
                                .build();
                testCases.add(
                                new TestCaseDetail<>(BASE_URL + "/group", "[그뤂코드] 필수값 누락 : 그뤂명 누락", td4,
                                                ResponseApi.fail(ErrorCode.VALIDATION_ERROR_CLIENT, "그뤂코드명은 필수입니다."),
                                                status().is4xxClientError()));
                /*
                 * [TC_ID : TC-049] [TC명 : 그뤂 코드 수정3] [REQ_ID : REQ_ADM_011] [화면 : 기준 정보 > 코드
                 * 관리] [기능 : 그뤂 코드 수정] [테스트항목 : 존재하지 않는 ID 수정 시도] [테스트 상세 : ]
                 */
                ComCodeMReqDto td5 = ComCodeMReqDto.builder()
                                .grpCd("[JUnit]ASSET_STATUS_NOT_EXIST")
                                .grpCdType("COMMON")
                                .grpNm("자산 상태(수정)")
                                .build();
                testCases.add(
                                new TestCaseDetail<>(BASE_URL + "/group", "[그뤂코드] 존재하지 않는 ID 수정 시도", td5,
                                                ResponseApi.fail(ErrorCode.OBJECT_NOT_FOUND),
                                                status().is4xxClientError()));

                /*
                 * [TC_ID : TC-050] [TC명 : 그뤂 코드 수정4] [REQ_ID : REQ_ADM_011] [화면 : 기준 정보 > 코드
                 * 관리] [기능 : 그뤂 코드 수정] [테스트항목 : 비즈니스로직 케이스] [테스트 상세 : ]
                 */
                // 해당 케이스 없음
                testCases.add(
                                new TestCaseDetail<>("N/A", "[그뤂코드] 비즈니스로직 케이스", td5,
                                                ResponseApi.fail(ErrorCode.OBJECT_NOT_FOUND),
                                                status().is4xxClientError()));
                /*
                 * [TC_ID : TC-051] [TC명 : 그뤂 코드 수정5] [REQ_ID : REQ_ADM_011] [화면 : 기준 정보 > 코드
                 * 관리] [기능 : 그뤂 코드 수정] [테스트항목 : 잘못된 형식 입력] [테스트 상세 : ]
                 */
                ComCodeMReqDto td6 = ComCodeMReqDto.builder()
                                .grpCd("[JUnit]ASSET_STATUS_NEW")
                                .grpCdType("COMMON")
                                .grpNm("자산 상태(수정)")
                                .build();
                testCases.add(
                                new TestCaseDetail<>(BASE_URL + "/", "[그뤂코드] 잘못된 형식 입력",
                                                td6,
                                                ResponseApi.fail(ErrorCode.PAGE_NOT_FOUND),
                                                status().is4xxClientError()));
                /**
                 * 
                 * 
                 * 
                 * 
                 * 
                 * 
                 * 
                 * 
                 * 
                 */
                /*
                 * [TC_ID : TC-067] [TC명 : 속성 코드 수정1] [REQ_ID : REQ_ADM_015] [화면 : 기준 정보 > 코드
                 * 관리] [기능 : 속성 코드 수정] [테스트항목 : 정상 수정] [테스트 상세 : ]
                 */
                ComCodeTReqDto td7 = ComCodeTReqDto.builder()
                                .grpCd("[JUnit]CUSTOMER_TYPE")
                                .attrCd("[JUnit]GRADE")
                                .attrNm("고객등급(수정)")
                                .orderNum(5)
                                .build();

                ComCodeTResDto ed7 = ComCodeTResDto.builder()
                                .attrCd("[JUnit]GRADE")
                                .attrNm("고객등급(수정)")
                                .orderNum(5)
                                .comCodeDs(new ArrayList<ComCodeDResDto>())
                                .build();

                testCases.add(
                                new TestCaseDetail<>(BASE_URL + "/attribute", "[속성코드] 정상 수정", td7,
                                                ResponseApi.success(Map.of("data", ed7)), status().isOk()));

                /*
                 * [TC_ID : TC-068] [TC명 : 속성 코드 수정2] [REQ_ID : REQ_ADM_015] [화면 : 기준 정보 > 코드
                 * 관리] [기능 : 속성 코드 수정] [테스트항목 : 필수값 누락] [테스트 상세 : ]
                 */
                ComCodeTReqDto td11 = ComCodeTReqDto.builder()
                                .attrCd("[JUnit]LOCATION")
                                .attrNm("승인 상태수정 테스트")
                                .orderNum(5)
                                .build();
                testCases.add(
                                new TestCaseDetail<>(BASE_URL + "/attribute", "[속성코드] 필수값 누락 | 그뤂코드 누락", td11,
                                                ResponseApi.fail(ErrorCode.VALIDATION_ERROR_CLIENT, "그뤂코드는 필수입니다."),
                                                status().is4xxClientError()));

                ComCodeTReqDto td8 = ComCodeTReqDto.builder()
                                .grpCd("[JUnit]ASSET_STATUS")
                                .attrNm("승인 상태수정 테스트")
                                .orderNum(5)
                                .build();
                testCases.add(
                                new TestCaseDetail<>(BASE_URL + "/attribute", "[속성코드] 필수값 누락 | 속성코드 누락", td8,
                                                ResponseApi.fail(ErrorCode.VALIDATION_ERROR_CLIENT, "속성코드는 필수입니다."),
                                                status().is4xxClientError()));

                ComCodeTReqDto td9 = ComCodeTReqDto.builder()
                                .grpCd("[JUnit]ASSET_STATUS")
                                .attrCd("[JUnit]LOCATION")
                                .orderNum(1)
                                .build();

                testCases.add(
                                new TestCaseDetail<>(BASE_URL + "/attribute", "[속성코드] 필수값 누락 | 속성명 누락", td9,
                                                ResponseApi.fail(ErrorCode.VALIDATION_ERROR_CLIENT, "속성명은 필수입니다."),
                                                status().is4xxClientError()));

                ComCodeTReqDto td10 = ComCodeTReqDto.builder()
                                .grpCd("[JUnit]ASSET_STATUS")
                                .attrCd("[JUnit]LOCATION")
                                .attrNm("승인 상태수정 테스트")
                                .build();
                testCases.add(
                                new TestCaseDetail<>(BASE_URL + "/attribute", "[속성코드] 필수값 누락 | 정렬순서 누락", td10,
                                                ResponseApi.fail(ErrorCode.VALIDATION_ERROR_CLIENT,
                                                                "정렬순서는 1 이상 필수입니다."),
                                                status().is4xxClientError()));
                /*
                 * [TC_ID : TC-069] [TC명 : 속성 코드 수정3] [REQ_ID : REQ_ADM_015] [화면 : 기준 정보 > 코드
                 * 관리] [기능 : 속성 코드 수정] [테스트항목 : 존재하지 않는 ID 수정 시도] [테스트 상세 : ]
                 */
                ComCodeTReqDto td12 = ComCodeTReqDto.builder()
                                .grpCd("[JUnit]ASSET_STATUS")
                                .attrCd("[JUnit]LOCATION_NOT_EXIST")
                                .attrNm("수정 테스트")
                                .orderNum(5)
                                .build();
                testCases.add(
                                new TestCaseDetail<>(BASE_URL + "/attribute", "[속성코드] 존재하는 ID 등록 시도", td12,
                                                ResponseApi.fail(ErrorCode.OBJECT_NOT_FOUND),
                                                status().is4xxClientError()));

                /*
                 * [TC_ID : TC-070] [TC명 : 속성 코드 수정4] [REQ_ID : REQ_ADM_015] [화면 : 기준 정보 > 코드
                 * 관리] [기능 : 속성 코드 수정] [테스트항목 : 비즈니스로직 케이스] [테스트 상세 : ]
                 */
                ComCodeTReqDto td13 = ComCodeTReqDto.builder()
                                .grpCd("[JUnit]ASSET_STATUS_IS_NOT_EXIST") // 그뤂코드가 없는 경우
                                .attrCd("[JUnit]LOCATION")
                                .attrNm("수정 테스트")
                                .orderNum(5)
                                .build();
                testCases.add(
                                new TestCaseDetail<>(BASE_URL + "/attribute", "[속성코드] 비즈니스로직 케이스 | 그뤂코드가 없는 경우", td13,
                                                ResponseApi.fail(ErrorCode.OBJECT_NOT_FOUND),
                                                status().is4xxClientError()));
                /*
                 * [TC_ID : TC-071] [TC명 : 속성 코드 수정5] [REQ_ID : REQ_ADM_015] [화면 : 기준 정보 > 코드
                 * 관리] [기능 : 속성 코드 수정] [테스트항목 : 잘못된 형식 입력] [테스트 상세 : ]
                 */
                ComCodeTReqDto td14 = ComCodeTReqDto.builder()
                                .grpCd("[JUnit]ASSET_STATUS")
                                .attrCd("[JUnit]LOCATION")
                                .attrNm("수정 테스트")
                                .build();
                testCases.add(
                                new TestCaseDetail<>(BASE_URL + "/", "[속성코드] 잘못된 형식 입력",
                                                td14,
                                                ResponseApi.fail(ErrorCode.PAGE_NOT_FOUND),
                                                status().is4xxClientError()));
                /**
                 * 
                 * 
                 * 
                 * 
                 * 
                 * 
                 * 
                 * 
                 * 
                 */
                /*
                 * [TC_ID : TC-087] [TC명 : 상세코드 수정1] [REQ_ID : REQ_ADM_019] [화면 : 기준 정보 > 코드 관리]
                 * [기능 : 상세코드 수정] [테스트항목 : 정상 수정] [테스트 상세 : ]
                 */
                ComCodeDReqDto td15 = ComCodeDReqDto.builder()
                                .grpCd("[JUnit]CUSTOMER_TYPE")
                                .attrCd("[JUnit]REGION")
                                .dtlCd("[JUnit]SEOUL")
                                .dtlNm("서울(수정)") // 수정
                                .useYn("N") // 수정
                                .orderNum(2) // 수정
                                .build();

                ComCodeDResDto ed15 = ComCodeDResDto.builder()
                                .dtlCd("[JUnit]SEOUL")
                                .dtlNm("서울(수정)") // 수정
                                .useYn("N") // 수정
                                .orderNum(2) // 수정
                                .build();
                testCases.add(
                                new TestCaseDetail<>(BASE_URL + "/detail", "[상세코드] 정상 수정", td15,
                                                ResponseApi.success(Map.of("data", ed15)), status().isOk()));
                /*
                 * [TC_ID : TC-088] [TC명 : 상세코드 수정2] [REQ_ID : REQ_ADM_019] [화면 : 기준 정보 > 코드 관리]
                 * [기능 : 상세코드 수정] [테스트항목 : 필수값 누락] [테스트 상세 : ]
                 */
                ComCodeDReqDto td16 = ComCodeDReqDto.builder()
                                .attrCd("[JUnit]CONDITION")
                                .dtlCd("[JUnit]NEW")
                                .dtlNm("신품(수정)") // 수정
                                .useYn("N") // 수정
                                .orderNum(2) // 수정
                                .build();
                testCases.add(
                                new TestCaseDetail<>(BASE_URL + "/detail", "[상세코드] 필수값 누락 | grpCd 누락", td16,
                                                ResponseApi.fail(ErrorCode.VALIDATION_ERROR_CLIENT, "그뤂코드는 필수입니다."),
                                                status().is4xxClientError()));

                // 2. attrCd 누락
                ComCodeDReqDto td17 = ComCodeDReqDto.builder()
                                .grpCd("[JUnit]ASSET_STATUS")
                                .dtlCd("[JUnit]NEW")
                                .dtlNm("신품(수정)") // 수정
                                .useYn("N") // 수정
                                .orderNum(2) // 수정
                                .build();
                testCases.add(
                                new TestCaseDetail<>(BASE_URL + "/detail", "[상세코드] 필수값 누락 | attrCd 누락", td17,
                                                ResponseApi.fail(ErrorCode.VALIDATION_ERROR_CLIENT, "속성코드는 필수입니다."),
                                                status().is4xxClientError()));

                // 3. dtlCd 누락
                ComCodeDReqDto td18 = ComCodeDReqDto.builder()
                                .grpCd("[JUnit]ASSET_STATUS")
                                .attrCd("[JUnit]CONDITION")
                                .dtlNm("신품(수정)") // 수정
                                .useYn("N") // 수정
                                .orderNum(2) // 수정
                                .build();
                testCases.add(
                                new TestCaseDetail<>(BASE_URL + "/detail", "[상세코드] 필수값 누락 | dtlCd 누락", td18,
                                                ResponseApi.fail(ErrorCode.VALIDATION_ERROR_CLIENT, "상세코드는 필수입니다."),
                                                status().is4xxClientError()));

                // 4. dtlNm 누락
                ComCodeDReqDto td19 = ComCodeDReqDto.builder()
                                .grpCd("[JUnit]CUSTOMER_TYPE")
                                .attrCd("[JUnit]REGION")
                                .dtlCd("[JUnit]INCHEON")
                                .useYn("N") // 수정
                                .orderNum(2) // 수정
                                .build();

                ComCodeDResDto ed19 = ComCodeDResDto.builder()
                                .dtlCd("[JUnit]INCHEON")
                                .useYn("N") // 수정
                                .orderNum(2) // 수정
                                .build();
                testCases.add(
                                new TestCaseDetail<>(BASE_URL + "/detail", "[상세코드] 필수값 누락 | dtlNm 누락(정상수정)", td19,
                                                ResponseApi.success(Map.of("data", ed19)), status().isOk()));

                // 5. useYn 누락
                ComCodeDReqDto td20 = ComCodeDReqDto.builder()
                                .grpCd("[JUnit]ASSET_STATUS")
                                .attrCd("[JUnit]CONDITION")
                                .dtlCd("[JUnit]NEW")
                                .dtlNm("신품(수정)") // 수정
                                .orderNum(2) // 수정
                                .build();
                testCases.add(
                                new TestCaseDetail<>(BASE_URL + "/detail", "[상세코드] 필수값 누락 | useYn 누락", td20,
                                                ResponseApi.fail(ErrorCode.VALIDATION_ERROR_CLIENT, "사용여부는 필수입니다."),
                                                status().is4xxClientError()));

                // 6. orderNum 누락
                ComCodeDReqDto td21 = ComCodeDReqDto.builder()
                                .grpCd("[JUnit]ASSET_STATUS")
                                .attrCd("[JUnit]CONDITION")
                                .dtlCd("[JUnit]NEW")
                                .dtlNm("신품(수정)") // 수정
                                .useYn("N") // 수정
                                .build();
                testCases.add(
                                new TestCaseDetail<>(BASE_URL + "/detail", "[상세코드] 필수값 누락 | orderNum 누락", td21,
                                                ResponseApi.fail(ErrorCode.VALIDATION_ERROR_CLIENT, "정렬순서는 1이상 필수입니다."),
                                                status().is4xxClientError()));

                /*
                 * [TC_ID : TC-089] [TC명 : 상세코드 수정3] [REQ_ID : REQ_ADM_019] [화면 : 기준 정보 > 코드 관리]
                 * [기능 : 상세코드 수정] [테스트항목 : 존재하지 않는 ID 수정 시도] [테스트 상세 : ]
                 */
                ComCodeDReqDto td22 = ComCodeDReqDto.builder()
                                .grpCd("[JUnit]ASSET_STATUS")
                                .attrCd("[JUnit]CONDITION")
                                .dtlCd("[JUnit]NEW_NOT_EXIST")
                                .dtlNm("신품(수정)") // 수정
                                .useYn("N") // 수정
                                .orderNum(2) // 수정
                                .build();
                testCases.add(
                                new TestCaseDetail<>(BASE_URL + "/detail", "[상세코드] 존재하는 ID 등록 시도", td22,
                                                ResponseApi.fail(ErrorCode.OBJECT_NOT_FOUND),
                                                status().is4xxClientError()));

                /*
                 * [TC_ID : TC-090] [TC명 : 상세코드 수정4] [REQ_ID : REQ_ADM_019] [화면 : 기준 정보 > 코드 관리]
                 * [기능 : 상세코드 수정] [테스트항목 : 비즈니스로직 케이스] [테스트 상세 : ]
                 */
                ComCodeDReqDto td23 = ComCodeDReqDto.builder()
                                .grpCd("[JUnit]ASSET_STATUS_NOT_EXIST")
                                .attrCd("[JUnit]CONDITION")
                                .dtlCd("[JUnit]NEW")
                                .dtlNm("신품(수정)") // 수정
                                .useYn("N") // 수정
                                .orderNum(2) // 수정
                                .build();
                testCases.add(
                                new TestCaseDetail<>(BASE_URL + "/detail", "[상세코드] 비즈니스로직 케이스 | 그뤂코드가 없는데 등록하는 경우",
                                                td23,
                                                ResponseApi.fail(ErrorCode.OBJECT_NOT_FOUND),
                                                status().is4xxClientError()));

                ComCodeDReqDto td24 = ComCodeDReqDto.builder()
                                .grpCd("[JUnit]ASSET_STATUS")
                                .attrCd("[JUnit]CONDITION_NOT_EXIST")
                                .dtlCd("[JUnit]NEW")
                                .dtlNm("신품(수정)") // 수정
                                .useYn("N") // 수정
                                .orderNum(2) // 수정
                                .build();
                testCases.add(
                                new TestCaseDetail<>(BASE_URL + "/detail", "[상세코드] 비즈니스로직 케이스 | 속성코드가 없는데 등록하는 경우",
                                                td24,
                                                ResponseApi.fail(ErrorCode.OBJECT_NOT_FOUND),
                                                status().is4xxClientError()));

                /*
                 * [TC_ID : TC-091] [TC명 : 상세코드 수정5] [REQ_ID : REQ_ADM_019] [화면 : 기준 정보 > 코드 관리]
                 * [기능 : 상세코드 수정] [테스트항목 : 잘못된 형식 입력] [테스트 상세 : ]
                 */
                ComCodeDReqDto td25 = ComCodeDReqDto.builder()
                                .attrCd("[JUnit]STATUS")
                                .dtlCd("[JUnit]APPROVED_NEW")
                                .dtlNm("승인완료")
                                .useYn("Y")
                                .orderNum(1)
                                .build();
                testCases.add(
                                new TestCaseDetail<>(BASE_URL + "/", "[상세코드] 잘못된 형식 입력",
                                                td25,
                                                ResponseApi.fail(ErrorCode.PAGE_NOT_FOUND),
                                                status().is4xxClientError()));

                // 각 케이스에 대한 InvocationContext 생성
                return testCases.stream().map(testCase -> new TestTemplateInvocationContext() {
                        @Override
                        public String getDisplayName(int invocationIndex) {
                                return "[" + invocationIndex + "] " + "코드 수정 : " + (testCase.getTestName());
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