
package com.basic.app.service.specialService;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;

import org.springframework.stereotype.Service;

import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.RequiredArgsConstructor;

/**
 * @파일명 : TemplateRenderer.java
 * @설명 : FreeMarker 템플릿을 렌더링하는 서비스
 * @작성자 : 김승연
 * @작성일 : 2025.09.05
 * @변경이력 :
 *       2025.09.05 김승연 최초 생성
 */
@Service
@RequiredArgsConstructor
public class TemplateRenderer {
    private final Configuration freemarkerConfig;

    /**
     * @기능 : FreeMarker 템플릿 문자열을 렌더링
     * @param templateContent 템플릿 원본 문자열
     * @param params          템플릿에 바인딩할 파라미터 객체
     * @return 렌더링된 결과 문자열
     * @throws Exception 렌더링 실패 시 예외
     */
    public String render(String templateContent, Object params) throws Exception {
        Template template = new Template("mailTemplate", new StringReader(templateContent), freemarkerConfig);
        StringWriter out = new StringWriter();
        template.process(params, out);
        return out.toString();
    }
}