/**
 * @파일명   : UtilConfig.java
 * @설명     : 유틸리티 설정
 * @작성자   : 김승연
 * @작성일   : 2025.07.24
 * @변경이력 :
 *   2025.07.24     김승연       최초 생성
 */
package com.basic.app.config;

import java.text.SimpleDateFormat;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration
public class UtilConfig implements WebMvcConfigurer {

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Bean
	public ObjectMapper objectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		return objectMapper;
	}

	@Bean
	public SimpleDateFormat currentTimeFormat() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	}

	/* 빈 문자열('') NULL로 처리 */
	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addConverter(new Converter<String, String>() {
			@Override
			public String convert(String source) {
				if (source != null && source.trim().isEmpty()) {
					return null;
				}
				return source;
			}
		});
	}
}
