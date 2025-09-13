package com.example.catalog_service.service;

import org.springframework.stereotype.Service;

import com.example.catalog_service.domain.Book;
import com.example.catalog_service.exception.BookAlreadyExistsException;
import com.example.catalog_service.exception.BookNotFoundException;
import com.example.catalog_service.repository.BookRepository;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Iterable<Book> viewBookList() {
        return bookRepository.findAll();
    }

    public Book viewBookDetails(String isbn) {
        return bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new BookNotFoundException(isbn));
    }

    public Book addBookToCatalog(Book book) {
        if (bookRepository.existsByIsbn(book.isbn())) {
            throw new BookAlreadyExistsException(book.isbn());
        }
        return bookRepository.save(book);
    }

    public void removeBookFromCatalog(String isbn) {
        bookRepository.deleteByIsbn(isbn);
    }

	public Book editBookDetails(String isbn, Book book) {
		return bookRepository.findByIsbn(isbn)
				.map(existingBook -> {
					var bookToUpdate = new Book(
							existingBook.id(), // 기존 책의 식별자를 사용
							existingBook.isbn(),
							book.title(),
							book.author(),
							book.price(),
							book.publisher(),
							existingBook.createdDate(),	// 기존 책 레코드의 생성 날짜 이용
							existingBook.lastModifiedDate(), // 기존 책 레코드의 마지막 수정 날짜 사용-> 업데이트가 성공하면 스프링 데이터에 의해 자동으로 변경됨
							existingBook.version()); // 기존 책 버전 사용 시 업데이트가 성공하면 자동으로 증가
					return bookRepository.save(bookToUpdate);
				})
				.orElseGet(() -> addBookToCatalog(book));
	}

}
