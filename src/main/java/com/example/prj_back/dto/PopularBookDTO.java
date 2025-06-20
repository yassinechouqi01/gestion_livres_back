package com.example.prj_back.dto;

public class PopularBookDTO {
    private Long id;
    private String title;
    private String author;
    private int borrowCount;

    public PopularBookDTO() {}

    public PopularBookDTO(Long id, String title, String author, int borrowCount) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.borrowCount = borrowCount;
    }

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public int getBorrowCount() { return borrowCount; }
    public void setBorrowCount(int borrowCount) { this.borrowCount = borrowCount; }
}