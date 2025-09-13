package com.example.catalog_service.repository;

import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.example.catalog_service.domain.Book;

// 엔티티(Book)와 기본 키 유형(Long)을 지정하면서 CRUD 연산을 제공하는 레포지토리를 확장
public interface BookRepository extends CrudRepository<Book,Long>{

	Optional<Book> findByIsbn(String isbn);
	boolean existsByIsbn(String isbn);

	@Modifying // 데이터 베이스 상태를 수정할 연산임을 나타냄
	@Transactional // 메서드가 트랜잭션으로 실행됨을 나타냄
	@Query("delete from Book where isbn = :isbn") // 스프링 데이터 메서드가 구현에 사용할 쿼리를 선언
	void deleteByIsbn(String isbn);

}
