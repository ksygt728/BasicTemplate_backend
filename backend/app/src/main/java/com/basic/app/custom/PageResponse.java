/**
 * @파일명   : PageResponse.java
 * @설명     : 페이징 처리된 REST API 응답 래퍼 클래스
 * @작성자   : 김승연
 * @작성일   : 2025.07.24
 * @변경이력 :
 *   2025.07.24     김승연       최초 생성
 */
package com.basic.app.custom;

import java.util.List;

import org.springframework.data.domain.Page;

public class PageResponse<T> {
  private List<T> content;
  private int page;
  private int size;
  private int totalPages;
  private long totalElements;

  public PageResponse(Page<T> page) {
    this.content = page.getContent();
    this.page = page.getNumber();
    this.size = page.getSize();
    this.totalPages = page.getTotalPages();
    this.totalElements = page.getTotalElements();
  }
}