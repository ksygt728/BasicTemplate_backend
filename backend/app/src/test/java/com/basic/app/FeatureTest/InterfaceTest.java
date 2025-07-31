package com.basic.app.FeatureTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Map;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.basic.app.api.ModelMapperUtils;
import com.basic.app.dto.requestDto.InterfaceReqDto;
import com.basic.app.dto.responseDto.InterfaceResDto;
import com.basic.app.entity.Interface;
import com.basic.app.repository.InterfaceRepository;
import com.basic.app.util.Status;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;

@Log4j2
@SpringBootTest // 전체 Spring ApplicationContext를 로딩해서 통합 테스트를 실행
@AutoConfigureMockMvc // MockMvc를 자동 설정하여 주입 가능하게 만듦
@Transactional // 테스트가 끝나면 자동으로 DB 롤백 처리 (테스트 데이터가 남지 않음)
public class InterfaceTest {

    @Autowired
    private MockMvc mockMvc; // Controller 테스트용 HTTP 요청 도구

    @Autowired
    private InterfaceRepository interfaceRepository;

    @Autowired
    private ObjectMapper objectMapper; // 객체 → JSON 문자열 변환용 Jackson 도구

    @BeforeAll
    static void setUpOnce() {
        log.info("📦 테스트 전체 시작 전 단 1회 실행 (@BeforeAll)");
        // DB 스키마 초기화나 공통 설정 작업
    }

    @BeforeEach
    void setUpEach(TestInfo testInfo) {
        log.info("🔄 테스트 시작: " + testInfo.getDisplayName() + " (@BeforeEach)");
        // 각 테스트 전에 공통 데이터 세팅 또는 Mock 준비
    }

    @AfterEach
    void tearDownEach(TestInfo testInfo) {
        log.info("✅ 테스트 종료: " + testInfo.getDisplayName() + " (@AfterEach)");
    }

    @AfterAll
    static void cleanUpOnce() {
        log.info("🧹 테스트 전체 종료 후 단 1회 실행 (@AfterAll)");
        // 파일 삭제, 서버 연결 종료 등 자원 해제
    }

    /**
     * 테스트에 사용할 사용자 데이터를 제공하는 메서드
     * 각 User 인스턴스가 하나의 테스트 케이스가 됨
     */
    static Stream<InterfaceReqDto> interfaceInitData() {
        return Stream.of(
                new InterfaceReqDto("IF001", "회원가입 요청", "/api/v1/users/signup"),
                new InterfaceReqDto("IF002", "로그인 요청", "/api/v1/users/login"),
                new InterfaceReqDto("IF003", "로그아웃 요청", "/api/v1/users/logout"),
                new InterfaceReqDto("IF004", "회원 정보 조회", "/api/v1/users/profile"),
                new InterfaceReqDto("IF005", "회원 정보 수정", "/api/v1/users/profile/update"),
                new InterfaceReqDto("IF006", "비밀번호 변경", "/api/v1/users/password"),
                new InterfaceReqDto("IF007", "비밀번호 초기화", "/api/v1/users/password/reset"),
                new InterfaceReqDto("IF008", "이메일 인증", "/api/v1/users/email/verify"),
                new InterfaceReqDto("IF009", "전화번호 인증", "/api/v1/users/phone/verify"),
                new InterfaceReqDto("IF010", "유저 리스트 조회", "/api/v1/admin/users"),
                new InterfaceReqDto("IF011", "게시글 목록 조회", "/api/v1/posts"),
                new InterfaceReqDto("IF012", "게시글 상세 조회", "/api/v1/posts/{id}"),
                new InterfaceReqDto("IF013", "게시글 등록", "/api/v1/posts/new"),
                new InterfaceReqDto("IF014", "게시글 수정", "/api/v1/posts/{id}/edit"),
                new InterfaceReqDto("IF015", "게시글 삭제", "/api/v1/posts/{id}/delete"),
                new InterfaceReqDto("IF016", "댓글 등록", "/api/v1/comments/new"),
                new InterfaceReqDto("IF017", "댓글 조회", "/api/v1/comments/{postId}"),
                new InterfaceReqDto("IF018", "댓글 삭제", "/api/v1/comments/{id}/delete"),
                new InterfaceReqDto("IF019", "파일 업로드", "/api/v1/files/upload"),
                new InterfaceReqDto("IF020", "파일 다운로드", "/api/v1/files/download/{id}"),
                new InterfaceReqDto("IF021", "알림 조회", "/api/v1/notifications"),
                new InterfaceReqDto("IF022", "알림 읽음 처리", "/api/v1/notifications/read"),
                new InterfaceReqDto("IF023", "관리자 대시보드", "/api/v1/admin/dashboard"),
                new InterfaceReqDto("IF024", "통계 조회", "/api/v1/admin/stats"),
                new InterfaceReqDto("IF025", "접속 이력 조회", "/api/v1/logs/access"),
                new InterfaceReqDto("IF026", "오류 로그 조회", "/api/v1/logs/error"),
                new InterfaceReqDto("IF027", "권한 목록 조회", "/api/v1/roles"),
                new InterfaceReqDto("IF028", "권한 부여", "/api/v1/roles/assign"),
                new InterfaceReqDto("IF029", "토큰 갱신", "/api/v1/auth/refresh"),
                new InterfaceReqDto("IF030", "세션 체크", "/api/v1/auth/session/check"));

    }

