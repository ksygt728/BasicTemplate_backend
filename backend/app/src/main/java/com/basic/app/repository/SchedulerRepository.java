/**
 * @파일명   : SchedulerRepository.java
 * @설명     : 스케줄러 레포지토리
 * @작성자   : 김승연
 * @작성일   : 2025.08.09
 * @변경이력 :
 *   2025.08.09     김승연       최초 생성
 */
package com.basic.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.basic.app.entity.ScheM;

@Repository
public interface SchedulerRepository extends JpaRepository<ScheM, String> {

}
