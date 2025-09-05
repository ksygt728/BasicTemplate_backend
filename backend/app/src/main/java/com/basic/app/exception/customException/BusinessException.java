
package com.basic.app.exception.customException;

import com.basic.app.exception.ErrorCode;

import lombok.Getter;

/**
 * @파일명 : BusinessException.java
 * @설명 : 비즈니스 로직에서 발생할 수 있는 예외를 처리하기 위한 커스텀 예외 클래스
 *     - 정상적인 비즈니스에서 발생할 수 있는 예외를 처리합니다.
 * @작성자 : 김승연
 * @작성일 : 2025.07.23
 * @변경이력 :
 *       2025.07.23 김승연 최초 생성
 */
@Getter
public class BusinessException extends BaseException {

    private String additionalMessage;

    /**
     * @기능 : BusinessException 생성자
     * @param errorCode 에러 코드
     */
    public BusinessException(ErrorCode errorCode) {
        super(errorCode);
    }

    /**
     * @기능 : BusinessException 생성자 (추가 메시지 포함)
     * @param errorCode         에러 코드
     * @param additionalMessage 추가 메시지
     */
    public BusinessException(ErrorCode errorCode, String additionalMessage) {
        super(errorCode);
        this.additionalMessage = additionalMessage;
    }
}