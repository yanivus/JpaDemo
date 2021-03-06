package com.jpa2.demo.repository.jpa;

import com.jpa2.demo.entity.QAuthor;
import com.jpa2.demo.entity.QBook;
import com.jpa2.demo.exception.DbResultNotFoundException;
import com.jpa2.demo.repository.BaseRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;

public abstract class BaseRepositoryImpl<T, ID> extends SimpleJpaRepository<T, ID> implements BaseRepository<T, ID> {

    protected final QAuthor author = QAuthor.author;
    protected final QBook book = QBook.book;

    private final EntityManager em;
    protected final JPAQueryFactory queryFactory;


    public BaseRepositoryImpl(Class<T> domainClass, EntityManager em) {
        super(domainClass, em);
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public T findByIdMandatory(ID id) throws DbResultNotFoundException {
        return findById(id)
                .orElseThrow(() -> {
                    String errorMessage = String.format("Entity [%s] with id [%s] was not found in DB", getDomainClass().getSimpleName(), id);
                    return new DbResultNotFoundException(errorMessage);
                });
    }

    public void clear() {
        em.clear();
    }

    public void detach(T entity) {
        em.detach(entity);
    }
}
