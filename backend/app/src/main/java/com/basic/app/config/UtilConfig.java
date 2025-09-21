package com.basic.app.config;

import java.text.SimpleDateFormat;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * @파일명 : UtilConfig.java
 * @설명 : 유틸리티 설정
 * @작성자 : 김승연
 * @작성일 : 2025.07.24
 * @변경이력 :
 *       2025.07.24 김승연 최초 생성
 */
@Configuration
public class UtilConfig implements WebMvcConfigurer {

	/**
	 * @기능 : ModelMapper Bean 설정
	 * @return ModelMapper 객체 매핑을 위한 ModelMapper 객체
	 */
	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		// 전역 매핑 전략을 STRICT로 설정
		modelMapper.getConfiguration()
				.setMatchingStrategy(MatchingStrategies.STRICT)
				.setSkipNullEnabled(true); // null 값은 무시 (옵션)
		return modelMapper;
	}

	/**
	 * @기능 : ObjectMapper Bean 설정
	 * @return ObjectMapper JSON 객체 매핑을 위한 ObjectMapper 객체
	 */
	@Bean
	public ObjectMapper objectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		return objectMapper;
	}

	/**
	 * @기능 : 현재 시간 포맷 설정
	 * @return SimpleDateFormat 시간 포맷 객체
	 */
	@Bean
	public SimpleDateFormat currentTimeFormat() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	}

	/**
	 * @기능 : 빈 문자열('') NULL로 처리하는 포매터 등록
	 * @param registry 포매터 레지스트리
	 */
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
