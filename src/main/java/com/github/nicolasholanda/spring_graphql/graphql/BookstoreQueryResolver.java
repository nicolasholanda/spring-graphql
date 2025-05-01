package com.github.nicolasholanda.spring_graphql.graphql;

import com.github.nicolasholanda.spring_graphql.model.Author;
import com.github.nicolasholanda.spring_graphql.model.Book;
import com.github.nicolasholanda.spring_graphql.repository.AuthorRepository;
import com.github.nicolasholanda.spring_graphql.repository.BookRepository;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class BookstoreQueryResolver {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookstoreQueryResolver(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @QueryMapping
    public List<Book> books() {
        return bookRepository.findAll();
    }

    @QueryMapping
    public List<Author> authors() {
        return authorRepository.findAll();
    }
}