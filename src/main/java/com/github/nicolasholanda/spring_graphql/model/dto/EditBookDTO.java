package com.github.nicolasholanda.spring_graphql.model.dto;

import lombok.Data;

@Data
public class EditBookDTO {
    private String title;
    private String isbn;
    private int publishedYear;
}
