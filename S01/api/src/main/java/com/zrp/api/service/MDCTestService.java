package com.zrp.api.service;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MDCTestService {
    
    public void process() {
        log.info("비즈니스 로직 처리 중...");
    }

}
