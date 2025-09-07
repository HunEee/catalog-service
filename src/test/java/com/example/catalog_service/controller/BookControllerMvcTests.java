package com.example.catalog_service.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.catalog_service.exception.BookNotFoundException;
import com.example.catalog_service.service.BookService;


@WebMvcTest(BookController.class)
@Import(BookControllerAdvice.class)   // ExceptionHandler 로드
class BookControllerMvcTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Test
    void whenGetBookNotExistingThenShouldReturn404() throws Exception {
        String isbn = "73737313940";

        // 반드시 예외 객체를 던져야 한다
        given(bookService.viewBookDetails(isbn))
                .willThrow(new BookNotFoundException(isbn));

        mockMvc.perform(get("/books/" + isbn))
               .andExpect(status().isNotFound());
    }
}