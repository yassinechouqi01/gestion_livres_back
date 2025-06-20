package com.example.prj_back.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class BookTest {

    @Test
    void constructor_WithFourParameters_ShouldInitializeCorrectly() {
        // When
        Book book = new Book("Java Programming", "John Doe", "123456789", 2023);

        // Then
        assertEquals("Java Programming", book.getTitle());
        assertEquals("John Doe", book.getAuthor());
        assertEquals("123456789", book.getIsbn());
        assertEquals(2023, book.getPublicationYear());
        assertTrue(book.isAvailable());
        assertEquals("Bon", book.getCondition());
        assertEquals("Bibliothèque principale", book.getLocation());
        assertEquals(0, book.getBorrowCount());
        assertNotNull(book.getDateAdded());
    }

    @Test
    void constructor_WithSixParameters_ShouldInitializeCorrectly() {
        // When
        Book book = new Book(1L, "Spring Boot", "Jane Smith", "987654321", 2024, false);

        // Then
        assertEquals(1L, book.getId());
        assertEquals("Spring Boot", book.getTitle());
        assertEquals("Jane Smith", book.getAuthor());
        assertEquals("987654321", book.getIsbn());
        assertEquals(2024, book.getPublicationYear());
        assertFalse(book.isAvailable());
        assertEquals("Bon", book.getCondition());
        assertEquals("Bibliothèque principale", book.getLocation());
        assertEquals(0, book.getBorrowCount());
        assertNotNull(book.getDateAdded());
    }

    @Test
    void constructor_WithAllParameters_ShouldInitializeCorrectly() {
        // Given
        LocalDateTime dateAdded = LocalDateTime.now().minusDays(10);

        // When
        Book book = new Book(2L, "Database Design", "Bob Wilson", "456789123", 
                           2022, true, "Database", "Excellent", "Section A", 5, dateAdded);

        // Then
        assertEquals(2L, book.getId());
        assertEquals("Database Design", book.getTitle());
        assertEquals("Bob Wilson", book.getAuthor());
        assertEquals("456789123", book.getIsbn());
        assertEquals(2022, book.getPublicationYear());
        assertTrue(book.isAvailable());
        assertEquals("Database", book.getCategory());
        assertEquals("Excellent", book.getCondition());
        assertEquals("Section A", book.getLocation());
        assertEquals(5, book.getBorrowCount());
        assertEquals(dateAdded, book.getDateAdded());
    }

    @Test
    void defaultConstructor_ShouldCreateEmptyBook() {
        // When
        Book book = new Book();

        // Then
        assertNull(book.getId());
        assertNull(book.getTitle());
        assertNull(book.getAuthor());
        assertNull(book.getIsbn());
        assertEquals(0, book.getPublicationYear());
        assertTrue(book.isAvailable()); // valeur par défaut
        assertNull(book.getCategory());
        assertEquals("Bon", book.getCondition()); // valeur par défaut
        assertEquals("Bibliothèque principale", book.getLocation()); // valeur par défaut
        assertEquals(0, book.getBorrowCount()); // valeur par défaut
        assertNull(book.getDateAdded());
    }

    @Test
    void settersAndGetters_ShouldWorkCorrectly() {
        // Given
        Book book = new Book();
        LocalDateTime now = LocalDateTime.now();

        // When
        book.setId(10L);
        book.setTitle("Test Book");
        book.setAuthor("Test Author");
        book.setIsbn("1234567890");
        book.setPublicationYear(2025);
        book.setAvailable(false);
        book.setCategory("Test Category");
        book.setCondition("Usé");
        book.setLocation("Section B");
        book.setBorrowCount(3);
        book.setDateAdded(now);

        // Then
        assertEquals(10L, book.getId());
        assertEquals("Test Book", book.getTitle());
        assertEquals("Test Author", book.getAuthor());
        assertEquals("1234567890", book.getIsbn());
        assertEquals(2025, book.getPublicationYear());
        assertFalse(book.isAvailable());
        assertEquals("Test Category", book.getCategory());
        assertEquals("Usé", book.getCondition());
        assertEquals("Section B", book.getLocation());
        assertEquals(3, book.getBorrowCount());
        assertEquals(now, book.getDateAdded());
    }
}