    /**
     * interfaceInitData 메서드에서 공급한 Interface 데이터를 받아 반복 실행됨
     */
    @ParameterizedTest // 파라미터 기반 테스트
    @MethodSource("interfaceInitData") // interfaceInitData 메서드를 통해 데이터를 공급
    @DisplayName("[REQ_ADM_024] [화면 : 기준 정보 > 인터페이스 관리] [기능 : 인터페이스 추가]")
    void 인터페이스저장(InterfaceReqDto expected) throws Exception {
        // 1. 테스트에 사용할 JSON 본문을 생성 (User 객체를 JSON으로 변환)
        // String json = objectMapper.writeValueAsString(expected);

        // 1. DTO를 Map으로 변환
        Map<String, String> paramMap = objectMapper.convertValue(expected, new TypeReference<>() {
        });
        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        paramMap.forEach(multiValueMap::add);

        // 2. 회원가입 API 호출 (POST 요청)
        mockMvc.perform(
                post("/admin/interface") // 회원가입 API 엔드포인트
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED) // form Type 기본 인코딩 요청 본문 타입 설정
                        .params(multiValueMap) // 요청 파라미터 설정

        // .content(json) // 요청 바디에 JSON 데이터를 포함
        )
                .andExpect(status().isOk()); // 응답이 200 OK인지 확인

        // 3. DB에서 이메일 기준으로 방금 저장한 사용자 조회
        Interface savedActual = interfaceRepository.findByIfIdAndSts(expected.getIfId(), Status.POSITIVE)
                .orElseThrow(() -> new AssertionError("DB에 없는 정보: " + expected.getIfId()));

        // 4. ModelMapperUtils를 사용하여 실제 엔티티를 DTO로 변환
        InterfaceResDto savedActualDto = ModelMapperUtils.map(savedActual, InterfaceResDto.class); // 실제 엔티티

        // 5. AssertJ로 실제 저장된 사용자와 예상 데이터를 비교
        // ID 필드는 자동 생성되므로 비교 대상에서 제외
        assertThat(savedActualDto)
                .usingRecursiveComparison() // 객체 내부 필드까지 재귀적으로 비교
                .ignoringFields("createUser", "createDate", "updateUser", "timestamp") // 생성자, 생성일시, 수정자, 수정일시는 제외
                .isEqualTo(expected); // 나머지 필드가 모두 일치하는지 확인
    }
}
