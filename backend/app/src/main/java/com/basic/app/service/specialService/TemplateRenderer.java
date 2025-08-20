package com.basic.app.service.specialService;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;

import org.springframework.stereotype.Service;

import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TemplateRenderer {
    private final Configuration freemarkerConfig;

    public String render(String templateContent, Object params) throws Exception {
        Template template = new Template("mailTemplate", new StringReader(templateContent), freemarkerConfig);
        StringWriter out = new StringWriter();
        template.process(params, out);
        return out.toString();
    }
}