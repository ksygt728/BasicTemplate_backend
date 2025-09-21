
package com.basic.app.util;

import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.Setter;

/**
 * @파일명 : UserRequestInfoManager.java
 * @설명 : 사용자 요청 정보를 관리하는 클래스
 * @작성자 : 김승연
 * @작성일 : 2025.08.6
 * @변경이력 :
 *       2025.08.6 김승연 최초 생성
 */
@Getter
@Setter
public class UserRequestInfoManager {

  private String userId;
  private String ipAddr;
  private String userAgent;
  private String requestUri;
  private String httpMethod;

  /**
   * @기능 : UserRequestInfoManager 생성자
   * @param request HTTP 요청 객체
   */
  public UserRequestInfoManager(HttpServletRequest request) {
    this.userId = getCurrentUserId();
    this.ipAddr = getClientIPv4(request);
    this.userAgent = request.getHeader("User-Agent");
    this.requestUri = request.getRequestURI();
    this.httpMethod = request.getMethod();
  }

  /**
   * @기능 : 현재 인증된 사용자 ID 가져오기
   * @return 사용자 ID 또는 "anonymous"
   */
  public String getCurrentUserId() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    return auth != null ? auth.getName() : "anonymous";
  }

  /**
   * @기능 : 클라이언트 IPv4 주소 가져오기
   * @param request HTTP 요청 객체
   * @return IPv4 주소
   */
  public String getClientIPv4(HttpServletRequest request) {

    String ip = request.getHeader("X-Forwarded-For");

    if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getRemoteAddr();
    }

    // 로컬에서 테스트 시 IPv6로 들어오는 경우 처리
    if ("0:0:0:0:0:0:0:1".equals(ip) || "::1".equals(ip)) {
      ip = "127.0.0.1";
    }

    // IPv6가 들어왔을 때 가능한 경우 IPv4로 변환
    try {
      InetAddress inetAddress = InetAddress.getByName(ip);

      if (inetAddress instanceof Inet6Address) {
        // IPv4로 매핑 가능한 경우
        byte[] ipv4Bytes = Arrays.copyOfRange(inetAddress.getAddress(), 12, 16);
        InetAddress ipv4Inet = InetAddress.getByAddress(ipv4Bytes);
        return ipv4Inet.getHostAddress();
      }

      return inetAddress.getHostAddress();

    } catch (UnknownHostException e) {
      return ip; // fallback
    }

  }

}
