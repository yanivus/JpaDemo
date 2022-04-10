package com.jpa2.demo.repository.jpa;

import com.jpa2.demo.entity.Author;
import com.jpa2.demo.entity.Book;

import javax.persistence.EntityManager;

public class BookRepositoryImpl extends BaseRepositoryImpl<Book, Long>  implements BookRepository {

//    public BookRepositoryImpl(Class<Book> domainClass, EntityManager em) {
//        super(domainClass, em);
//    }

    public BookRepositoryImpl(EntityManager em) {
        super(Book.class, em);
    }
}
