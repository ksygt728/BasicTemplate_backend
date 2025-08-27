/**
 * @파일명   : UserResDtoResDto.java
 * @설명     : 
 * @작성자   : 김승연
 * @작성일   : 2025.07.23
 * @변경이력 :
 *   2025.07.23     김승연       최초 생성
 */
package com.basic.app.dto.responseDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.basic.app.dto.requestDto.BbsReqDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@JsonIgnoreProperties(ignoreUnknown = true) // JwtAuthenticationFilter.java 에서 jackson ObejctMapper null값이 들어가는것을 무시
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResDto {

  private String userId; // 사용자아이디

  private String name; // 이름

  private String phoneNum; // 전화번호

  private String email; // 이메일

  private String role; // 역할

  private String userType; // 사용자타입

  private String gender; // 성별 (M, F)

  // User - Department (N:1) [Onwer]
  private DepartmentResDto department; // 부서코드

  // User - Bbs (1:N)
  // private List<BbsReqDto> bbsWritors = new ArrayList<BbsReqDto>(); // 작성한 게시글
  // 리스트

  // User - BbsComment (1:N)
  // private List<BbsCommentResDto> bbsCommentWritors = new
  // ArrayList<BbsCommentResDto>(); // 작성한 댓글 리스트

  // User - Notice (1:N)
  // private List<NoticeResDto> noticeWritors = new ArrayList<NoticeResDto>(); //
  // 작성한 공지 리스트

  // User - RoleUser (1:N)
  // private List<RoleUserResDto> roleUsers = new ArrayList<RoleUserResDto>(); //
  // 사용자가 가진 권한 리스트

}
