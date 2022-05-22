package com.external.repository;

import com.external.MockRepositoryApplication;
import com.external.dto.request.Payload;
import com.external.entity.Person;
import com.external.utils.MapperUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PersonRepository {

    private static final String MOCK_DATA_FILE_NAME = "/mock_data.json";

    private final List<Person> persons = new ArrayList<>();

    public PersonRepository() {
        onInit();
    }

    @SneakyThrows
    private void onInit() {

        ObjectMapper mapper = MapperUtils.objectMapper;

        StringBuilder fileContent = new StringBuilder();
        try (
                InputStream inputStream = MockRepositoryApplication.class.getResourceAsStream(MOCK_DATA_FILE_NAME);
                BufferedReader br = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputStream)))
        ) {
            String line;
            while ((line = br.readLine()) != null) {
                fileContent.append(line)
                        .append("\n");
            }
        }

        String mockDataFile = fileContent.toString();
        Person[] people = mapper.readValue(mockDataFile, Person[].class);

        Collections.addAll(persons, people);
    }

    public List<Person> findAllByRequest(Payload request) {
        return persons.stream()
                .filter(request::isEqualToPerson)
                .collect(Collectors.toList());
    }

    public List<Person> getAll() {
        return persons;
    }
}
