/**
 * @파일명   : ChaebunRepository.java
 * @설명     : 스케줄러 레포지토리
 * @작성자   : 김승연
 * @작성일   : 2025.08.24
 * @변경이력 :
 *   2025.08.24     김승연       최초 생성
 */
package com.basic.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.basic.app.entity.Chaebun;

import jakarta.persistence.LockModeType;

@Repository
public interface ChaebunRepository extends JpaRepository<Chaebun, String> {

  @Lock(LockModeType.PESSIMISTIC_WRITE) // 동시 채번 방지를 위해 Lock 설정
  @Query("SELECT c FROM Chaebun c WHERE c.seqId = ?1 AND c.sts = ?2")
  Optional<Chaebun> findByIdAndSts(String seqId, String sts);

}
