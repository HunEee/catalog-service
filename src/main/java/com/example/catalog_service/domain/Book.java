package com.example.catalog_service.domain;

import java.time.Instant;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record Book (
	
    @Id
    Long id, //이 필드를 엔티티에 대한 기본 키로 식별
	
    @NotBlank(message = "The book ISBN must be defined.")
    @Pattern(regexp = "^([0-9]{10}|[0-9]{13})$", message = "The ISBN format must be valid.")
    String isbn,

    @NotBlank(message = "The book title must be defined.")
    String title,

    @NotBlank(message = "The book author must be defined.")
    String author,

    @NotNull(message = "The book price must be defined.")
    @Positive(message = "The book price must be greater than zero.")
    Double price,
    
    String publisher,

    @CreatedDate // 엔티티가 생성된 때
    Instant createdDate,

    @LastModifiedDate // 엔티티가 마지막으로 수정된 때
    Instant lastModifiedDate,

    @Version // 낙관적 잠금을 위해 사용되는 엔티티 버전 번호
    int version
    
){
	
    public static Book of(String isbn, String title, String author, Double price, String publisher) {
        return new Book(null, isbn, title, author, price, publisher, null, null, 0);
        //ID가 Null이고 버전이 0이면 새로운 엔티티로 인식
    }
	
}

