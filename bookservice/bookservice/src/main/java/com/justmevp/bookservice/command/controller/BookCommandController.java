package com.justmevp.bookservice.command.controller;

import java.util.UUID;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.justmevp.bookservice.command.command.CreateBookCommand;
import com.justmevp.bookservice.command.command.DeleteBookCommand;
import com.justmevp.bookservice.command.command.UpdateBookCommand;
import com.justmevp.bookservice.command.model.BookRequestModel;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
public class BookCommandController {
    private final CommandGateway commandGateway;

    @PostMapping()
    private String addBook(@RequestBody BookRequestModel model){
        CreateBookCommand createBookCommand = new CreateBookCommand(UUID.randomUUID().toString(), model.getName(), model.getAuthor(), true);
        return commandGateway.sendAndWait(createBookCommand);
    }

    @PutMapping("/{bookId}")
    private String updateBook(@RequestBody BookRequestModel model, @PathVariable String bookId){
        UpdateBookCommand updateBookCommand = new UpdateBookCommand(bookId, model.getName(), model.getAuthor(), model.getIsReady());
        return commandGateway.sendAndWait(updateBookCommand);
    }

    @DeleteMapping("/{bookId}")
    private String deleteBook( @PathVariable String bookId){
        DeleteBookCommand deleteBookCommand = new DeleteBookCommand(bookId);
        return commandGateway.sendAndWait(deleteBookCommand);
    } 
}
