package com.basic.app.api;

import java.util.List;

import org.springframework.data.domain.Page;

import lombok.Getter;
import lombok.Setter;

/**
 * @파일명 : PageResponse.java
 * @설명 : 페이징 응답 래퍼 클래스
 * @작성자 : 김승연
 * @작성일 : 2025.07.24
 * @변경이력 :
 *       2025.07.24 김승연 최초 생성
 */
@Getter
@Setter
public class PageResponse<T> {
  private List<T> content;
  private int page; // 현재 페이지 번호
  private int size; // 페이지 크기
  private long totalElements; // 전체 요소 수
  private int totalPages; // 전체 페이지 수
  private boolean first;
  private boolean last;

  /**
   * @기능 : Spring Data Page 객체를 PageResponse로 변환하는 생성자
   * @param page Spring Data Page 객체
   */
  public PageResponse(Page<T> page) {
    this.content = page.getContent();
    this.page = page.getNumber();
    this.size = page.getSize();
    this.totalElements = page.getTotalElements();
    this.totalPages = page.getTotalPages();
    this.first = (page.getNumber() >= page.getTotalPages() || page.isFirst()) ? true : false;
    this.last = page.isLast();
  }

}