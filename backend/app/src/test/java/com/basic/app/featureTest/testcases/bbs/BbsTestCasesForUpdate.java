package com.basic.app.featureTest.testcases.bbs;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
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
import com.basic.app.dto.requestDto.BbsReqDto;
import com.basic.app.dto.responseDto.BbsResDto;
import com.basic.app.dto.responseDto.CompanyResDto;
import com.basic.app.dto.responseDto.DepartmentResDto;
import com.basic.app.dto.responseDto.UserResDto;
import com.basic.app.exception.ErrorCode;
import com.basic.app.util.TestCaseDetail;

public class BbsTestCasesForUpdate implements TestTemplateInvocationContextProvider {

    private final String BASE_URL = "/admin/bbs";

    @Override
    public boolean supportsTestTemplate(ExtensionContext context) {
        return true;
    }

    @Override
    public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(ExtensionContext context) {

        List<TestCaseDetail<?>> testCases = new ArrayList<TestCaseDetail<?>>();

        /*
         * [TC_ID : TC-412] [TC명 : 게시글 수정1] [REQ_ID : REQ_ADM_084] [화면 : 시스템 관리 > 게시판
         * 관리] [기능 : 게시판 수정] [테스트항목 : 정상 수정] [테스트 상세 : ]
         */
        BbsReqDto td1 = BbsReqDto.builder()
                .bbsId("[JUnit]BBS002")
                .bbsType("[JUnit]일반수정")
                .title("[JUnit]수정된 일반게시글 제목")
                .content("[JUnit]수정된 일반게시글 내용입니다.")
                .writor("[JUnit]user001")
                .build();

        UserResDto writor1 = UserResDto.builder()
                .userId("[JUnit]user001")
                .name("[JUnit]이직원")
                .phoneNum("010-2222-2222")
                .email("user001@company.com")
                .role("ROLE_GUEST")
                .userType("CBMS")
                .gender("F")
                .department(DepartmentResDto.builder()
                        .deptCode("[JUnit]21323243")
                        .deptNm("TEST회사")
                        .company(null)
                        .upperDeptCode("20000000")
                        .deptLv(1)
                        .useYn("Y")
                        .company(CompanyResDto.builder()
                                .companyCode("[JUnit]C100")
                                .companyName("CBMS회사")
                                .build())
                        .build())
                .build();

        BbsResDto ed1 = BbsResDto.builder()
                .bbsId("[JUnit]BBS002")
                .bbsType("[JUnit]일반수정")
                .title("[JUnit]수정된 일반게시글 제목")
                .content("[JUnit]수정된 일반게시글 내용입니다.")
                .writor(writor1)
                .writeDate(LocalDateTime.now())
                .build();

        testCases.add(
                new TestCaseDetail<>(BASE_URL, "정상 수정", td1,
                        ResponseApi.success(Map.of("data", ed1)), status().isOk()));

        /*
         * [TC_ID : TC-413] [TC명 : 게시글 수정2] [REQ_ID : REQ_ADM_084] [화면 : 시스템 관리 > 게시판
         * 관리] [기능 : 게시판 수정] [테스트항목 : 필수값 누락] [테스트 상세 : ]
         */
        // bbsId 누락
        BbsReqDto td2_1 = BbsReqDto.builder()
                .bbsType("[JUnit]공지")
                .title("[JUnit]제목")
                .content("[JUnit]내용")
                .writor("[JUnit]admin001")
                .build();

        testCases.add(
                new TestCaseDetail<>(BASE_URL, "필수값 누락 - bbsId", td2_1,
                        ResponseApi.fail(ErrorCode.VALIDATION_ERROR_CLIENT, "게시판아이디는 필수입니다."),
                        status().is4xxClientError()));

        // bbsType 누락
        BbsReqDto td2_2 = BbsReqDto.builder()
                .bbsId("[JUnit]BBS002")
                .title("[JUnit]제목")
                .content("[JUnit]내용")
                .writor("[JUnit]user001")
                .build();

        testCases.add(
                new TestCaseDetail<>(BASE_URL, "필수값 누락 - bbsType", td2_2,
                        ResponseApi.fail(ErrorCode.VALIDATION_ERROR_CLIENT, "게시판타입은 필수입니다."),
                        status().is4xxClientError()));

        // title 누락
        BbsReqDto td2_3 = BbsReqDto.builder()
                .bbsId("[JUnit]BBS002")
                .bbsType("[JUnit]공지")
                .content("[JUnit]내용")
                .writor("[JUnit]user001")
                .build();

        testCases.add(
                new TestCaseDetail<>(BASE_URL, "필수값 누락 - title", td2_3,
                        ResponseApi.fail(ErrorCode.VALIDATION_ERROR_CLIENT, "제목을 입력하세요."),
                        status().is4xxClientError()));

        // content 누락
        BbsReqDto td2_4 = BbsReqDto.builder()
                .bbsId("[JUnit]BBS002")
                .bbsType("[JUnit]공지")
                .title("[JUnit]제목")
                .writor("[JUnit]user001")
                .build();

        testCases.add(
                new TestCaseDetail<>(BASE_URL, "필수값 누락 - content", td2_4,
                        ResponseApi.fail(ErrorCode.VALIDATION_ERROR_CLIENT, "내용을 입력하세요."),
                        status().is4xxClientError()));

        // writor 누락
        BbsReqDto td2_5 = BbsReqDto.builder()
                .bbsId("[JUnit]BBS002")
                .bbsType("[JUnit]공지")
                .title("[JUnit]제목")
                .content("[JUnit]내용")
                .build();

        testCases.add(
                new TestCaseDetail<>(BASE_URL, "필수값 누락 - writor", td2_5,
                        ResponseApi.fail(ErrorCode.VALIDATION_ERROR_CLIENT, "작성자는필수입니다."),
                        status().is4xxClientError()));

        /*
         * [TC_ID : TC-414] [TC명 : 게시글 수정3] [REQ_ID : REQ_ADM_084] [화면 : 시스템 관리 > 게시판
         * 관리] [기능 : 게시판 수정] [테스트항목 : 존재하지 않는 ID 수정 시도] [테스트 상세 : ]
         */
        BbsReqDto td3 = BbsReqDto.builder()
                .bbsId("[JUnit]BBS_NOT_EXIST")
                .bbsType("[JUnit]공지")
                .title("[JUnit]제목")
                .content("[JUnit]내용")
                .writor("[JUnit]admin001")
                .build();

        testCases.add(
                new TestCaseDetail<>(BASE_URL, "존재하지 않는 ID 수정 시도", td3,
                        ResponseApi.fail(ErrorCode.OBJECT_NOT_FOUND), status().is4xxClientError()));

        /*
         * [TC_ID : TC-415] [TC명 : 게시글 수정4] [REQ_ID : REQ_ADM_084] [화면 : 시스템 관리 > 게시판
         * 관리] [기능 : 게시판 수정] [테스트항목 : 비즈니스로직 케이스] [테스트 상세 : ]
         */
        // 작성자 불일치 (다른 사람의 게시글 수정 시도)
        BbsReqDto td4 = BbsReqDto.builder()
                .bbsId("[JUnit]BBS002")
                .bbsType("[JUnit]공지")
                .title("[JUnit]제목")
                .content("[JUnit]내용")
                .writor("[JUnit]admin001") // 원래 작성자는 user001
                .build();

        testCases.add(
                new TestCaseDetail<>(BASE_URL, "비즈니스로직 케이스", td4,
                        ResponseApi.fail(ErrorCode.BBS_WRITOR_NOT_MATCH), status().is4xxClientError()));

        /*
         * [TC_ID : TC-416] [TC명 : 게시글 수정5] [REQ_ID : REQ_ADM_084] [화면 : 시스템 관리 > 게시판
         * 관리] [기능 : 게시판 수정] [테스트항목 : 잘못된 형식 입력] [테스트 상세 : ]
         */
        testCases.add(
                new TestCaseDetail<>(BASE_URL + "/", "잘못된 형식 입력",
                        td1, ResponseApi.fail(ErrorCode.PAGE_NOT_FOUND), status().is4xxClientError()));

        return testCases.stream().map(testCase -> new TestTemplateInvocationContext() {
            @Override
            public String getDisplayName(int invocationIndex) {
                return "[" + invocationIndex + "] " + "게시글 수정 : " + (testCase.getTestName());
            }

            @Override
            public List<Extension> getAdditionalExtensions() {
                return List.of(new ParameterResolver() {
                    @Override
                    public boolean supportsParameter(ParameterContext parameterContext,
                            ExtensionContext extensionContext) {
                        return parameterContext.getParameter().getType() == TestCaseDetail.class;
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