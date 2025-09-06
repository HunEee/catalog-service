package com.example.catalog_service.repository;

import java.util.Optional;

import com.example.catalog_service.domain.Book;

public interface BookRepository {

	Iterable<Book> findAll();
	Optional<Book> findByIsbn(String isbn);
	boolean existsByIsbn(String isbn);
	Book save(Book book);
	void deleteByIsbn(String isbn);

}
