package com.example.prj_back.service;

import com.example.prj_back.dto.*;
import com.example.prj_back.model.Book;
import com.example.prj_back.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StatisticsService {

    @Autowired
    private BookRepository bookRepository;

    public BookStatisticsDTO getCompleteStatistics() {
        int totalBooks = (int) bookRepository.count();
        int availableBooks = (int) bookRepository.countByAvailableTrue();
        int borrowedBooks = (int) bookRepository.countByAvailableFalse();
        
        List<CategoryStatsDTO> categoryStats = getCategoryStatistics();
        List<PopularBookDTO> popularBooks = getPopularBooks(10);
        List<RecentBookDTO> recentBooks = getRecentBooks(30);
        List<InventoryItemDTO> inventoryReport = getInventoryReport();
        
        return new BookStatisticsDTO(totalBooks, availableBooks, borrowedBooks,
                                   categoryStats, popularBooks, recentBooks, inventoryReport);
    }

    public List<PopularBookDTO> getPopularBooks(int limit) {
        return bookRepository.findMostPopularBooks()
                .stream()
                .limit(limit)
                .map(book -> new PopularBookDTO(
                    book.getId(),
                    book.getTitle(),
                    book.getAuthor(),
                    book.getBorrowCount() != null ? book.getBorrowCount() : 0
                ))
                .collect(Collectors.toList());
    }

    public List<CategoryStatsDTO> getCategoryStatistics() {
        List<Object[]> results = bookRepository.findCategoryStatistics();
        long totalBooks = bookRepository.count();
        
        return results.stream()
                .map(result -> {
                    String category = (String) result[0];
                    if (category == null) category = "Non catégorisé";
                    Long count = (Long) result[1];
                    double percentage = totalBooks > 0 ? (count.doubleValue() / totalBooks) * 100 : 0;
                    return new CategoryStatsDTO(category, count.intValue(), Math.round(percentage * 100.0) / 100.0);
                })
                .collect(Collectors.toList());
    }

    public List<RecentBookDTO> getRecentBooks(int days) {
        LocalDateTime since = LocalDateTime.now().minusDays(days);
        return bookRepository.findRecentBooks(since)
                .stream()
                .map(book -> new RecentBookDTO(
                    book.getId(),
                    book.getTitle(),
                    book.getAuthor(),
                    book.getDateAdded()
                ))
                .collect(Collectors.toList());
    }

    public List<InventoryItemDTO> getInventoryReport() {
        return bookRepository.findAll()
                .stream()
                .map(book -> new InventoryItemDTO(
                    book.getId(),
                    book.getTitle(),
                    book.getAuthor(),
                    book.getIsbn(),
                    book.isAvailable() ? "Disponible" : "Emprunté",
                    book.getCondition() != null ? book.getCondition() : "Bon",
                    book.getLocation() != null ? book.getLocation() : "Bibliothèque principale"
                ))
                .collect(Collectors.toList());
    }
}