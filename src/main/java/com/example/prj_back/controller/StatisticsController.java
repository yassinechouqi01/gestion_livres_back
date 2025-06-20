package com.example.prj_back.controller;

import com.example.prj_back.dto.*;
import com.example.prj_back.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/statistics")
@CrossOrigin(origins = "http://localhost:4200")
public class StatisticsController {

    @Autowired
    private StatisticsService statisticsService;

    @GetMapping
    public ResponseEntity<BookStatisticsDTO> getStatistics() {
        try {
            BookStatisticsDTO statistics = statisticsService.getCompleteStatistics();
            return ResponseEntity.ok(statistics);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/popular")
    public ResponseEntity<List<PopularBookDTO>> getPopularBooks(
            @RequestParam(defaultValue = "10") int limit) {
        try {
            List<PopularBookDTO> popularBooks = statisticsService.getPopularBooks(limit);
            return ResponseEntity.ok(popularBooks);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryStatsDTO>> getCategoryStats() {
        try {
            List<CategoryStatsDTO> categoryStats = statisticsService.getCategoryStatistics();
            return ResponseEntity.ok(categoryStats);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/recent")
    public ResponseEntity<List<RecentBookDTO>> getRecentBooks(
            @RequestParam(defaultValue = "30") int days) {
        try {
            List<RecentBookDTO> recentBooks = statisticsService.getRecentBooks(days);
            return ResponseEntity.ok(recentBooks);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/inventory")
    public ResponseEntity<List<InventoryItemDTO>> getInventoryReport() {
        try {
            List<InventoryItemDTO> inventory = statisticsService.getInventoryReport();
            return ResponseEntity.ok(inventory);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}