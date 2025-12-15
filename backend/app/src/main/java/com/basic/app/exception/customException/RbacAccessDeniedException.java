
package com.basic.app.exception.customException;

import com.basic.app.exception.ErrorCode;

import lombok.Getter;

/**
 * @파일명 : RbacAccessDeniedException.java
 * @설명 : RBAC 인가 실패 시 발생하는 커스텀 예외 클래스(AOP에서만 사용)
 * @작성자 : 김승연
 * @작성일 : 2025.12.14
 * @변경이력 :
 *       2025.12.14 김승연 최초 생성
 */
@Getter
public class RbacAccessDeniedException extends BaseException {

    private String additionalMessage;

    /**
     * @기능 : RbacAccessDeniedException 생성자
     * @param errorCode 에러 코드
     */
    public RbacAccessDeniedException(ErrorCode errorCode) {
        super(errorCode);
    }

    /**
     * @기능 : RbacAccessDeniedException 생성자 (추가 메시지 포함)
     * @param errorCode         에러 코드
     * @param additionalMessage 추가 메시지
     */
    public RbacAccessDeniedException(ErrorCode errorCode, String additionalMessage) {
        super(errorCode);
        this.additionalMessage = additionalMessage;
    }
}