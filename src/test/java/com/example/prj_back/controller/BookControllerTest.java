package com.example.prj_back.controller;

import com.example.prj_back.model.Book;
import com.example.prj_back.repository.BookRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
class BookControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private BookRepository bookRepository;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Test
    void getAllBooks_ShouldReturnListOfBooks() throws Exception {
        Book book1 = new Book(1L, "Book 1", "Author 1", "123", 2023, true);
        Book book2 = new Book(2L, "Book 2", "Author 2", "456", 2024, false);
        
        when(bookRepository.findAll()).thenReturn(Arrays.asList(book1, book2));
        
        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].title").value("Book 1"))
                .andExpect(jsonPath("$[1].title").value("Book 2"));
    }
    
    @Test
    void createBook_ShouldReturnCreatedBook() throws Exception {
        Book book = new Book(null, "New Book", "New Author", "789", 2024, true);
        Book savedBook = new Book(1L, "New Book", "New Author", "789", 2024, true);
        
        when(bookRepository.save(any(Book.class))).thenReturn(savedBook);
        
        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("New Book"));
    }
    
    @Test
    void getBookById_WhenBookExists_ShouldReturnBook() throws Exception {
        Book book = new Book(1L, "Test Book", "Test Author", "123", 2023, true);
        
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        
        mockMvc.perform(get("/api/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Book"));
    }
    
    @Test
    void getBookById_WhenBookNotExists_ShouldReturn404() throws Exception {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());
        
        mockMvc.perform(get("/api/books/1"))
                .andExpect(status().isNotFound());
    }
}