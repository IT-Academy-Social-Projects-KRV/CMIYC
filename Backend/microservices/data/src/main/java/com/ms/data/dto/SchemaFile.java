package com.ms.data.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class SchemaFile {

    private final String name;
    private final boolean selected;
    private final LocalDateTime uploadedAt;
}
