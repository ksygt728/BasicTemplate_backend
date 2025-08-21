/**
 * @파일명   : BusinessException.java
 * @설명     : 비즈니스 로직에서 발생할 수 있는 예외를 처리하기 위한 커스텀 예외 클래스
 *              - 비정상적인 처리를 위한 예외로, 예를 들어 데이터 유효성 검사 실패나 비즈니스 규칙 위반 등을 처리합니다.
 * @작성자   : 김승연
 * @작성일   : 2025.07.23
 * @변경이력 :
 *   2025.07.23     김승연       최초 생성
 */
package com.basic.app.exception.customException;

import com.basic.app.exception.ErrorCode;

import lombok.Getter;

@Getter
public class NotFoundException extends BaseException {

    private String additionalMessage = "";

    public NotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }

    public NotFoundException(ErrorCode errorCode, String additionalMessage) {
        super(errorCode);
        this.additionalMessage = additionalMessage;
    }
}