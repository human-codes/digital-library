package uz.tridev.digital_library.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.tridev.digital_library.dto.BookDTO;
import uz.tridev.digital_library.entity.Book;
import uz.tridev.digital_library.entity.User;
import uz.tridev.digital_library.repo.BookRepository;
import uz.tridev.digital_library.service.impl.BooksServiceI;
import org.springframework.http.MediaType;
import java.util.List;


@RestController
@RequestMapping("/api/books")
@SecurityRequirement(name = "Bearer Authentication")
public class BooksController {

    private final BookRepository bookRepository;
    private final BooksServiceI booksServiceI;

    public BooksController(BookRepository bookRepository, BooksServiceI booksServiceI) {
        this.bookRepository = bookRepository;
        this.booksServiceI = booksServiceI;
    }

    @GetMapping
    public ResponseEntity<?> getBooks() {
        List<Book> books = bookRepository.findAll();
        if (books.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping("/my-borrowed")
    public ResponseEntity<?> getMyBorrowedBooks(@AuthenticationPrincipal UserDetails user) {
        return booksServiceI.getUsersBorrowedBooks(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addBook(@RequestPart("book") BookDTO bookDTO,@RequestPart("file") MultipartFile file) {
        return booksServiceI.addBook(bookDTO,file);
    }

    @PutMapping("/{id}/borrow")
    public ResponseEntity<?> borrowBook(@PathVariable int id, @AuthenticationPrincipal UserDetails user) {
        return booksServiceI.borrowBook(id,user);
    }

    @PutMapping("/{id}/return")
    public ResponseEntity<?> returnBorrowedBook(@PathVariable int id, @AuthenticationPrincipal UserDetails user) {
        return booksServiceI.returnBorrowedBook(id,user);
    }



}
