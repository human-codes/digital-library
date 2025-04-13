package uz.tridev.digital_library.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book extends BaseEntity {
    private String title;
    private String author;
    private String description;
    private String language;
    private String genre;
    private int pages;

    @ManyToOne(optional = false)
    private Attachment attachment;

    private boolean isAvailable;

    @ManyToOne
    private User borrower;

}
