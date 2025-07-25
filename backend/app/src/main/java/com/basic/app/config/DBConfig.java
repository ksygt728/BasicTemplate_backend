// /**
// * @파일명 : DBConfig.java
// * @설명 : 데이터베이스 연결 설정
// * @작성자 : 김승연
// * @작성일 : 2025.07.24
// * @변경이력 :
// * 2025.07.24 김승연 최초 생성
// */
// package com.basic.app.config;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;

// import com.querydsl.jpa.impl.JPAQueryFactory;

// import jakarta.persistence.EntityManager;
// import jakarta.persistence.PersistenceContext;

// @Configuration
// public class DBConfig {

// // queryDSL 설정
// @PersistenceContext
// private EntityManager entityManager;

// @Bean
// public JPAQueryFactory jpaQueryFactory() {
// // Provider<EntityManager> entityManager
// return new JPAQueryFactory(entityManager);
// }

// // Spring data JPA -> application.properties에서 설정

// // JDBC Template 설정
// // @Bean
// // public ComboPooledDataSource dataSource() throws PropertyVetoException {
// // ComboPooledDataSource dataSource = new ComboPooledDataSource();
// //
// // dataSource.setDriverClass("com.mysql.cj.jdbc.Driver");
// // dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/lins_finaltest");
// // dataSource.setUser("root");
// // dataSource.setPassword("jx1019aw!!@@");
// // dataSource.setMaxPoolSize(200);
// // dataSource.setCheckoutTimeout(60000);
// // dataSource.setMaxIdleTime(1800);
// // dataSource.setIdleConnectionTestPeriod(600);
// //
// // return dataSource;
// //
// // }
// }