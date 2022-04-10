package com.jpa2.demo.controller;

import com.jpa2.demo.dto.AuthorStatistic;
import com.jpa2.demo.entity.Author;
import com.jpa2.demo.entity.Book;
import com.jpa2.demo.model.EmptyJsonResponse;
import com.jpa2.demo.repository.jpa.AuthorRepository;
import com.jpa2.demo.repository.jpa.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class MainRestController {

    AuthorRepository authorRepository;
    BookRepository bookRepository;

    @Autowired
    public void setAuthorRepository(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Autowired
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @RequestMapping(path="/authors", method = RequestMethod.GET)
    public List<Author> showAuthors() {
        return authorRepository.findAll();
    }

    @RequestMapping(path="/authorStats", method = RequestMethod.GET)
    @ResponseBody
    public List<AuthorStatistic> showAuthorStats() {
        return authorRepository.findAuthorStatistic();
    }

    @RequestMapping(path="/authorByEmail", method = RequestMethod.GET)
    public ResponseEntity<Optional<Author>> fetchAuthorByEmail(@RequestParam("email") String email) {
        Optional<Author> author = authorRepository.findByEmailIgnoreCase(email);
        if (author.isPresent()) {
            return new ResponseEntity<>(authorRepository.findByEmailIgnoreCase(email), HttpStatus.OK);
        }
        return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.OK);
    }


    @RequestMapping(path="/books", method = RequestMethod.GET)
    public List<Book> showBooks() {
        return bookRepository.findAll();
    }
}
