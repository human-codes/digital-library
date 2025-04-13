package uz.tridev.digital_library.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.tridev.digital_library.entity.Book;

import java.util.List;
import java.util.Set;

public interface BookRepository extends JpaRepository<Book, Integer> {
    List<Book> findByIdIn(Set<Integer> borrowedBookIds);
}