package com.basic.app.auth.rbac;

import java.util.List;

/**
 * @파일명 : ActioPermissionKeynType.java
 * @설명 : 권한 키 생성 유틸리티 클래스(RBAC)
 * @작성자 : 김승연
 * @작성일 : 2025.12.14
 * @변경이력 :
 *       2025.12.14 김승연 최초 생성
 */
public final class PermissionKey {

    private PermissionKey() {
    }

    /**
     * @기능 주어진 메뉴 코드와 액션 유형 문자열을 기반으로 권한 키 목록을 생성합니다.
     * @설명 : 메뉴 코드 "MENU001"과 액션 유형 "CRUD"가 주어지면,
     * 반환되는 목록은 ["MENU001_CREATE", "MENU001_READ", "MENU001_UPDATE", "MENU001_DELETE"]가 됩니다.
     * @return List<String> 권한 키 목록
     */
    public static List<String> of(String menuCode, String actionType) {

        // actionType 문자열을 대문자로 변환 후 각 문자별로 PermissionKey 생성
        // 예: menuCode "CRUD" -> ['C', 'R', 'U', 'D']
        // 아래에서 각 문자에 대해 PermissionKey를 만듦
        return actionType.toUpperCase()
                .chars()
                .mapToObj(c -> menuCode + "_" + convertActionChar((char) c))
                .toList();
    }

    private static String convertActionChar(char action) {
        return switch (action) {
            case 'C' -> "CREATE";
            case 'R' -> "READ";
            case 'U' -> "UPDATE";
            case 'D' -> "DELETE";
            default -> throw new IllegalArgumentException("Invalid action type: " + action);
        };
    }
}