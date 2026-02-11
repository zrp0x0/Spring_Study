package com.zrp.api.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zrp.api.dto.MemberDto;
import com.zrp.api.service.MDCTestService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.extern.slf4j.Slf4j;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory; // @Slf4j를 사용하면 사용하지 않음

@Slf4j
@RestController
@RequestMapping("/api/v1/get-api")
public class GetController {

    // private final Logger LOGGER = LoggerFactory.getLogger(GetController.class);
    private final MDCTestService mdcTestService;
    
    @Autowired
    public GetController(MDCTestService mdcTestService) {
        this.mdcTestService = mdcTestService;
    }

    // http://localhost:8080/api/v1/get-api/hello
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String getHello() {
        log.info("getHello 메서드가 호출되었습니다.");
        return "Hello World";
    }

    // http://localhost:8080/api/v1/get-api/name
    @GetMapping(value = "/name")
    public String getName() {
        return "Flature";
    }

    // http://localhost:8080/api/v1/get-api/variable1/{String 값}
    @GetMapping(value = "/variable1/{variable}")
    public String getVaribale1(@PathVariable String variable) {
        return variable;
    }

    // http://localhost:8080/api/v1/get-api/variable2/{String 값}
    @GetMapping(value = "/variable2/{variable}")
    public String getVaribale2(@PathVariable(value = "variable") String var) {
        return var;
    }

    // http://localhost:8080/api/v1/get-api/request1?name=value1&email=value2&organization=value3
    @Operation(summary = "GET 메서드 예제", description = "@RequestParam을 활용한 GET 메서드")
    @GetMapping(value = "/request1")
    public String getRequestParam1(
        @Parameter(name = "namefield", description = "이름", required = true) @RequestParam("name") String name,
        @RequestParam String email,
        @RequestParam String organization
    ) {
        return name + " " + email + " " + organization;
    }

    // http://localhost:8080/api/v1/get-api/request2?name=value1&email=value2&organization=value3
    @GetMapping(value = "/request2")
    public String getRequestParam2(
        @RequestParam Map<String, String> param
    ) {
        StringBuilder sb = new StringBuilder();
        param.forEach((key, value) -> sb.append(key + " : " + value + "\n"));

        return sb.toString();
    }

    // http://localhost:8080/api/v1/get-api/request3?name=value1&email=value2&organization=value3
    @GetMapping(value = "/request3")
    public String getRequestParam3(@ModelAttribute MemberDto memberDto) {
        return memberDto.toString();
    }

    @GetMapping(value = "/logger-test/{var}")
    public Integer loggerTest(
        @PathVariable("var") Integer var
    ) {
        // log.info("var {}", var);
        // if (true) {
        //     throw new RuntimeException("의도적인 에러");
        // }
        return var;
    }

    @GetMapping(value = "/mdc-test")
    public String mdcTest() {
        log.info("MDC 테스트");
        mdcTestService.process(); // 서비스 계층으로 이동
        log.info("응답 반환");
        if (true) {
            throw new RuntimeException("MDC Test 에러");
        }
        return "ok";
    }

}
