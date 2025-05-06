package com.justmevp.bookservice.command.event;

import java.util.Optional;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.justmevp.bookservice.command.data.Book;
import com.justmevp.bookservice.command.data.BookRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BookEventHandle {

    private final BookRepository bookRepository;

    @EventHandler
    public void on(BookCreatedEvent event) {
        Book book = new Book();
        BeanUtils.copyProperties(event, book);
        bookRepository.save(book);
    }

    @EventHandler
    public void on(BookUpdatedEvent event) {
        Optional<Book> optionalBook = bookRepository.findById(event.getId());
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            book.setAuthor(event.getAuthor());
            book.setName(event.getName());
            book.setIsReady(event.getIsReady());
            bookRepository.save(book);
        }
    }

    @EventHandler
    public void on(BookDeletedEvent event) {
        Optional<Book> optionalBook = bookRepository.findById(event.getId());
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            bookRepository.delete(book);
        }
    }
}
