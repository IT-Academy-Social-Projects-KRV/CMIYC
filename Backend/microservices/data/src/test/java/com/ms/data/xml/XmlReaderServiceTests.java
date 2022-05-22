package com.ms.data.xml;

import com.ms.data.dto.xml.InterfaceSchema;
import com.ms.data.dto.xml.Transaction;
import com.ms.data.service.CloudStorageService;
import com.ms.data.service.XmlReaderService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UncheckedIOException;

import static java.nio.charset.StandardCharsets.UTF_8;

@SpringBootTest
public class XmlReaderServiceTests {

    @MockBean
    private CloudStorageService cloudStorageService;

    @Autowired
    private XmlReaderService xmlReaderService;

    @Value("classpath:schema.xml")
    private Resource resource;

    private String xml;

    @BeforeEach
    void setUp() {
        try (Reader reader = new InputStreamReader(resource.getInputStream(), UTF_8)) {
            xml = FileCopyUtils.copyToString(reader);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Test
    public void contextLoads() {
        Assertions.assertNotNull(xmlReaderService);
    }

    @Test
    public void xmlReaderService_ifBadXml_exceptionThrown() {
        Assertions.assertThrows(Exception.class, () -> xmlReaderService.read("Some text"), "If no xml passed, exception must be thrown");
        Assertions.assertThrows(Exception.class, () -> xmlReaderService.read(""), "If empty xml passed, exception must be thrown");
        Assertions.assertThrows(Exception.class, () -> xmlReaderService.read(null), "If null xml passed, exception must be thrown");
    }

    @Test
    public void xmlReaderService_ifCorrectXml_schemaObjectBuilt() {
        InterfaceSchema schema = xmlReaderService.read(xml);

        Transaction transaction = schema.getTransaction();
        Assertions.assertNotNull(transaction);

        Assertions.assertFalse(transaction.getFields().isEmpty());
        Assertions.assertFalse(transaction.getCombinations().isEmpty());

        Assertions.assertEquals(3, transaction.getCombinations().size());
        Assertions.assertEquals(7, transaction.getFields().size());

        Assertions.assertEquals("BirthDate", transaction.getFields().get(0).getName());
        Assertions.assertEquals(3, transaction.getFields().get(0).getComponents().size());
        Assertions.assertEquals("Day", transaction.getFields().get(0).getComponents().get(0).getName());
    }
}
