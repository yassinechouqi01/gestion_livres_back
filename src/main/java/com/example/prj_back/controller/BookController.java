package com.example.prj_back.controller;

import com.example.prj_back.model.Book;
import com.example.prj_back.repository.BookRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookRepository bookRepository;

    // Injection par constructeur (bonne pratique)
    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @GetMapping("/available")
    public List<Book> getAvailableBooks() {
        return bookRepository.findByAvailable(true);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        return bookRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        if (book == null || book.getTitle() == null || book.getTitle().trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        Book savedBook = bookRepository.save(book);
        return ResponseEntity.ok(savedBook);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book bookDetails) {
        if (bookDetails == null) {
            return ResponseEntity.badRequest().build();
        }
        return bookRepository.findById(id)
                .map(existingBook -> {
                    if (bookDetails.getTitle() != null) existingBook.setTitle(bookDetails.getTitle());
                    if (bookDetails.getAuthor() != null) existingBook.setAuthor(bookDetails.getAuthor());
                    if (bookDetails.getIsbn() != null) existingBook.setIsbn(bookDetails.getIsbn());
                    existingBook.setPublicationYear(bookDetails.getPublicationYear());
                    existingBook.setAvailable(bookDetails.isAvailable());
                    if (bookDetails.getCategory() != null) existingBook.setCategory(bookDetails.getCategory());
                    if (bookDetails.getCondition() != null) existingBook.setCondition(bookDetails.getCondition());
                    if (bookDetails.getLocation() != null) existingBook.setLocation(bookDetails.getLocation());
                    return ResponseEntity.ok(bookRepository.save(existingBook));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Long id) {
        return bookRepository.findById(id)
                .map(book -> {
                    bookRepository.delete(book);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public List<Book> searchByAuthor(@RequestParam String author) {
        return bookRepository.findByAuthorContainingIgnoreCase(author);
    }
    
}