package com.jpa2.demo.repository.jpa;


import com.jpa2.demo.dto.AuthorStatistic;
import com.jpa2.demo.entity.Author;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.data.domain.PageRequest;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

public class AuthorRepositoryImpl extends BaseRepositoryImpl<Author, Long> implements AuthorRepository {

    public AuthorRepositoryImpl(EntityManager em) {
        super(Author.class, em);
    }

    @Override
    public List<Author> findByAny(String name, String email) {
        BooleanBuilder where = new BooleanBuilder();
        if (name != null && name.length() > 0) {
            where.or(author.full_name.toLowerCase().contains(name.toLowerCase(Locale.ROOT)));
        }
        if (email != null && email.length() > 0) {
            where.or(author.email.toLowerCase().contains(email.toLowerCase(Locale.ROOT)));
        }

        Optional<List<Author>> authors = Optional.ofNullable(queryFactory
                .select(author)
                .from(author)
                .where(where)
                .orderBy(author.full_name.asc())
                .fetch());

        if (authors.isPresent()) {
            return authors.get();
        }

        return new ArrayList<Author>(0);
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
                .groupBy(author.full_name)
                .select(Projections.constructor(AuthorStatistic.class,
                        author.full_name,
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

    @Override
    public List<Author> finalAllWithBooksPaginated(int limit, int offset) {
        PageRequest pageRequest = PageRequest.of(0, 5);
        return queryFactory
                .select(author).distinct()
                .from(author)
                .innerJoin(author.books, book).fetchJoin()
                .limit(limit)
                .offset(offset)
                .orderBy(author.id.asc())
                .fetch();
    }
}
