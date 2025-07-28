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
import java.util.ArrayList;
import java.util.HashMap;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class UtilConfig {

	@Bean
	@Scope("prototype")
	public ArrayList<String> stringList() {
		return new ArrayList<String>();
	}

	@Bean
	@Scope("prototype")
	public ArrayList<Boolean> booleanList() {
		return new ArrayList<Boolean>();
	}

	@Bean
	@Scope("prototype")
	public ArrayList<Integer> integerList() {
		return new ArrayList<Integer>();
	}

	@Bean
	@Scope("prototype")
	public HashMap<String, Object> map() {
		return new HashMap<String, Object>();
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Bean
	public SimpleDateFormat currentTimeFormat() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	}
}
