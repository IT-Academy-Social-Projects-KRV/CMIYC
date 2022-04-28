package com.ms.search.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class SearchQuery {

    private String firstName;
    private String lastName;
    private String birthDayDate;
    private String sex;
    private Set<String> foreignDataSource;
}
