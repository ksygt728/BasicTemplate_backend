/**
 * @파일명   : JwtExeption.java
 * @설명     : JWT 관련 예외 처리 클래스
 * @작성자   : 김승연
 * @작성일   : 2025.08.02
 * @변경이력 :
 *   2025.08.02     김승연       최초 생성
 */
package com.basic.app.exception.customException;

import com.basic.app.exception.ErrorCode;

import lombok.Getter;

@Getter
public class JwtExeption extends BaseException {

    public JwtExeption(ErrorCode errorCode) {
        super(errorCode);
    }
}