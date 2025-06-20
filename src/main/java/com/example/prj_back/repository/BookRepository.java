package com.example.prj_back.repository;

import com.example.prj_back.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    
    // Méthodes existantes
    List<Book> findByAvailable(boolean available);
    List<Book> findByAuthorContainingIgnoreCase(String author);
    
    // Nouvelles méthodes pour les statistiques
    
    // Statistiques de base
    long countByAvailableTrue();
    long countByAvailableFalse();
    
    // Livres populaires (triés par nombre d'emprunts)
    @Query("SELECT b FROM Book b ORDER BY b.borrowCount DESC")
    List<Book> findMostPopularBooks();
    
    // Statistiques par catégorie
    @Query("SELECT b.category, COUNT(b) FROM Book b GROUP BY b.category")
    List<Object[]> findCategoryStatistics();
    
    // Livres récents (ajoutés depuis une date donnée)
    @Query("SELECT b FROM Book b WHERE b.dateAdded >= :since ORDER BY b.dateAdded DESC")
    List<Book> findRecentBooks(@Param("since") LocalDateTime since);
    
    // Livres disponibles (alias pour compatibilité)
    default List<Book> findByAvailableTrue() {
        return findByAvailable(true);
    }
}