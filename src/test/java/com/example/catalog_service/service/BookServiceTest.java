package com.example.catalog_service.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.catalog_service.exception.BookAlreadyExistsException;
import com.example.catalog_service.exception.BookNotFoundException;
import com.example.catalog_service.repository.BookRepository;
import com.example.catalog_service.domain.Book;


//JUnit5와 Mockito를 통합하기 위한 확장 기능 추가
@ExtendWith(MockitoExtension.class)
class BookServiceTest {
	
	 // BookRepository를 가짜(Mock) 객체로 생성
	 @Mock
	 private BookRepository bookRepository;
	
	 // BookService를 테스트할 때,
	 // 위에서 만든 Mock 객체(bookRepository)를 주입해서 사용할 수 있게 함
	 @InjectMocks
	 private BookService bookService;
	
	 @Test
	 void whenBookToCreateAlreadyExistsThenThrows() {
	     // given: 이미 존재하는 ISBN을 가진 책 정보 준비
	     var bookIsbn = "1234561232";
	     var bookToCreate = new Book(bookIsbn, "Title", "Author", 9.90);
	
	     // repository.existsByIsbn() 호출 시 true를 반환하도록 Mock 설정
	     when(bookRepository.existsByIsbn(bookIsbn)).thenReturn(true);
	
	     // when & then: 동일한 ISBN으로 책 추가 시 예외가 발생해야 함
	     assertThatThrownBy(() -> bookService.addBookToCatalog(bookToCreate))
	             .isInstanceOf(BookAlreadyExistsException.class) // 예외 타입 검증
	             .hasMessage("A book with ISBN " + bookIsbn + " already exists."); // 예외 메시지 검증
	 }
	
	 @Test
	 void whenBookToReadDoesNotExistThenThrows() {
	     // given: 존재하지 않는 ISBN 설정
	     var bookIsbn = "1234561232";
	
	     // repository.findByIsbn() 호출 시 빈 결과 반환하도록 Mock 설정
	     when(bookRepository.findByIsbn(bookIsbn)).thenReturn(Optional.empty());
	
	     // when & then: 책 조회 시 BookNotFoundException 예외가 발생해야 함
	     assertThatThrownBy(() -> bookService.viewBookDetails(bookIsbn))
	             .isInstanceOf(BookNotFoundException.class) // 예외 타입 검증
	             .hasMessage("The book with ISBN " + bookIsbn + " was not found."); // 예외 메시지 검증
	 }

}

