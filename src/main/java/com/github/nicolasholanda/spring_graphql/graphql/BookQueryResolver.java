package com.github.nicolasholanda.spring_graphql.graphql;

import com.github.nicolasholanda.spring_graphql.model.Book;
import com.github.nicolasholanda.spring_graphql.repository.BookRepository;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class BookQueryResolver {

    private final BookRepository bookRepository;

    public BookQueryResolver(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @QueryMapping
    public List<Book> books() {
        return bookRepository.findAll();
    }

    @QueryMapping
    public Book bookById(@Argument("id") Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Couldn't find book with id " + id));
    }
}
