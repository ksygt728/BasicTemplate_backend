package com.basic.app.api;

import java.util.List;

import org.springframework.data.domain.Page;

import lombok.Getter;
import lombok.Setter;

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

  public PageResponse(Page<T> page) {
    this.content = page.getContent();
    this.page = page.getNumber();
    this.size = page.getSize();
    this.totalElements = page.getTotalElements();
    this.totalPages = page.getTotalPages();
    this.first = (page.getNumber() >= page.getTotalPages() || page.isFirst()) ? true : false;
    this.last = page.isLast();
  }

  // getters, setters (or use Lombok @Getter)
}