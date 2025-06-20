package com.example.prj_back.controller;

import com.example.prj_back.dto.*;
import com.example.prj_back.service.StatisticsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StatisticsController.class)
class StatisticsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StatisticsService statisticsService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getStatistics_ShouldReturnCompleteStatistics() throws Exception {
        // Given
        BookStatisticsDTO stats = new BookStatisticsDTO(10, 8, 2, 
            Collections.emptyList(), Collections.emptyList(), 
            Collections.emptyList(), Collections.emptyList());
        when(statisticsService.getCompleteStatistics()).thenReturn(stats);

        // When & Then
        mockMvc.perform(get("/api/statistics"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.totalBooks").value(10))
                .andExpect(jsonPath("$.availableBooks").value(8))
                .andExpect(jsonPath("$.borrowedBooks").value(2));
    }

    @Test
    void getStatistics_WhenServiceThrowsException_ShouldReturn500() throws Exception {
        // Given
        when(statisticsService.getCompleteStatistics()).thenThrow(new RuntimeException("Database error"));

        // When & Then
        mockMvc.perform(get("/api/statistics"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void getPopularBooks_ShouldReturnPopularBooksList() throws Exception {
        // Given
        List<PopularBookDTO> popularBooks = Arrays.asList(
            new PopularBookDTO(1L, "Book 1", "Author 1", 10),
            new PopularBookDTO(2L, "Book 2", "Author 2", 8)
        );
        when(statisticsService.getPopularBooks(anyInt())).thenReturn(popularBooks);

        // When & Then
        mockMvc.perform(get("/api/statistics/popular?limit=5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].title").value("Book 1"))
                .andExpect(jsonPath("$[0].borrowCount").value(10))
                .andExpect(jsonPath("$[1].title").value("Book 2"))
                .andExpect(jsonPath("$[1].borrowCount").value(8));
    }

    @Test
    void getPopularBooks_WithDefaultLimit_ShouldUseDefault() throws Exception {
        // Given
        when(statisticsService.getPopularBooks(10)).thenReturn(Collections.emptyList());

        // When & Then
        mockMvc.perform(get("/api/statistics/popular"))
                .andExpect(status().isOk());
    }

    @Test
    void getCategoryStats_ShouldReturnCategoryStatistics() throws Exception {
        // Given
        List<CategoryStatsDTO> categoryStats = Arrays.asList(
            new CategoryStatsDTO("Programming", 5, 50.0),
            new CategoryStatsDTO("Database", 3, 30.0)
        );
        when(statisticsService.getCategoryStatistics()).thenReturn(categoryStats);

        // When & Then
        mockMvc.perform(get("/api/statistics/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].category").value("Programming"))
                .andExpect(jsonPath("$[0].count").value(5))
                .andExpect(jsonPath("$[0].percentage").value(50.0));
    }

    @Test
    void getRecentBooks_ShouldReturnRecentBooksList() throws Exception {
        // Given
        List<RecentBookDTO> recentBooks = Arrays.asList(
            new RecentBookDTO(1L, "Recent Book 1", "Author 1", LocalDateTime.now()),
            new RecentBookDTO(2L, "Recent Book 2", "Author 2", LocalDateTime.now().minusDays(1))
        );
        when(statisticsService.getRecentBooks(anyInt())).thenReturn(recentBooks);

        // When & Then
        mockMvc.perform(get("/api/statistics/recent?days=7"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].title").value("Recent Book 1"))
                .andExpect(jsonPath("$[1].title").value("Recent Book 2"));
    }

    @Test
    void getInventoryReport_ShouldReturnInventoryList() throws Exception {
        // Given
        List<InventoryItemDTO> inventory = Arrays.asList(
            new InventoryItemDTO(1L, "Book 1", "Author 1", "123", "Disponible", "Bon", "Bibliothèque principale"),
            new InventoryItemDTO(2L, "Book 2", "Author 2", "456", "Emprunté", "Bon", "Bibliothèque principale")
        );
        when(statisticsService.getInventoryReport()).thenReturn(inventory);

        // When & Then
        mockMvc.perform(get("/api/statistics/inventory"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].title").value("Book 1"))
                .andExpect(jsonPath("$[0].status").value("Disponible"))
                .andExpect(jsonPath("$[1].status").value("Emprunté"));
    }
}