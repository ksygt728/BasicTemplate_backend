package com.basic.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @파일명 : MailFreeMarkerConfig.java
 * @설명 : 메일 발송을 위한 FreeMarker 설정
 * @작성자 : 김승연
 * @작성일 : 2025.08.19
 * @변경이력 :
 *       2025.08.19 김승연 최초 생성
 */
@Configuration
public class MailFreeMarkerConfig {

    /**
     * @기능 : 메일 발송용 FreeMarker 설정 생성
     * @return freemarker.template.Configuration FreeMarker 설정 객체
     */
    @Primary
    @Bean
    public freemarker.template.Configuration freemarkerConfiguration() {
        freemarker.template.Configuration cfg = new freemarker.template.Configuration(
                freemarker.template.Configuration.VERSION_2_3_31);
        cfg.setDefaultEncoding("UTF-8");
        return cfg;
    }
}