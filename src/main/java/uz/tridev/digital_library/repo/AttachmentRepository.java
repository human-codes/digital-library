package uz.tridev.digital_library.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.tridev.digital_library.entity.Attachment;

public interface AttachmentRepository extends JpaRepository<Attachment, Integer> {
}