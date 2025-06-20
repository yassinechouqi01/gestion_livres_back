package com.example.prj_back.dto;

import java.time.LocalDateTime;

public class RecentBookDTO {
    private Long id;
    private String title;
    private String author;
    private LocalDateTime dateAdded;

    public RecentBookDTO() {}

    public RecentBookDTO(Long id, String title, String author, LocalDateTime dateAdded) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.dateAdded = dateAdded;
    }

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public LocalDateTime getDateAdded() { return dateAdded; }
    public void setDateAdded(LocalDateTime dateAdded) { this.dateAdded = dateAdded; }
}