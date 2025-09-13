package com.example.catalog_service.demo;

import java.util.List;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.example.catalog_service.domain.Book;
import com.example.catalog_service.repository.BookRepository;

@Component
@Profile("testdata") // testdata 프로파일에 할당 -> testdata 프로파일이 활성화될 때만 로드됨
public class BookDataLoader {

	private final BookRepository bookRepository;

	public BookDataLoader(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	//ApplicationReadyEvent가 발생하면 테스트 데이터 생성이 시작된다.
	// 이 이벤트는 애플리케이션 시작 단계가 완료되면 발생
	@EventListener(ApplicationReadyEvent.class)
	public void loadBookTestData() {
		// 빈 데이터베이스로 시작하기 위해 기존 책이 있다면 모두 삭제한다.
		bookRepository.deleteAll();
		
		// 프레임 워크가 내부적으로 식별자와 버전에 대한 할당 값을 처리한다.
		var book1 = Book.of("1234567891", "Northern Lights", "Lyra Silverstar", 9.90, "Polarsophia");
		var book2 = Book.of("1234567892", "Polar Journey", "Iorek Polarson", 12.90, "Polarsophia");
		
		// 여러 객체를 한꺼번에 저장
		bookRepository.saveAll(List.of(book1, book2));
		
	}

}
