package com.example.catalog_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.catalog_service.config.PolarProperties;

@RestController
public class HomeController {

	// 사용자 정의 속성 엑세스를 위해 생성자 오토와이어링을 통해 주입된 빈
	private final PolarProperties polarProperties;

	public HomeController(PolarProperties polarProperties) {
		this.polarProperties = polarProperties;
	}

	@GetMapping("/")
	public String getGreeting() {
		// 설정 데이터 빈에서 가져온 환영 메시지를 사용
		return polarProperties.getGreeting();
	}
	
}
