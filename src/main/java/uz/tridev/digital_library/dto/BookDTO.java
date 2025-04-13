package uz.tridev.digital_library.dto;

import lombok.Value;

@Value
public class BookDTO {
     String title;
     String author;
     String description;
     String language;
     String genre;
     int pages;
}
