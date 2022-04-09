package com.jpa2.demo.repository.jpa;

import com.jpa2.demo.entity.Book;

import javax.persistence.EntityManager;

public class BookRepositoryImpl extends BaseRepositoryImpl<Book, Long>  {


    public BookRepositoryImpl(Class<Book> domainClass, EntityManager em) {
        super(domainClass, em);
    }
}
