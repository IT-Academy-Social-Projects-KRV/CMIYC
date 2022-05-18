package com.ms.search.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.ms.search.utils.ImageIndicatorDeserializer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class SearchRequestPayload {

    private Name name;
    private Date birthDate;
    private String operatorLicenseNumber;

    @JsonDeserialize(using = ImageIndicatorDeserializer.class)
    private boolean imageIndicator;

    private SexCode sexCode;
    private RaceCode raceCode;
    private StateCode state;
}
