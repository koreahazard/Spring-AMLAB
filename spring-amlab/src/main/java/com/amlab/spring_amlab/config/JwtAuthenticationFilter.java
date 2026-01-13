package com.amlab.spring_amlab.config;

import com.amlab.spring_amlab.account.entity.User;
import com.amlab.spring_amlab.account.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        //헤더에서 토큰 추출
        String token = resolveToken(request);

        // 토큰이 존재하고 유효하면
        if (token != null && jwtProvider.validateToken(token)) {

            // 토큰에서 userId 가져오기
            Long userId = jwtProvider.getUserId(token);

            //  DB에서 유저 조회
            User user = userRepository.findById(userId).orElse(null);

            if (user != null) {
                //  Spring Security 인증 객체 생성
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                user,
                                null,
                                Collections.emptyList() // 권한 없으면 비워두기
                        );

                //  SecurityContext에 등록
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        //  필터 체인 계속 진행
        filterChain.doFilter(request, response);
    }

    /** Authorization 헤더에서 "Bearer " 제거하고 토큰 반환 */
    private String resolveToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }
}
