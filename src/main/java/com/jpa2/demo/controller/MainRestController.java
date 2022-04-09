package com.jpa2.demo.controller;

import com.jpa2.demo.entity.Author;
import com.jpa2.demo.entity.Book;
import com.jpa2.demo.repository.BaseRepository;
import com.jpa2.demo.repository.jpa.AuthorRepository;
import com.jpa2.demo.repository.jpa.BaseRepositoryImpl;
import com.jpa2.demo.repository.jpa.BookRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class MainRestController {

    @Autowired
    AuthorRepository authorRepository;
//
//    @Autowired
//    BaseRepository baseRepository;


    @RequestMapping(path="/authors", method = RequestMethod.GET)
    @ResponseBody
    public List<Author> showAuthors() {
        return authorRepository.findAll();
    }

//
//    @RequestMapping(path="/books", method = RequestMethod.GET)
//    @ResponseBody
//    public List<Book> showBooks() {
//        return baseRepository.findAll();
//    }
}
