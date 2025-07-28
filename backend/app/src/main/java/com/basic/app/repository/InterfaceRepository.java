/**
 * @파일명   : InterfaceRepository.java
 * @설명     : 인터페이스 정보 레포지토리
 * @작성자   : 김승연
 * @작성일   : 2025.07.24
 * @변경이력 :
 *   2025.07.24     김승연       최초 생성
 */
package com.basic.app.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.basic.app.entity.Interface;

@Repository
public interface InterfaceRepository extends JpaRepository<Interface, String> {

  Page<Interface> findAllBySts(String string, Pageable pageable);

  Optional<Interface> findByIfIdAndSts(String ifId, String sts);

}
