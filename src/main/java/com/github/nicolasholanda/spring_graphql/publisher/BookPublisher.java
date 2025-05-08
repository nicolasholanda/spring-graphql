package com.github.nicolasholanda.spring_graphql.publisher;

import com.github.nicolasholanda.spring_graphql.model.Book;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Component
public class BookPublisher {

    private final Sinks.Many<Book> sink = Sinks.many().multicast().onBackpressureBuffer();

    public void publish(Book book) {
        sink.tryEmitNext(book);
    }

    public Flux<Book> getPublisher() {
        return sink.asFlux();
    }
}
