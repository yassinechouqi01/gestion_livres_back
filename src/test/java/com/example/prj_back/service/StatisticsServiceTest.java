package com.example.prj_back.service;

import com.example.prj_back.dto.*;
import com.example.prj_back.model.Book;
import com.example.prj_back.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StatisticsServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private StatisticsService statisticsService;

    private Book book1;
    private Book book2;
    private Book book3;

    @BeforeEach
    void setUp() {
        book1 = new Book(1L, "Java Programming", "John Doe", "123456789", 2023, true);
        book1.setCategory("Programming");
        book1.setBorrowCount(5);
        book1.setDateAdded(LocalDateTime.now().minusDays(10));
        
        book2 = new Book(2L, "Spring Boot Guide", "Jane Smith", "987654321", 2024, false);
        book2.setCategory("Programming");
        book2.setBorrowCount(3);
        book2.setDateAdded(LocalDateTime.now().minusDays(5));
        
        book3 = new Book(3L, "Database Design", "Bob Wilson", "456789123", 2022, true);
        book3.setCategory("Database");
        book3.setBorrowCount(8);
        book3.setDateAdded(LocalDateTime.now().minusDays(50));
    }

    @Test
    void getCompleteStatistics_ShouldReturnCompleteStats() {
        // Given
        List<Book> books = Arrays.asList(book1, book2, book3);
        when(bookRepository.count()).thenReturn(3L);
        when(bookRepository.countByAvailableTrue()).thenReturn(2L);
        when(bookRepository.countByAvailableFalse()).thenReturn(1L);
        when(bookRepository.findCategoryStatistics()).thenReturn(Arrays.asList(
            new Object[]{"Programming", 2L},
            new Object[]{"Database", 1L}
        ));
        when(bookRepository.findMostPopularBooks()).thenReturn(Arrays.asList(book3, book1, book2));
        when(bookRepository.findRecentBooks(any(LocalDateTime.class))).thenReturn(Arrays.asList(book2, book1));
        when(bookRepository.findAll()).thenReturn(books);

        // When
        BookStatisticsDTO result = statisticsService.getCompleteStatistics();

        // Then
        assertNotNull(result);
        assertEquals(3, result.getTotalBooks());
        assertEquals(2, result.getAvailableBooks());
        assertEquals(1, result.getBorrowedBooks());
        assertNotNull(result.getCategoriesStats());
        assertNotNull(result.getPopularBooks());
        assertNotNull(result.getRecentBooks());
        assertNotNull(result.getInventoryReport());
    }

    @Test
    void getPopularBooks_ShouldReturnLimitedList() {
        // Given
        when(bookRepository.findMostPopularBooks()).thenReturn(Arrays.asList(book3, book1, book2));

        // When
        List<PopularBookDTO> result = statisticsService.getPopularBooks(2);

        // Then
        assertEquals(2, result.size());
        assertEquals("Database Design", result.get(0).getTitle());
        assertEquals(8, result.get(0).getBorrowCount());
        assertEquals("Java Programming", result.get(1).getTitle());
        assertEquals(5, result.get(1).getBorrowCount());
    }

    @Test
    void getCategoryStatistics_ShouldCalculatePercentages() {
        // Given
        when(bookRepository.count()).thenReturn(3L);
        when(bookRepository.findCategoryStatistics()).thenReturn(Arrays.asList(
            new Object[]{"Programming", 2L},
            new Object[]{"Database", 1L}
        ));

        // When
        List<CategoryStatsDTO> result = statisticsService.getCategoryStatistics();

        // Then
        assertEquals(2, result.size());
        assertEquals("Programming", result.get(0).getCategory());
        assertEquals(2, result.get(0).getCount());
        assertEquals(66.67, result.get(0).getPercentage(), 0.01);
        assertEquals("Database", result.get(1).getCategory());
        assertEquals(1, result.get(1).getCount());
        assertEquals(33.33, result.get(1).getPercentage(), 0.01);
    }

    @Test
    void getCategoryStatistics_WithNullCategory_ShouldHandleGracefully() {
        // Given
        when(bookRepository.count()).thenReturn(2L);
        when(bookRepository.findCategoryStatistics()).thenReturn(Arrays.asList(
            new Object[]{null, 1L},
            new Object[]{"Programming", 1L}
        ));

        // When
        List<CategoryStatsDTO> result = statisticsService.getCategoryStatistics();

        // Then
        assertEquals(2, result.size());
        assertEquals("Non catégorisé", result.get(0).getCategory());
        assertEquals("Programming", result.get(1).getCategory());
    }

    @Test
    void getRecentBooks_ShouldReturnBooksFromSpecifiedDays() {
        // Given
        when(bookRepository.findRecentBooks(any(LocalDateTime.class))).thenReturn(Arrays.asList(book2, book1));

        // When
        List<RecentBookDTO> result = statisticsService.getRecentBooks(30);

        // Then
        assertEquals(2, result.size());
        assertEquals("Spring Boot Guide", result.get(0).getTitle());
        assertEquals("Java Programming", result.get(1).getTitle());
        verify(bookRepository).findRecentBooks(any(LocalDateTime.class));
    }

    @Test
    void getInventoryReport_ShouldReturnAllBooksWithStatus() {
        // Given
        List<Book> books = Arrays.asList(book1, book2, book3);
        when(bookRepository.findAll()).thenReturn(books);

        // When
        List<InventoryItemDTO> result = statisticsService.getInventoryReport();

        // Then
        assertEquals(3, result.size());
        assertEquals("Java Programming", result.get(0).getTitle());
        assertEquals("Disponible", result.get(0).getStatus());
        assertEquals("Spring Boot Guide", result.get(1).getTitle());
        assertEquals("Emprunté", result.get(1).getStatus());
    }
}