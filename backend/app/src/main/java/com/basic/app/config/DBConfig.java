package com.basic.app.config;

import javax.sql.DataSource;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @파일명 : DBConfig.java
 * @설명 : 데이터베이스 설정 클래스
 * @작성자 : 김승연
 * @작성일 : 2025.07.24
 * @변경이력 :
 *       2025.07.24 김승연 최초 생성
 */
@Configuration
public class DBConfig {

  /**
   * @기능 : DSLContext를 Bean으로 등록하여 JOOQ를 사용할 수 있도록 설정
   * @param dataSource 데이터 소스
   * @return DSLContext JOOQ DSL 컨텍스트 객체
   */
  @Bean
  public DSLContext dslContext(DataSource dataSource) {
    return DSL.using(dataSource, SQLDialect.MYSQL);
  }

}
