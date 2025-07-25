/**
 * @파일명   : BusinessException.java
 * @설명     : 비즈니스 로직에서 발생할 수 있는 예외를 처리하기 위한 커스텀 예외 클래스
 *             - 정상적인 비즈니스에서 발생할 수 있는 예외를 처리합니다.
 * @작성자   : 김승연
 * @작성일   : 2025.07.23
 * @변경이력 :
 *   2025.07.23     김승연       최초 생성
 */
package com.basic.app.exception.customException;

import com.basic.app.exception.ErrorCode;

import lombok.Getter;

@Getter
public abstract class BaseException extends RuntimeException {
  private ErrorCode errorCode;

  public BaseException(ErrorCode errorCode) {
    super();
    this.errorCode = errorCode;
  }

}