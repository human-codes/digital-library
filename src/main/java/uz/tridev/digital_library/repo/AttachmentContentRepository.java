package uz.tridev.digital_library.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.tridev.digital_library.entity.AttachmentContent;

public interface AttachmentContentRepository extends JpaRepository<AttachmentContent, Integer> {
}