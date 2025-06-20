package com.example.prj_back.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String title;
    private String author;
    private String isbn;
    private int publicationYear;
    private boolean available = true;
    
    // Nouveaux champs pour les statistiques
    private String category;
    
    @Column(name = "condition_status")
    private String condition = "Bon";
    
    private String location = "Bibliothèque principale";
    
    @Column(name = "borrow_count")
    private Integer borrowCount = 0;
    
    @Column(name = "date_added")
    private LocalDateTime dateAdded;
    
    @PrePersist
    protected void onCreate() {
        if (dateAdded == null) {
            dateAdded = LocalDateTime.now();
        }
    }
    
    // Constructeur personnalisé pour compatibilité avec l'ancien code
    public Book(String title, String author, String isbn, int publicationYear) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publicationYear = publicationYear;
        this.available = true;
        this.condition = "Bon";
        this.location = "Bibliothèque principale";
        this.borrowCount = 0;
        this.dateAdded = LocalDateTime.now();
    }
    
    // Constructeur utilisé par les tests (6 paramètres)
    public Book(Long id, String title, String author, String isbn, int publicationYear, boolean available) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publicationYear = publicationYear;
        this.available = available;
        this.category = null;
        this.condition = "Bon";
        this.location = "Bibliothèque principale";
        this.borrowCount = 0;
        this.dateAdded = LocalDateTime.now();
    }
    
    // Constructeur avec tous les paramètres (remplace @AllArgsConstructor)
    public Book(Long id, String title, String author, String isbn, int publicationYear, 
                boolean available, String category, String condition, String location, 
                Integer borrowCount, LocalDateTime dateAdded) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publicationYear = publicationYear;
        this.available = available;
        this.category = category;
        this.condition = condition;
        this.location = location;
        this.borrowCount = borrowCount;
        this.dateAdded = dateAdded;
    }
}