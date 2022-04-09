package com.jpa2.demo.repository.jpa;


import com.jpa2.demo.dto.AuthorStatistic;
import com.jpa2.demo.entity.Author;
import com.querydsl.core.types.Projections;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class AuthorRepositoryImpl extends BaseRepositoryImpl<Author, Long> implements AuthorRepository {

    public AuthorRepositoryImpl(EntityManager em) {
        super(Author.class, em);
    }

    @Override
    public Optional<Author> findByEmailIgnoreCase(String email) {
        return Optional.ofNullable(queryFactory
                .select(author)
                .from(author)
                .where(author.email.equalsIgnoreCase(email))
                .fetchFirst());
    }

    @Override
    public List<AuthorStatistic> findAuthorStatistic() {
        return queryFactory
                .from(author)
                .innerJoin(author.books, book)
                .groupBy(author.fullName)
                .select(Projections.constructor(AuthorStatistic.class,
                        author.fullName,
                        book.count())
                )
                .fetch();
    }

    @Override
    public List<Author> findAllWithBooks() {
        return queryFactory
                .select(author).distinct()
                .from(author)
                .innerJoin(author.books, book).fetchJoin()
                .fetch();
    }
}
