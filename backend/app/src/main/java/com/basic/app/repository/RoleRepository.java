/**
 * @파일명   : RoleRepository.java
 * @설명     : 권한 레포지토리
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
import com.basic.app.entity.Role;

import jakarta.persistence.LockModeType;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {

}
