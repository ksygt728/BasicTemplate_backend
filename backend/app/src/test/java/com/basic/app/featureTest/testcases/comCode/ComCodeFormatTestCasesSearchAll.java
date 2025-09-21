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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.basic.app.api.PageResponse;
import com.basic.app.api.ResponseApi;
import com.basic.app.dto.requestDto.specialDto.CodeSearchFormReqDto;
import com.basic.app.dto.responseDto.specialDto.CodeSearchFormResDto;
import com.basic.app.util.TestCaseDetailSearchForm;

public class ComCodeFormatTestCasesSearchAll implements
        TestTemplateInvocationContextProvider {
    private final String BASE_URL = "/admin/code";

    @Override
    public boolean supportsTestTemplate(ExtensionContext context) {
        return true;
    }

    @Override
    public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(ExtensionContext context) {

        List<TestCaseDetailSearchForm> testCases = new ArrayList<TestCaseDetailSearchForm>();

        /*
         * [TC_ID : TC-037] [TC명 : 그뤂 코드 조회1] [REQ_ID : REQ_ADM_009] [화면 : 기준 정보 > 코드
         * 관리] [기능 : 그뤂 코드 조회] [테스트항목 : [단건] 정상 조회] [테스트 상세 : ]
         */
        // searchForm
        CodeSearchFormReqDto td1 = CodeSearchFormReqDto.builder()
                .grpCd("[JUnit]APPROVAL_STATUS") // 그룹코드
                .attrCd("[JUnit]STATUS") // 속성코드
                .dtlCd("[JUnit]PENDING") // 속성명
                .build();
        PageRequest pr1 = PageRequest.of(0, 2000, Sort.by("grp_cd").descending());

        // Response
        List<CodeSearchFormResDto> data1 = List.of(
                CodeSearchFormResDto.builder()
                        .grpCdType("WORKFLOW") // 그룹코드
                        .grpCd("[JUnit]APPROVAL_STATUS") // 속성코드
                        .grpNm("승인 상태") // 속성명
                        .dtlCd("[JUnit]PENDING") // 정렬순서
                        .attrCd("[JUnit]STATUS") // 사용여부
                        .dtlNm("대기중") // 사용여부
                        .attrNm("승인상태") // 사용여부
                        .useYn("Y") // 사용여부
                        .codeTOrderNum(1) // 사용여부
                        .codeDOrderNum(1) // 사용여부
                        .build()

        );

        PageResponse<CodeSearchFormResDto> pagedData1 = new PageResponse<>(new PageImpl<>(
                data1, pr1, 1)); // totalElements=_order10
        pagedData1.setTotalElements(1);
        pagedData1.setTotalPages(1);
        pagedData1.setFirst(true);
        pagedData1.setLast(true);
        ResponseApi<?> ed1 = ResponseApi.success(Map.of("data", pagedData1));

        ;

        testCases.add(
                new TestCaseDetailSearchForm(
                        BASE_URL + "/search", "정상조회(N건) - 그뤂코드", td1, ed1, status().isOk(), pr1)

        );

        /*
         * [TC_ID : TC-057] [TC명 : 속성 코드 조회1] [REQ_ID : REQ_ADM_013] [화면 : 기준 정보 > 코드
         * 관리] [기능 : 속성 코드 조회] [테스트항목 : [단건] 정상 조회] [테스트 상세 : ]
         */

        /*
         * [TC_ID : TC-077] [TC명 : 상세코드 조회1] [REQ_ID : REQ_ADM_017] [화면 : 기준 정보 > 코드 관리]
         * [기능 : 상세코드 조회] [테스트항목 : [단건] 정상 조회] [테스트 상세 : ]
         */

        // 각 케이스에 대한 InvocationContext 생성
        return testCases.stream().map(testCase -> new TestTemplateInvocationContext() {
            @Override
            public String getDisplayName(int invocationIndex) {
                return "코드 조회 : [" + invocationIndex + "] " +
                        (testCase.getTestName());

            }

            @Override
            public List<Extension> getAdditionalExtensions() {
                return List.of(new ParameterResolver() {
                    @Override
                    public boolean supportsParameter(ParameterContext parameterContext,
                            ExtensionContext extensionContext) {
                        Class<?> type = parameterContext.getParameter().getType();
                        // TestCaseDetail만 지원
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