package com.example.prj_back.dto;

public class InventoryItemDTO {
    private Long id;
    private String title;
    private String author;
    private String isbn;
    private String status;
    private String condition;
    private String location;

    public InventoryItemDTO() {}

    public InventoryItemDTO(Long id, String title, String author, String isbn, 
                           String status, String condition, String location) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.status = status;
        this.condition = condition;
        this.location = location;
    }

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getCondition() { return condition; }
    public void setCondition(String condition) { this.condition = condition; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
}