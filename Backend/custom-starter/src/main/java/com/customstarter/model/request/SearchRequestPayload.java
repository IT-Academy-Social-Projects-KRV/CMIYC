package com.customstarter.model.request;

import com.customstarter.model.Date;
import com.customstarter.model.Name;
import com.customstarter.model.RaceCode;
import com.customstarter.model.SexCode;
import com.customstarter.model.StateCode;
import com.customstarter.utils.ImageIndicatorDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
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
