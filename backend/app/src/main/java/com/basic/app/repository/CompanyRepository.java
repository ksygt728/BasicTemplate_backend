/**
 * @파일명   : UserRepository.java
 * @설명     : 유저 정보 레포지토리
 * @작성자   : 김승연
 * @작성일   : 2025.07.31
 * @변경이력 :
 *   2025.07.31     김승연       최초 생성
 */
package com.basic.app.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.basic.app.entity.Company;
import com.basic.app.entity.Department;

@Repository
public interface CompanyRepository extends JpaRepository<Company, String> {

}
