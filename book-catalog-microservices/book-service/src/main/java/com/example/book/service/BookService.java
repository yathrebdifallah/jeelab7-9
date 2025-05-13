// book-service/src/main/java/com/example/book/service/BookService.java
package com.example.book.service;

import com.example.book.client.UserClient;
import com.example.book.dto.BookEvent;
import com.example.book.kafka.BookEventProducer;
import com.example.book.model.Book;
import com.example.book.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookEventProducer eventProducer;

    @Autowired
    private UserClient userClient;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    public List<Book> getBooksByUserId(Integer userId) {
        // Verify that the user exists
        try {
            userClient.getUserById(userId);
            return bookRepository.findByUserId(userId);
        } catch (Exception e) {
            throw new RuntimeException("User not found or user service unavailable");
        }
    }

    public Book createBook(Book book) {
        // Verify that the user exists if userId is provided
        if (book.getUserId() != null) {
            try {
                userClient.getUserById(book.getUserId());
            } catch (Exception e) {
                throw new RuntimeException("User not found or user service unavailable");
            }
        }

        Book savedBook = bookRepository.save(book);
        eventProducer.sendBookCreatedEvent(savedBook);
        return savedBook;
    }

    public Book updateBook(Long id, Book bookDetails) {
        return bookRepository.findById(id)
                .map(existingBook -> {
                    existingBook.setTitle(bookDetails.getTitle());
                    existingBook.setAuthor(bookDetails.getAuthor());
                    existingBook.setIsbn(bookDetails.getIsbn());
                    existingBook.setPrice(bookDetails.getPrice());

                    Book updatedBook = bookRepository.save(existingBook);
                    eventProducer.sendBookUpdatedEvent(updatedBook);
                    return updatedBook;
                })
                .orElseThrow(() -> new RuntimeException("Book not found with id " + id));
    }

    public void deleteBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id " + id));

        bookRepository.delete(book);
        eventProducer.sendBookDeletedEvent(book);
    }
}