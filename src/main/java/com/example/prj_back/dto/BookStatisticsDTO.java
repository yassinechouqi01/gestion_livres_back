package com.example.prj_back.dto;

import java.util.List;

public class BookStatisticsDTO {
    private int totalBooks;
    private int availableBooks;
    private int borrowedBooks;
    private List<CategoryStatsDTO> categoriesStats;
    private List<PopularBookDTO> popularBooks;
    private List<RecentBookDTO> recentBooks;
    private List<InventoryItemDTO> inventoryReport;

    // Constructeurs
    public BookStatisticsDTO() {}

    public BookStatisticsDTO(int totalBooks, int availableBooks, int borrowedBooks,
                           List<CategoryStatsDTO> categoriesStats, List<PopularBookDTO> popularBooks,
                           List<RecentBookDTO> recentBooks, List<InventoryItemDTO> inventoryReport) {
        this.totalBooks = totalBooks;
        this.availableBooks = availableBooks;
        this.borrowedBooks = borrowedBooks;
        this.categoriesStats = categoriesStats;
        this.popularBooks = popularBooks;
        this.recentBooks = recentBooks;
        this.inventoryReport = inventoryReport;
    }

    // Getters et Setters
    public int getTotalBooks() { return totalBooks; }
    public void setTotalBooks(int totalBooks) { this.totalBooks = totalBooks; }

    public int getAvailableBooks() { return availableBooks; }
    public void setAvailableBooks(int availableBooks) { this.availableBooks = availableBooks; }

    public int getBorrowedBooks() { return borrowedBooks; }
    public void setBorrowedBooks(int borrowedBooks) { this.borrowedBooks = borrowedBooks; }

    public List<CategoryStatsDTO> getCategoriesStats() { return categoriesStats; }
    public void setCategoriesStats(List<CategoryStatsDTO> categoriesStats) { this.categoriesStats = categoriesStats; }

    public List<PopularBookDTO> getPopularBooks() { return popularBooks; }
    public void setPopularBooks(List<PopularBookDTO> popularBooks) { this.popularBooks = popularBooks; }

    public List<RecentBookDTO> getRecentBooks() { return recentBooks; }
    public void setRecentBooks(List<RecentBookDTO> recentBooks) { this.recentBooks = recentBooks; }

    public List<InventoryItemDTO> getInventoryReport() { return inventoryReport; }
    public void setInventoryReport(List<InventoryItemDTO> inventoryReport) { this.inventoryReport = inventoryReport; }
}