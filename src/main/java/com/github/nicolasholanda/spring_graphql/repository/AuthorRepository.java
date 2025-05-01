package com.github.nicolasholanda.spring_graphql.repository;

import com.github.nicolasholanda.spring_graphql.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {}