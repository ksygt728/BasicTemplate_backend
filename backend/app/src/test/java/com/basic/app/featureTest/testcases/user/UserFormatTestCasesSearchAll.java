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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.basic.app.api.PageResponse;
import com.basic.app.api.ResponseApi;
import com.basic.app.dto.requestDto.UserReqDto;
import com.basic.app.dto.requestDto.specialDto.CodeSearchFormReqDto;
import com.basic.app.dto.responseDto.CompanyResDto;
import com.basic.app.dto.responseDto.DepartmentResDto;
import com.basic.app.dto.responseDto.UserResDto;
import com.basic.app.dto.responseDto.specialDto.CodeSearchFormResDto;
import com.basic.app.util.TestCaseDetailSearchForm;

public class UserFormatTestCasesSearchAll implements
        TestTemplateInvocationContextProvider {
    private final String BASE_URL = "/admin/user";

    @Override
    public boolean supportsTestTemplate(ExtensionContext context) {
        return true;
    }

    @Override
    public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(ExtensionContext context) {

        List<TestCaseDetailSearchForm> testCases = new ArrayList<TestCaseDetailSearchForm>();
        /*
         * [TC_ID : TC-001] [TC명 : 사용자 정보 조회1] [REQ_ID : REQ_ADM_001] [화면 : 조직 관리 > 사용자
         * 관리] [기능 : 사용자 정보 조회] [테스트항목 : [N건] 리스트 페이징 조회] [테스트 상세 : ]
         */

        UserReqDto td1 = new UserReqDto().builder()
                .userId("[JUnit]admin001")
                .name("[JUnit]김관리")
                .phoneNum("010-1111-1111") // 번호 수정
                .email("admin001@company.com")
                .userType("CBMS")
                .gender("M")
                .build();

        PageRequest pr1 = PageRequest.of(0, 2000, Sort.by("userId").ascending());

        // Response
        List<UserResDto> data1 = List.of(
                UserResDto.builder()
                        .userId("[JUnit]admin001")
                        .name("[JUnit]김관리")
                        .phoneNum("010-1111-1111")
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
                        .build());

        PageResponse<UserResDto> pagedData1 = new PageResponse<>(new PageImpl<>(
                data1, pr1, 1)); // totalElements=_order10
        pagedData1.setTotalElements(1);
        pagedData1.setTotalPages(1);
        pagedData1.setFirst(true);
        pagedData1.setLast(true);
        ResponseApi<?> ed1 = ResponseApi.success(Map.of("data", pagedData1));

        ;

        testCases.add(
                new TestCaseDetailSearchForm(
                        BASE_URL + "/search", "정상조회(N건)", td1, ed1, status().isOk(), pr1)

        );

        // 각 케이스에 대한 InvocationContext 생성
        return testCases.stream().map(testCase -> new TestTemplateInvocationContext() {
            @Override
            public String getDisplayName(int invocationIndex) {
                return "사용자 조회 : [" + invocationIndex + "] " +
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