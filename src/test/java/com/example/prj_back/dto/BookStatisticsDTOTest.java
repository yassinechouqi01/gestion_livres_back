package com.example.prj_back.dto;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class BookStatisticsDTOTest {

    @Test
    void constructor_ShouldInitializeAllFields() {
        // Given
        var categoryStats = Arrays.asList(new CategoryStatsDTO("Programming", 5, 50.0));
        var popularBooks = Arrays.asList(new PopularBookDTO(1L, "Book", "Author", 10));
        var recentBooks = Collections.<RecentBookDTO>emptyList();
        var inventory = Collections.<InventoryItemDTO>emptyList();

        // When
        BookStatisticsDTO dto = new BookStatisticsDTO(10, 8, 2, categoryStats, popularBooks, recentBooks, inventory);

        // Then
        assertEquals(10, dto.getTotalBooks());
        assertEquals(8, dto.getAvailableBooks());
        assertEquals(2, dto.getBorrowedBooks());
        assertEquals(categoryStats, dto.getCategoriesStats());
        assertEquals(popularBooks, dto.getPopularBooks());
        assertEquals(recentBooks, dto.getRecentBooks());
        assertEquals(inventory, dto.getInventoryReport());
    }

    @Test
    void defaultConstructor_ShouldCreateEmptyObject() {
        // When
        BookStatisticsDTO dto = new BookStatisticsDTO();

        // Then
        assertEquals(0, dto.getTotalBooks());
        assertEquals(0, dto.getAvailableBooks());
        assertEquals(0, dto.getBorrowedBooks());
        assertNull(dto.getCategoriesStats());
        assertNull(dto.getPopularBooks());
        assertNull(dto.getRecentBooks());
        assertNull(dto.getInventoryReport());
    }

    @Test
    void settersAndGetters_ShouldWorkCorrectly() {
        // Given
        BookStatisticsDTO dto = new BookStatisticsDTO();
        var categoryStats = Arrays.asList(new CategoryStatsDTO("Test", 1, 100.0));

        // When
        dto.setTotalBooks(15);
        dto.setAvailableBooks(12);
        dto.setBorrowedBooks(3);
        dto.setCategoriesStats(categoryStats);

        // Then
        assertEquals(15, dto.getTotalBooks());
        assertEquals(12, dto.getAvailableBooks());
        assertEquals(3, dto.getBorrowedBooks());
        assertEquals(categoryStats, dto.getCategoriesStats());
    }
}