package com.github.nicolasholanda.spring_graphql.graphql;

import com.github.nicolasholanda.spring_graphql.model.Author;
import com.github.nicolasholanda.spring_graphql.model.dto.AddAuthorDTO;
import com.github.nicolasholanda.spring_graphql.model.dto.EditAuthorDTO;
import com.github.nicolasholanda.spring_graphql.repository.AuthorRepository;
import jakarta.transaction.Transactional;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class AuthorQueryResolver {

    private final AuthorRepository authorRepository;

    public AuthorQueryResolver(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @QueryMapping
    public List<Author> authors() {
        return authorRepository.findAll();
    }

    @QueryMapping
    public Author authorById(@Argument("id") Long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Couldn't find book with id " + id));
    }

    @MutationMapping
    public Author addAuthor(@Argument AddAuthorDTO addAuthorDTO) {
        Author author = new Author();
        author.setName(addAuthorDTO.getName());
        author.setBiography(addAuthorDTO.getBiography());
        return authorRepository.save(author);
    }

    @Transactional
    @MutationMapping
    public Author editAuthor(@Argument("id") Long id,
                             @Argument EditAuthorDTO editAuthorDTO) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Couldn't find author with id " + id));

        author.setName(editAuthorDTO.getName());
        author.setBiography(editAuthorDTO.getBiography());

        return author;
    }
}
