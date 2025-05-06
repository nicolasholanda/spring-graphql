package com.github.nicolasholanda.spring_graphql.graphql;

import com.github.nicolasholanda.spring_graphql.model.Author;
import com.github.nicolasholanda.spring_graphql.model.Book;
import com.github.nicolasholanda.spring_graphql.repository.AuthorRepository;
import com.github.nicolasholanda.spring_graphql.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.graphql.test.tester.GraphQlTester;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;
import java.util.Optional;


@GraphQlTest(BookQueryResolver.class)
class BookQueryResolverTest {

    @Autowired
    private GraphQlTester graphQlTester;

    @MockitoBean
    private AuthorRepository authorRepository;

    @MockitoBean
    private BookRepository bookRepository;

    @Test
    void shouldReturnListOfBooks() {
        Author author = new Author();
        author.setId(1L);
        author.setName("Orwell");

        Book book = new Book();
        book.setId(1L);
        book.setTitle("1984");
        book.setAuthor(author);
        book.setIsbn("123456");
        book.setPublishedYear(1949);

        Mockito.when(bookRepository.findAll()).thenReturn(List.of(book));

        graphQlTester.document("""
                        query {
                            books {
                                id
                                title
                                author {
                                    name
                                }
                            }
                        }
                        """)
                .execute()
                .path("books[0].title").entity(String.class).isEqualTo("1984")
                .path("books[0].author.name").entity(String.class).isEqualTo("Orwell");
    }

    @Test
    void shouldReturnBookById() {
        Author author = new Author();
        author.setId(2L);
        author.setName("J.K. Rowling");

        Book book = new Book();
        book.setId(2L);
        book.setTitle("Harry Potter");
        book.setAuthor(author);
        book.setIsbn("654321");
        book.setPublishedYear(1997);

        Mockito.when(bookRepository.findById(2L)).thenReturn(Optional.of(book));

        graphQlTester.document("""
                        query {
                            bookById(id: 2) {
                                id
                                title
                                author {
                                    name
                                }
                            }
                        }
                        """)
                .execute()
                .path("bookById.title").entity(String.class).isEqualTo("Harry Potter")
                .path("bookById.author.name").entity(String.class).isEqualTo("J.K. Rowling");
    }

}
