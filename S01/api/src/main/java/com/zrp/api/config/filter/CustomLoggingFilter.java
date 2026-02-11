package com.zrp.api.config.filter;

import java.io.IOException;
import java.util.UUID;

import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component // 스프링 Bean으로 등록
@Order(Ordered.HIGHEST_PRECEDENCE) // 필터 중 가장 먼저 실행되도록 설정!!
public class CustomLoggingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
        HttpServletRequest request, 
        HttpServletResponse response, 
        FilterChain filterChain
    ) throws ServletException, IOException {
        // 1. 고유 ID 생성 (UUID의 앞 8자리만 사용해서 짧게 만듦)
        String traceId = UUID.randomUUID().toString().substring(0, 8);
        
        // 2. MDC에 저장 (이제부터 이 스레드에서 찍는 모든 로그는 이 ID를 공유함)
        MDC.put("traceId", traceId);

        response.setHeader("X-Trace-Id", traceId); // 응답 헤더에 traceId를 담아서 보냄

        try {
            // 3. 다음 로직 (컨트롤러 등) 실행
            filterChain.doFilter(request, response);
        } finally {
            // 4. 필수!!! 요청이 끝나면 MDC를 비워줘야 함 (스레드 풀 재사용 문제 방지)
            MDC.clear();
        }
            
    }

}
