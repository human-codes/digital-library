package uz.tridev.digital_library.service.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;
import uz.tridev.digital_library.dto.BookDTO;
import uz.tridev.digital_library.entity.User;

import java.io.IOException;

public interface BooksServiceI {
    ResponseEntity<?> addBook(BookDTO bookDTO, MultipartFile file) ;

    ResponseEntity<?> borrowBook(int id, UserDetails user);

    ResponseEntity<?> returnBorrowedBook(int id, UserDetails user);

    ResponseEntity<?> getUsersBorrowedBooks(UserDetails user);
}
