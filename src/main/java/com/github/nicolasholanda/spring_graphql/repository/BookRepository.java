package com.github.nicolasholanda.spring_graphql.repository;

import com.github.nicolasholanda.spring_graphql.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {}