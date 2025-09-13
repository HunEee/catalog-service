package com.example.catalog_service.domain;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import static org.assertj.core.api.Assertions.assertThat;

class BookValidationTests {

    // Validator 객체를 테스트 클래스 전체에서 사용할 수 있도록 선언
    private static Validator validator;

    // 테스트 시작 전에 Validator 초기화
    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void whenAllFieldsCorrectThenValidationSucceeds() {
        // 모든 값이 정상적으로 들어간 Book 객체 생성
    	var book = Book.of("1234567890", "Title", "Author", 9.90, "Polarsophia");

        // 유효성 검사 실행
        Set<ConstraintViolation<Book>> violations = validator.validate(book);

        // 위반사항이 없어야 한다.
        assertThat(violations).isEmpty();
    }

    @Test
    void whenIsbnNotDefinedThenValidationFails() {
        // ISBN을 비워둔 Book 객체 생성
    	var book = Book.of("", "Title", "Author", 9.90, "Polarsophia");

        // 유효성 검사 실행
        Set<ConstraintViolation<Book>> violations = validator.validate(book);

        // ISBN 관련 2개의 위반사항이 발생해야 함 (@NotBlank, @Pattern)
        assertThat(violations).hasSize(2);

        // 위반 메시지들을 리스트로 변환
        List<String> constraintViolationMessages = violations.stream()
                .map(ConstraintViolation::getMessage).collect(Collectors.toList());

        // 기대한 에러 메시지가 포함되어야 한다
        assertThat(constraintViolationMessages)
                .contains("The book ISBN must be defined.")
                .contains("The ISBN format must be valid.");
    }

    @Test
    void whenIsbnDefinedButIncorrectThenValidationFails() {
        // ISBN이 숫자가 아닌 잘못된 값으로 생성
    	var book = Book.of("a234567890", "Title", "Author", 9.90, "Polarsophia");

        // 유효성 검사 실행
        Set<ConstraintViolation<Book>> violations = validator.validate(book);

        // 하나의 위반만 발생해야 함
        assertThat(violations).hasSize(1);

        // 메시지는 ISBN 형식 에러여야 함
        assertThat(violations.iterator().next().getMessage())
                .isEqualTo("The ISBN format must be valid.");
    }

    @Test
    void whenTitleIsNotDefinedThenValidationFails() {
        // Title을 비워둔 Book 객체 생성
    	var book = Book.of("1234567890", "", "Author", 9.90, "Polarsophia");

        // 유효성 검사 실행
        Set<ConstraintViolation<Book>> violations = validator.validate(book);

        // Title 하나만 위반되어야 함
        assertThat(violations).hasSize(1);

        // 메시지 검증
        assertThat(violations.iterator().next().getMessage())
                .isEqualTo("The book title must be defined.");
    }

    @Test
    void whenAuthorIsNotDefinedThenValidationFails() {
        // Author를 비워둔 Book 객체 생성
    	var book = Book.of("1234567890", "Title", "", 9.90, "Polarsophia");
        // 유효성 검사 실행
        Set<ConstraintViolation<Book>> violations = validator.validate(book);

        // Author 하나만 위반
        assertThat(violations).hasSize(1);

        // 메시지 검증
        assertThat(violations.iterator().next().getMessage())
                .isEqualTo("The book author must be defined.");
    }

    @Test
    void whenPriceIsNotDefinedThenValidationFails() {
        // Price를 null로 설정
    	var book = Book.of("1234567890", "Title", "Author", null, "Polarsophia");

        // 유효성 검사 실행
        Set<ConstraintViolation<Book>> violations = validator.validate(book);

        // Price 하나만 위반
        assertThat(violations).hasSize(1);

        // 메시지 검증
        assertThat(violations.iterator().next().getMessage())
                .isEqualTo("The book price must be defined.");
    }

    @Test
    void whenPriceDefinedButZeroThenValidationFails() {
        // Price를 0으로 설정 (양수 아님)
    	var book = Book.of("1234567890", "Title", "Author", 0.0, "Polarsophia");

        // 유효성 검사 실행
        Set<ConstraintViolation<Book>> violations = validator.validate(book);

        // Price 하나만 위반
        assertThat(violations).hasSize(1);

        // 메시지 검증
        assertThat(violations.iterator().next().getMessage())
                .isEqualTo("The book price must be greater than zero.");
    }

    @Test
    void whenPriceDefinedButNegativeThenValidationFails() {
        // Price를 음수로 설정
    	var book = Book.of("1234567890", "Title", "Author", -9.90, "Polarsophia");

        // 유효성 검사 실행
        Set<ConstraintViolation<Book>> violations = validator.validate(book);

        // Price 하나만 위반
        assertThat(violations).hasSize(1);

        // 메시지 검증
        assertThat(violations.iterator().next().getMessage())
                .isEqualTo("The book price must be greater than zero.");
    }
    
    @Test
    void whenPublisherIsNotDefinedThenValidationSucceeds() {
        Book book = Book.of("1234567890", "Title", "Author", 9.90,null);
        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        assertThat(violations).isEmpty();
    }


}
