package com.basic.app.config;

import javax.sql.DataSource;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DBConfig {

  @Bean // DSLContext를 Bean으로 등록하여 JOOQ를 사용할 수 있도록 설정
  public DSLContext dslContext(DataSource dataSource) {
    return DSL.using(dataSource, SQLDialect.MYSQL);
  }

}
