package com.external.repository;

import com.external.dto.request.RequestPayload;
import com.external.entity.Person;
import com.external.utils.MapperUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.core.io.ClassPathResource;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class PersonRepository {

    private static final String MOCK_DATA_FILE_NAME = "mock_data.json";

    private final Set<Person> persons = new HashSet<>();

    public PersonRepository() {
        onInit();
    }

    @SneakyThrows
    private void onInit() {
        ObjectMapper mapper = MapperUtils.objectMapper;
        Person[] people = mapper.readValue(new ClassPathResource(MOCK_DATA_FILE_NAME).getFile(), Person[].class);

        int id = 1000;
        for (Person person : people) {
            person.setId(++id);
            persons.add(person);
        }
    }

    public List<Person> findAllByRequest(RequestPayload request) {
        return persons.stream()
                .filter(request::isEqualToPerson)
                .collect(Collectors.toList());
    }

}
