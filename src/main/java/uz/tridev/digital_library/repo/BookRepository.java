package uz.tridev.digital_library.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.tridev.digital_library.entity.Book;

public interface BookRepository extends JpaRepository<Book, Integer> {
}