package com.basic.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.basic.app.entity.Chaebun;

import jakarta.persistence.LockModeType;

/**
 * @파일명 : ChaebunRepository.java
 * @설명 : 채번 관리 레포지토리
 * @작성자 : 김승연
 * @작성일 : 2025.08.24
 * @변경이력 :
 *       2025.08.24 김승연 최초 생성
 */
@Repository
public interface ChaebunRepository extends JpaRepository<Chaebun, String> {

  /**
   * @기능 : 시퀀스 ID와 상태로 채번 정보를 비관적 락으로 조회 (동시성 제어)
   * @param seqId : 시퀀스 ID
   * @param sts   : 상태 코드
   * @return : 채번 정보 Optional 객체
   */
  @Lock(LockModeType.PESSIMISTIC_WRITE) // 동시 채번 방지를 위해 Lock 설정
  @Query("SELECT c FROM Chaebun c WHERE c.seqId = ?1 AND c.sts = ?2")
  Optional<Chaebun> findByIdAndSts(String seqId, String sts);

}
