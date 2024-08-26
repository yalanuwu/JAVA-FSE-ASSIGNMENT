package com.example.bookstoreapi.controller;

import com.example.bookstoreapi.model.Book;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private List<Book> books = new ArrayList<>();

    // GET method to retrieve all books with custom status code and headers
    @GetMapping
    @ResponseStatus(HttpStatus.OK) // Explicitly setting the status code to 200 OK
    public ResponseEntity<List<Book>> getAllBooks() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", String.valueOf(books.size())); // Custom header example
        return new ResponseEntity<>(books, headers, HttpStatus.OK);
    }

    // POST method to add a new book with custom status code and headers
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // Explicitly setting the status code to 201 Created
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        books.add(book);

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Book-Added", "true"); // Custom header example

        return new ResponseEntity<>(book, headers, HttpStatus.CREATED);
    }

    // GET method to retrieve a book by ID with custom headers
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        for (Book book : books) {
            if (book.getId().equals(id)) {
                HttpHeaders headers = new HttpHeaders();
                headers.add("X-Book-Found", "true"); // Custom header example

                return new ResponseEntity<>(book, headers, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // GET method to filter books by title and/or author with custom headers
    @GetMapping("/search")
    public ResponseEntity<List<Book>> searchBooks(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author) {

        List<Book> filteredBooks = new ArrayList<>();
        HttpHeaders headers = new HttpHeaders();

        for (Book book : books) {
            boolean matchesTitle = (title == null || book.getTitle().equalsIgnoreCase(title));
            boolean matchesAuthor = (author == null || book.getAuthor().equalsIgnoreCase(author));

            if (matchesTitle && matchesAuthor) {
                filteredBooks.add(book);
            }
        }

        headers.add("X-Filtered-Count", String.valueOf(filteredBooks.size())); // Custom header example

        return new ResponseEntity<>(filteredBooks, headers, HttpStatus.OK);
    }

    // PUT method to update a book by id with custom status code and headers
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book updatedBook) {
        for (Book book : books) {
            if (book.getId().equals(id)) {
                book.setTitle(updatedBook.getTitle());
                book.setAuthor(updatedBook.getAuthor());
                book.setPrice(updatedBook.getPrice());
                book.setIsbn(updatedBook.getIsbn());

                HttpHeaders headers = new HttpHeaders();
                headers.add("X-Book-Updated", "true"); // Custom header example

                return new ResponseEntity<>(book, headers, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // DELETE method to remove a book by id with custom status code and headers
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        boolean removed = books.removeIf(book -> book.getId().equals(id));
        if (removed) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("X-Book-Deleted", "true"); // Custom header example

            return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
