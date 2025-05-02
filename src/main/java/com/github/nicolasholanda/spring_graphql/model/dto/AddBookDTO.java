package com.github.nicolasholanda.spring_graphql.model.dto;

import lombok.Data;

@Data
public class AddBookDTO {
    private String title;
    private String isbn;
    private int publishedYear;
    private Long authorId;
}
