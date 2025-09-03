/**
 * @파일명   : BbsCommentRepository.java
 * @설명     : 게시판 댓글 정보 레포지토리
 * @작성자   : 김승연
 * @작성일   : 2025.09.2
 * @변경이력 :
 *   2025.09.02     김승연       최초 생성
 */
package com.basic.app.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.basic.app.entity.BbsComment;

@Repository
public interface BbsCommentRepository extends JpaRepository<BbsComment, String> {

  @Query("SELECT bc FROM BbsComment bc WHERE bc.bbs.bbsId = ?1 AND bc.sts = ?2")
  Page<BbsComment> findAllByBbsIdAndSts(String bbsId, String sts, Pageable pageable);

  @Query("SELECT bc FROM BbsComment bc WHERE bc.bbs.bbsId = ?1 AND bc.sts = ?2")
  List<BbsComment> findAllByBbsIdAndSts(String bbsId, String sts);

}
