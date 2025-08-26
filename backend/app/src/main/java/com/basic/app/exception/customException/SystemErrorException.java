/**
 * @파일명   : SystemErrorException.java
 * @설명     : try-catch, 각종 에러가 발생할 수 있는 부분 등 예외를 처리하기 위한 클래스입니다.
 *             - 시스템 에러나 예기치 못한 상황에서 발생하는 예외를 처리합니다. (관리자가 확인해야하는 부분)
 * @작성자   : 김승연
 * @작성일   : 2025.07.23
 * @변경이력 :
 *   2025.07.23     김승연       최초 생성
 */
package com.basic.app.exception.customException;

import com.basic.app.exception.ErrorCode;

import lombok.Getter;

@Getter
public class SystemErrorException extends BaseException {

    private String additionalMessage;
    private Exception e;

    public SystemErrorException(ErrorCode errorCode) {
        super(errorCode);
    }

    public SystemErrorException(ErrorCode errorCode, String additionalMessage) {
        super(errorCode);
        this.additionalMessage = additionalMessage;
    }

    public SystemErrorException(Exception e, ErrorCode errorCode, String additionalMessage) {
        super(errorCode);
        this.e = e;
        this.additionalMessage = additionalMessage;
    }

}