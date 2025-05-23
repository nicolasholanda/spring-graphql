package com.github.nicolasholanda.spring_graphql.graphql;

import com.github.nicolasholanda.spring_graphql.model.Author;
import com.github.nicolasholanda.spring_graphql.model.Book;
import com.github.nicolasholanda.spring_graphql.model.dto.AddBookDTO;
import com.github.nicolasholanda.spring_graphql.model.dto.EditBookDTO;
import com.github.nicolasholanda.spring_graphql.publisher.BookPublisher;
import com.github.nicolasholanda.spring_graphql.repository.AuthorRepository;
import com.github.nicolasholanda.spring_graphql.repository.BookRepository;
import jakarta.transaction.Transactional;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SubscriptionMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;

import java.util.List;

@Controller
public class BookQueryResolver {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final BookPublisher bookPublisher;

    public BookQueryResolver(BookPublisher bookPublisher,
                             BookRepository bookRepository,
                             AuthorRepository authorRepository) {
        this.bookPublisher = bookPublisher;
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
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

    @MutationMapping
    public Book addBook(@Argument AddBookDTO addBookDTO) {
        Author author = authorRepository.findById(addBookDTO.getAuthorId())
                .orElseThrow(() -> new RuntimeException("Author not found: " + addBookDTO.getAuthorId()));

        Book book = new Book();
        book.setIsbn(addBookDTO.getIsbn());
        book.setTitle(addBookDTO.getTitle());
        book.setPublishedYear(addBookDTO.getPublishedYear());
        book.setAuthor(author);

        bookPublisher.publish(book);

        return bookRepository.save(book);
    }

    @Transactional
    @MutationMapping
    public Book editBook(@Argument("id") Long id,
                         @Argument EditBookDTO editBookDTO) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found: " + id));

        book.setTitle(editBookDTO.getTitle());
        book.setIsbn(editBookDTO.getIsbn());
        book.setPublishedYear(editBookDTO.getPublishedYear());

        return book;
    }

    @SubscriptionMapping
    public Flux<Book> bookAdded() {
        return bookPublisher.getPublisher();
    }
}
