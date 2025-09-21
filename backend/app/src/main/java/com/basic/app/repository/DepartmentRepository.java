package com.basic.app.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.basic.app.entity.Department;

/**
 * @파일명 : DepartmentRepository.java
 * @설명 : 부서 정보 레포지토리
 * @작성자 : 김승연
 * @작성일 : 2025.07.31
 * @변경이력 :
 *       2025.07.31 김승연 최초 생성
 */
@Repository
public interface DepartmentRepository extends JpaRepository<Department, String> {

  /**
   * @기능 : 상태별로 부서 목록을 페이징 조회
   * @param sts      : 상태 코드
   * @param pageable : 페이징 정보
   * @return : 페이징된 부서 목록
   */
  Page<Department> findAllBySts(String sts, Pageable pageable);

  /**
   * @기능 : 부서 코드와 상태로 부서 정보를 조회
   * @param deptCode : 부서 코드
   * @param sts      : 상태 코드
   * @return : 부서 정보 Optional 객체
   */
  Optional<Department> findByDeptCodeAndSts(String deptCode, String sts);

}
