package com.external;

import com.customstarter.model.Date;
import com.customstarter.model.Name;
import com.customstarter.model.RaceCode;
import com.customstarter.model.SexCode;
import com.external.dto.API;
import com.external.dto.SearchRequest;
import com.external.dto.request.Payload;
import com.external.dto.request.PayloadOne;
import com.external.entity.Person;
import com.external.exception.BadRequestException;
import com.external.exception.NoSplitInRequestException;
import com.external.exception.UnknownApiException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SearchRequestUnitTests {

    static String api = API.API1.getName();
    static String firstName = "Ivan";
    static String payload;

    @SneakyThrows
    @BeforeAll
    static void setup() {
        ObjectMapper mapper = new ObjectMapper();

        Name name = new Name();
        name.setFirst(firstName);
        name.setMiddle("Ivanovich");
        name.setLast("Sad");

        Date birthDate = new Date();
        birthDate.setDay(1);
        birthDate.setMonth(1);
        birthDate.setYear(1990);

        PayloadOne payloadOne = new PayloadOne();
        payloadOne.setName(name);
        payloadOne.setBirthDate(birthDate);
        payloadOne.setRaceCode(RaceCode.O);
        payloadOne.setSexCode(SexCode.M.name());

        payload = mapper.writeValueAsString(payloadOne);
    }

    @Test
    void searchRequestBuild_ifNoSplitInMessage_exceptionThrown() {
        assertThrows(
                NoSplitInRequestException.class,
                () -> SearchRequest.fromMessage(api + "#")
        );
    }

    @Test
    void searchRequestBuild_ifUnknownAPI_exceptionThrown() {
        assertThrows(
                UnknownApiException.class,
                () -> SearchRequest.fromMessage("SomeUnnecessaryLongNameForAPI" + SearchRequest.REQUEST_SPLIT + payload)
        );
    }

    @Test
    void searchRequestBuild_ifBadJSON_exceptionThrown() {
        assertThrows(
                BadRequestException.class,
                () -> SearchRequest.fromMessage(api + SearchRequest.REQUEST_SPLIT + "{KKKK}")
        );
    }

    @Test
    void searchRequestBuild_ifEverythingOkay_searchRequestReturned() {
        SearchRequest searchRequest = SearchRequest.fromMessage(api + SearchRequest.REQUEST_SPLIT + payload);
        assertSame(searchRequest.getApi(), API.API1, "Wrong API returned");

        PayloadOne requestPayload = (PayloadOne) searchRequest.getRequestPayload();
        assertEquals(requestPayload.getName().getFirst(), firstName, "Wrong firstName returned");
    }
}
