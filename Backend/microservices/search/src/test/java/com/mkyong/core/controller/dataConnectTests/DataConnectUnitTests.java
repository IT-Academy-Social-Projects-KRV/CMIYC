package com.mkyong.core.controller.DataConnectTests;

import com.ms.search.connectInterface.DataConnect;
import com.ms.search.model.XmlObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest(classes= DataConnect.class)
public class DataConnectUnitTests {

    @MockBean
    private DataConnect dataConnect;

    String authorizationHeader;
    List<XmlObject> result;

    @BeforeEach
    void setUp() {
        authorizationHeader ="hi1";


        result = new ArrayList<>();

        XmlObject x1 = new XmlObject();
        x1.setFirstName("1");
        x1.setBirthDate("0");
        x1.setForeignDataSource("api1");
        result.add(x1);

        XmlObject x2 = new XmlObject();
        x2.setLastName("1");
        x2.setSex("0");
        x2.setForeignDataSource("api2");
        result.add(x2);
    }

    @Test
    public void xmlSchema_getDataFromData_XmlObjectReturned() throws Exception {
        when(dataConnect.xmlSchema(authorizationHeader)).thenReturn(result);

        assertThat(dataConnect.xmlSchema(authorizationHeader)).isEqualTo(result);
    }

}
