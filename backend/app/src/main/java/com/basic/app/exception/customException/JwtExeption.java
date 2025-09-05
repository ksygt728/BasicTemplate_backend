
package com.basic.app.exception.customException;

import com.basic.app.exception.ErrorCode;

import lombok.Getter;

/**
 * @파일명 : JwtExeption.java
 * @설명 : JWT 관련 예외 처리 클래스
 * @작성자 : 김승연
 * @작성일 : 2025.08.02
 * @변경이력 :
 *       2025.08.02 김승연 최초 생성
 */
@Getter
public class JwtExeption extends BaseException {

    /**
     * @기능 : JwtExeption 생성자
     * @param errorCode 에러 코드
     */
    public JwtExeption(ErrorCode errorCode) {
        super(errorCode);
    }
}