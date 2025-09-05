
package com.basic.app.exception.customException;

import com.basic.app.exception.ErrorCode;

import lombok.Getter;

/**
 * @파일명 : ClientActionException.java
 * @설명 : 클라이언트의 잘못된 요청으로 인해 발생하는 예외를 처리하기 위한 커스텀 예외 클래스
 * @작성자 : 김승연
 * @작성일 : 2025.07.23
 * @변경이력 :
 *       2025.07.23 김승연 최초 생성
 */
@Getter
public class ClientActionException extends BaseException {

    private String additionalMessage;

    /**
     * @기능 : ClientActionException 생성자
     * @param errorCode 에러 코드
     */
    public ClientActionException(ErrorCode errorCode) {
        super(errorCode);
    }

    /**
     * @기능 : ClientActionException 생성자 (추가 메시지 포함)
     * @param errorCode         에러 코드
     * @param additionalMessage 추가 메시지
     */
    public ClientActionException(ErrorCode errorCode, String additionalMessage) {
        super(errorCode);
        this.additionalMessage = additionalMessage;
    }

}