package com.jpa2.demo.controller;

import com.jpa2.demo.dto.AuthorStatistic;
import com.jpa2.demo.entity.Author;
import com.jpa2.demo.entity.Book;
import com.jpa2.demo.model.EmptyJsonResponse;
import com.jpa2.demo.repository.jpa.AuthorRepository;
import com.jpa2.demo.repository.jpa.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import javax.validation.constraints.Size;
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

//    @RequestMapping(path="/authorByEmail", method = RequestMethod.GET)
//    public ResponseEntity<Optional<Author>> fetchAuthorByEmail(@RequestParam(name="email") String email) {
//        Optional<Author> author = authorRepository.findByEmailIgnoreCase(email);
//        if (author.isPresent()) {
//            return new ResponseEntity<>(author, HttpStatus.OK);
//        }
//        return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.OK);
//    }


    @RequestMapping(path = "/authorByEmail", method = RequestMethod.GET)
    public ResponseEntity<Optional<Author>> fetchAuthorByEmail(@RequestParam(name = "email") String email) {
        Optional<Author> author = authorRepository.findByEmailIgnoreCase(email);
        if (author.isPresent()) {
            // self link - hateoas
             Link selfLink = linkTo(methodOn(MainRestController.class).fetchAuthorByEmail(email)).withSelfRel();
             author.get().add(selfLink);

            return new ResponseEntity<>(author, HttpStatus.OK);
        }
        return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.OK);
    }

    @RequestMapping(path="/authorByAny", method = RequestMethod.GET)
    public ResponseEntity<List<Author>> fetchAuthorByAny(@RequestParam(name="name", required = false) String name,
                                                         @RequestParam(name="email", required = false) String email) {
        List<Author> author = authorRepository.findByAny(name, email);
        return new ResponseEntity<>(author, HttpStatus.OK);
//        if (author.isPresent()) {
//            return new ResponseEntity<>(author, HttpStatus.OK);
//        }
//        return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.OK);
    }

    @RequestMapping(path="/books", method = RequestMethod.GET)
    public List<Book> showBooks() {
        return bookRepository.findAll();
    }


    @RequestMapping(path="/authorsPaginated", method = RequestMethod.GET)
    public List<Author> showAuthorsPaginated(@RequestParam(name="limit", required = true, defaultValue = "1") @Size(min = 1) Integer limit,
                                             @RequestParam(name="offset", required = true) @Size(min = 0)  Integer offset) {
        return authorRepository.finalAllWithBooksPaginated(limit.intValue(), offset.intValue());
    }

}
