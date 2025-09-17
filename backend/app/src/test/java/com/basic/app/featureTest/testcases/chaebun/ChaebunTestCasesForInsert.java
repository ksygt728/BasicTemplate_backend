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

import com.basic.app.api.ResponseApi;
import com.basic.app.dto.requestDto.ChaebunReqDto;
import com.basic.app.dto.responseDto.ChaebunResDto;
import com.basic.app.exception.ErrorCode;
import com.basic.app.util.TestCaseDetail;

public class ChaebunTestCasesForInsert implements TestTemplateInvocationContextProvider {
  private final String BASE_URL = "/admin/chaebun";

  @Override
  public boolean supportsTestTemplate(ExtensionContext context) {
    return true;
  }

  @Override
  public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(ExtensionContext context) {
    List<TestCaseDetail<?>> testCases = new ArrayList<TestCaseDetail<?>>();

    /*
     * [TC_ID : TC-262] [TC명 : 채번 추가1] [REQ_ID : REQ_ADM_054] [화면 : 시스템 관리 > 채번관리]
     * [기능 : 채번 추가] [테스트항목 : 정상 등록] [테스트 상세 : ]
     */
    ChaebunReqDto td1 = ChaebunReqDto.builder()
        .seqId("[JUnit]SEQ_NEW001")
        .seqName("[JUnit]신규채번테스트")
        .pattern("{PREFIX}_{DATEFORMAT}_{VALUE}")
        .prefix("[JUnit]NEW")
        .step(1)
        .length(6)
        .dateformat("yyyyMMdd")
        .build();

    ChaebunResDto ed1 = ChaebunResDto.builder()
        .seqId("[JUnit]SEQ_NEW001")
        .seqName("[JUnit]신규채번테스트")
        .pattern("{PREFIX}_{DATEFORMAT}_{VALUE}")
        .prefix("[JUnit]NEW")
        .currentValue(0)
        .step(1)
        .length(6)
        .dateformat("yyyyMMdd")
        .build();

    testCases.add(
        new TestCaseDetail<>(BASE_URL, "정상 등록", td1,
            ResponseApi.success(Map.of("data", ed1)), status().isOk()));

    /*
     * [TC_ID : TC-263] [TC명 : 채번 추가2] [REQ_ID : REQ_ADM_054] [화면 : 시스템 관리 > 채번관리]
     * [기능 : 채번 추가] [테스트항목 : 필수값 누락] [테스트 상세 : ]
     */

    ChaebunReqDto td2ReqDto = ChaebunReqDto.builder()
        // 채번아이디 누락
        .seqName("asd")
        .pattern("{PREFIX}_{VALUE}")
        .prefix("[JUnit]INVALID")
        .step(1)
        .length(4)
        .build();

    testCases.add(
        new TestCaseDetail<>(BASE_URL, "필수값 누락 - 채번아이디", td2ReqDto,
            ResponseApi.fail(ErrorCode.VALIDATION_ERROR_CLIENT, "채번아이디는 필수입니다."), status().is4xxClientError()));

    // 채번명 누락
    ChaebunReqDto td2_1 = ChaebunReqDto.builder()
        .seqId("[JUnit]SEQ_INVALID001")
        .seqName("") // 채번명 누락
        .pattern("{PREFIX}_{VALUE}")
        .prefix("[JUnit]INVALID")
        .step(1)
        .length(4)
        .build();

    testCases.add(
        new TestCaseDetail<>(BASE_URL, "필수값 누락 - 채번명", td2_1,
            ResponseApi.fail(ErrorCode.VALIDATION_ERROR_CLIENT, "채번명을 입력하세요."), status().is4xxClientError()));

    // 채번패턴 누락
    ChaebunReqDto td2_2 = ChaebunReqDto.builder()
        .seqId("[JUnit]SEQ_INVALID002")
        .seqName("[JUnit]패턴누락테스트")
        .pattern("") // 채번패턴 누락
        .prefix("[JUnit]INVALID")
        .step(1)
        .length(4)
        .build();

    testCases.add(
        new TestCaseDetail<>(BASE_URL, "필수값 누락 - 채번패턴", td2_2,
            ResponseApi.fail(ErrorCode.VALIDATION_ERROR_CLIENT, "채번패턴을 입력하세요."), status().is4xxClientError()));

    // 채번고유번호 누락
    ChaebunReqDto td2_3 = ChaebunReqDto.builder()
        .seqId("[JUnit]SEQ_INVALID003")
        .seqName("[JUnit]프리픽스누락테스트")
        .pattern("{PREFIX}_{VALUE}")
        .prefix("") // 채번고유번호 누락
        .step(1)
        .length(4)
        .build();

    testCases.add(
        new TestCaseDetail<>(BASE_URL, "필수값 누락 - 채번고유번호", td2_3,
            ResponseApi.fail(ErrorCode.VALIDATION_ERROR_CLIENT, "채번고유번호 누락"), status().is4xxClientError()));

    // 증가량 누락 (null)
    ChaebunReqDto td2_4 = ChaebunReqDto.builder()
        .seqId("[JUnit]SEQ_INVALID004")
        .seqName("[JUnit]증가량누락테스트")
        .pattern("{PREFIX}_{VALUE}")
        .prefix("[JUnit]INVALID")
        .step(0) // 실제로는 null이어야 하지만 primitive type이므로 0으로 테스트
        .length(4)
        .build();

    testCases.add(
        new TestCaseDetail<>(BASE_URL, "필수값 누락 - 증가량", td2_4,
            ResponseApi.fail(ErrorCode.VALIDATION_ERROR_CLIENT, "증가량은 1 이상이어야 합니다."), status().is4xxClientError()));

    // 채번길이 누락 (null)
    ChaebunReqDto td2_5 = ChaebunReqDto.builder()
        .seqId("[JUnit]SEQ_INVALID005")
        .seqName("[JUnit]길이누락테스트")
        .pattern("{PREFIX}_{VALUE}")
        .prefix("[JUnit]INVALID")
        .step(1)
        .length(0) // 실제로는 null이어야 하지만 primitive type이므로 0으로 테스트
        .build();

    testCases.add(
        new TestCaseDetail<>(BASE_URL, "필수값 누락 - 채번길이", td2_5,
            ResponseApi.fail(ErrorCode.VALIDATION_ERROR_CLIENT, "채번길이는 1 이상이어야 합니다."), status().is4xxClientError()));

    /*
     * [TC_ID : TC-264] [TC명 : 채번 추가3] [REQ_ID : REQ_ADM_054] [화면 : 시스템 관리 > 채번관리]
     * [기능 : 채번 추가] [테스트항목 : 존재하는 ID 등록 시도] [테스트 상세 : ]
     */
    ChaebunReqDto td3 = ChaebunReqDto.builder()
        .seqId("[JUnit]SEQ001") // 이미 존재하는 seqId
        .seqName("[JUnit]중복테스트")
        .pattern("{PREFIX}_{VALUE}")
        .prefix("[JUnit]DUP")
        .step(1)
        .length(4)
        .build();

    testCases.add(
        new TestCaseDetail<>(BASE_URL, "존재하는 ID 등록 시도", td3,
            ResponseApi.fail(ErrorCode.OBJECT_IS_EXISTED), status().is4xxClientError()));

    /*
     * [TC_ID : TC-265] [TC명 : 채번 추가4] [REQ_ID : REQ_ADM_054] [화면 : 시스템 관리 > 채번관리]
     * [기능 : 채번 추가] [테스트항목 : 비즈니스로직 케이스] [테스트 상세 : ]
     */
    // 해당 케이스 미존재
    testCases.add(new TestCaseDetail<>("N/A", "비즈니스로직 케이스", null, null, status().isOk()));

    /*
     * [TC_ID : TC-266] [TC명 : 채번 추가5] [REQ_ID : REQ_ADM_054] [화면 : 시스템 관리 > 채번관리]
     * [기능 : 채번 추가] [테스트항목 : 잘못된 형식 입력] [테스트 상세 : ]
     */
    // 해당 케이스 미존재 (DTO 변환 시 자동으로 타입 체크됨)
    testCases.add(new TestCaseDetail<>("N/A", "잘못된 형식 입력", null, null, status().isOk()));

    return testCases.stream().map(testCase -> new TestTemplateInvocationContext() {
      @Override
      public String getDisplayName(int invocationIndex) {
        return "[" + invocationIndex + "] " + "채번 등록 : " + (testCase.getTestName());
      }

      @Override
      public List<Extension> getAdditionalExtensions() {
        return List.of(new ParameterResolver() {
          @Override
          public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) {
            return parameterContext.getParameter().getType() == TestCaseDetail.class;
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