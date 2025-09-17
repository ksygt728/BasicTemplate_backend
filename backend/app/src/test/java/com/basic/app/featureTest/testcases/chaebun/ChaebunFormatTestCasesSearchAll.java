package com.basic.app.featureTest.testcases.chaebun;

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
import com.basic.app.dto.requestDto.ChaebunReqDto;
import com.basic.app.dto.responseDto.ChaebunResDto;
import com.basic.app.util.TestCaseDetailSearchForm;

public class ChaebunFormatTestCasesSearchAll implements
    TestTemplateInvocationContextProvider {
  private final String BASE_URL = "/admin/chaebun";

  @Override
  public boolean supportsTestTemplate(ExtensionContext context) {
    return true;
  }

  @Override
  public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(ExtensionContext context) {

    List<TestCaseDetailSearchForm<?, ?>> testCases = new ArrayList<TestCaseDetailSearchForm<?, ?>>();

    /*
     * [TC_ID : TC-001] [TC명 : 채번 정보 조회1] [REQ_ID : REQ_ADM_053] [화면 : 시스템 관리 >
     * 채번관리] [기능 : 채번 정보 조회] [테스트항목 : [N건] 리스트 페이징 조회] [테스트 상세 : ]
     */

    ChaebunReqDto td1 = ChaebunReqDto.builder()
        .seqId("[JUnit]SEQ")
        .build();

    PageRequest pr1 = PageRequest.of(0, 2000, Sort.by("seqId").ascending());

    // Response - 예상되는 전체 채번 데이터 (테스트 데이터에서 sts='C'인 것들)
    List<ChaebunResDto> data1 = List.of(
        ChaebunResDto.builder()
            .seqId("[JUnit]SEQ001")
            .seqName("[JUnit]회원번호채번")
            .pattern("{PREFIX}_{DATEFORMAT}_{VALUE}")
            .prefix("[JUnit]MEMBER")
            .currentValue(0)
            .step(1)
            .length(4)
            .dateformat("yyyyMMdd")
            .build(),
        ChaebunResDto.builder()
            .seqId("[JUnit]SEQ002")
            .seqName("[JUnit]주문번호채번")
            .pattern("{PREFIX}_{DATEFORMAT}_{VALUE}")
            .prefix("[JUnit]ORDER")
            .currentValue(5)
            .step(1)
            .length(6)
            .dateformat("yyyyMMdd")
            .build(),
        ChaebunResDto.builder()
            .seqId("[JUnit]SEQ003")
            .seqName("[JUnit]상품번호채번")
            .pattern("{PREFIX}_{VALUE}")
            .prefix("[JUnit]PRODUCT")
            .currentValue(10)
            .step(1)
            .length(5)
            .dateformat("")
            .build(),
        ChaebunResDto.builder()
            .seqId("[JUnit]SEQ005")
            .seqName("[JUnit]게시판번호채번")
            .pattern("{PREFIX}_{DATEFORMAT}_{VALUE}")
            .prefix("[JUnit]BOARD")
            .currentValue(100)
            .step(1)
            .length(8)
            .dateformat("yyyyMMdd")
            .build(),
        ChaebunResDto.builder()
            .seqId("[JUnit]SEQ006")
            .seqName("[JUnit]파일번호채번")
            .pattern("{PREFIX}_{VALUE}")
            .prefix("[JUnit]FILE")
            .currentValue(0)
            .step(1)
            .length(10)
            .dateformat("")
            .build());

    PageResponse<ChaebunResDto> pagedData1 = new PageResponse<>(new PageImpl<>(
        data1, pr1, 5)); // totalElements=5 (SEQ004는 삭제된 상태이므로 제외)
    pagedData1.setTotalElements(5);
    pagedData1.setTotalPages(1);
    pagedData1.setFirst(true);
    pagedData1.setLast(true);
    ResponseApi<?> ed1 = ResponseApi.success(Map.of("data", pagedData1));

    testCases.add(
        new TestCaseDetailSearchForm<>(
            BASE_URL + "/search", "정상조회(N건)", td1, ed1, status().isOk(), pr1));

    /* 추가 테스트 케이스들은 필요에 따라 추가 가능 */

    return testCases.stream().map(testCase -> new TestTemplateInvocationContext() {
      @Override
      public String getDisplayName(int invocationIndex) {
        return testCase.getTestName();
      }

      @Override
      public List<Extension> getAdditionalExtensions() {
        return List.of(new ParameterResolver() {
          @Override
          public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) {
            return parameterContext.getParameter().getType() == TestCaseDetailSearchForm.class;
          }

          @Override
          public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) {
            return testCase;
          }
        });
      }
    });
  }
}