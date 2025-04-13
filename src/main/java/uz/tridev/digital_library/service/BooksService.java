package uz.tridev.digital_library.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.tridev.digital_library.dto.BookDTO;
import uz.tridev.digital_library.dto.UserDTO;
import uz.tridev.digital_library.entity.Attachment;
import uz.tridev.digital_library.entity.AttachmentContent;
import uz.tridev.digital_library.entity.Book;
import uz.tridev.digital_library.entity.User;
import uz.tridev.digital_library.repo.AttachmentContentRepository;
import uz.tridev.digital_library.repo.AttachmentRepository;
import uz.tridev.digital_library.repo.BookRepository;
import uz.tridev.digital_library.repo.UserRepository;
import uz.tridev.digital_library.service.impl.BooksServiceI;

import java.io.IOException;
import java.util.List;

@Service
public class BooksService implements BooksServiceI {
    private final AttachmentRepository attachmentRepository;
    private final AttachmentContentRepository attachmentContentRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public BooksService(AttachmentRepository attachmentRepository, AttachmentContentRepository attachmentContentRepository, BookRepository bookRepository, UserRepository userRepository) {
        this.attachmentRepository = attachmentRepository;
        this.attachmentContentRepository = attachmentContentRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<?> addBook(BookDTO bookDTO, MultipartFile file) {

        try {
            Attachment attachment = new Attachment();
            attachment.setFileName(file.getOriginalFilename());
            attachment.setFileType(file.getContentType());
            attachmentRepository.save(attachment);

            AttachmentContent attachmentContent = new AttachmentContent();
            attachmentContent.setContent(file.getBytes());
            attachmentContent.setAttachment(attachment);
            attachmentContentRepository.save(attachmentContent);

            Book book=new Book();
            book.setAuthor(bookDTO.getAuthor());
            book.setTitle(bookDTO.getTitle());
            book.setDescription(bookDTO.getDescription());
            book.setPages(bookDTO.getPages());
            book.setAttachment(attachment);
            book.setGenre(bookDTO.getGenre());
            book.setLanguage(bookDTO.getLanguage());
            book.setAvailable(false);
            bookRepository.save(book);

            return ResponseEntity.status(HttpStatus.CREATED).body(book);

        } catch (IOException e) {
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<?> borrowBook(int id, UserDetails user) {
        User user1 = userRepository.findByUsername(user.getUsername()).orElseThrow();

        Book book = bookRepository.findById(id).orElseThrow();
        book.setAvailable(true);
        book.setBorrower(user1);
        bookRepository.save(book);

        user1.getBorrowedBookIds().add(id);
        userRepository.save(user1);
        return ResponseEntity.ok().body(book);
    }

    @Override
    public ResponseEntity<?> returnBorrowedBook(int id, UserDetails user) {
        Book book = bookRepository.findById(id).orElseThrow();
        book.setAvailable(false);
        book.setBorrower(null);
        bookRepository.save(book);

        User user1 = userRepository.findByUsername(user.getUsername()).orElseThrow();
        user1.getBorrowedBookIds().remove(id);
        userRepository.save(user1);
        return ResponseEntity.ok().body(bookRepository.findById(id).orElseThrow());
    }

    @Override
    public ResponseEntity<?> getUsersBorrowedBooks(UserDetails user) {
        User user1 = userRepository.findByUsername(user.getUsername()).orElseThrow();
        List<Book> borrowedBooks = bookRepository.findByIdIn(user1.getBorrowedBookIds());
        if (borrowedBooks.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Qarzga olingan kitoblar mavjud emas");
        }
        return ResponseEntity.ok().body(borrowedBooks);
    }
}
