package com.jpa2.demo.repository.jpa;

import com.jpa2.demo.dto.AuthorStatistic;
import com.jpa2.demo.entity.Author;
import com.jpa2.demo.repository.BaseRepository;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends BaseRepository<Author, Long> {

    List<Author> findByAny(String name, String email);

    Optional<Author> findByEmailIgnoreCase(String email);

    List<AuthorStatistic> findAuthorStatistic();

    List<Author> findAllWithBooks();

}
