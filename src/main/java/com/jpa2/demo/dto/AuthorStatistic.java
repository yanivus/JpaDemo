package com.jpa2.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;


@AllArgsConstructor
public @Data
class AuthorStatistic {

    private String authorName;
    private Long bookSize;

}
