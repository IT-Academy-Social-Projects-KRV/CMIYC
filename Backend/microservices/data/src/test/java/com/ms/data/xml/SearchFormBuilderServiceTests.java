package com.ms.data.xml;

import com.customstarter.model.StateCode;
import com.customstarter.model.schema.Schema;
import com.customstarter.model.schema.input.ComplexInput;
import com.customstarter.model.schema.input.HtmlInput;
import com.customstarter.model.schema.input.HtmlInputType;
import com.customstarter.model.schema.input.NumberInput;
import com.customstarter.model.schema.input.Select;
import com.ms.data.dto.xml.Combination;
import com.ms.data.dto.xml.Field;
import com.ms.data.dto.xml.FieldComponent;
import com.ms.data.dto.xml.Fields;
import com.ms.data.dto.xml.InterfaceSchema;
import com.ms.data.dto.xml.Requirements;
import com.ms.data.dto.xml.Transaction;
import com.ms.data.service.CloudStorageService;
import com.ms.data.service.SearchFormBuilderService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

@SpringBootTest
public class SearchFormBuilderServiceTests {

    @MockBean
    private CloudStorageService cloudStorageService;

    @Autowired
    private SearchFormBuilderService searchFormBuilderService;

    private InterfaceSchema interfaceSchema;

    private final String formName = "Form name";
    private final String fieldName = "name";
    private final String combinationName1 = "comb1";
    private final String combinationName2 = "comb2";

    @BeforeEach
    void setUp() {

        Field actualField = new Field();
        actualField.setName(fieldName);
        actualField.setType("Alphabetic");

        Field fieldReference = new Field();
        fieldReference.setReference(fieldName);

        Requirements requirements = new Requirements();
        requirements.setFields(new Fields());
        requirements.getFields().getMandatoryFields().add(fieldReference);

        Combination firstCombination = new Combination();
        firstCombination.setPrimaryFieldReference(combinationName1);
        firstCombination.setRequirements(requirements);

        Combination secondCombination = new Combination();
        secondCombination.setPrimaryFieldReference(combinationName2);

        Transaction transaction = new Transaction();
        transaction.setName(formName);
        transaction.setCombinations(List.of(firstCombination, secondCombination));
        transaction.setFields(List.of(actualField));

        interfaceSchema = new InterfaceSchema();
        interfaceSchema.setTransaction(transaction);
    }

    @Test
    public void contextLoads() {
        Assertions.assertNotNull(searchFormBuilderService);
    }

    @Test
    public void searchFormBuilderService_ifBadSchema_exceptionThrown() {
        Assertions.assertThrows(Exception.class, () -> searchFormBuilderService.buildSchemaForm(null));
        Assertions.assertThrows(Exception.class, () -> searchFormBuilderService.buildSchemaForm(new InterfaceSchema()));
    }

    @Test
    public void searchFormBuilderService_ifCorrectSchema_formBuilt() {
        Schema schema = searchFormBuilderService.buildSchemaForm(interfaceSchema);

        Assertions.assertEquals(formName, schema.getName());

        Assertions.assertEquals(1, schema.getInputs().size());
        Assertions.assertEquals(2, schema.getCombinations().size());

        Assertions.assertEquals(fieldName, schema.getInputs().get(0).getName());
        Assertions.assertEquals(HtmlInputType.text, schema.getInputs().get(0).getType());

        Assertions.assertEquals(combinationName1, schema.getCombinations().get(0).getApiName());
        Assertions.assertEquals(1, schema.getCombinations().get(0).getFields().size());

        Assertions.assertEquals(combinationName2, schema.getCombinations().get(1).getApiName());
        Assertions.assertEquals(0, schema.getCombinations().get(1).getFields().size());
    }

    @Test
    public void searchFormBuilderService_ifInputNameIsState_stateSelectBuilt() {
        Field selectField = new Field();
        selectField.setType("Alphabetical");
        selectField.setName("State");

        HtmlInput htmlInput = searchFormBuilderService.buildInput(selectField);
        Assertions.assertInstanceOf(Select.class, htmlInput);

        Select select = (Select) htmlInput;
        Assertions.assertEquals(StateCode.values().length + 1, select.getOptions().size());
        Assertions.assertEquals(StateCode.values()[0].name(), select.getOptions().get(1).getValue());
    }

    @Test
    public void searchFormBuilderService_ifComplexField_complexInputBuilt() {
        String complexFieldName = "Name";
        int maxLength = 30;
        String firstComponentName = "first";

        FieldComponent firstComponent = new FieldComponent();
        firstComponent.setName(firstComponentName);
        firstComponent.setType("Alphabetic");

        FieldComponent lastComponent = new FieldComponent();
        lastComponent.setName("last");
        lastComponent.setType("Alphabetic");

        Field complexField = new Field();
        complexField.setName(complexFieldName);
        complexField.setMaxLength(maxLength);
        complexField.setComponents(List.of(firstComponent, lastComponent));

        HtmlInput htmlInput = searchFormBuilderService.buildInput(complexField);
        Assertions.assertInstanceOf(ComplexInput.class, htmlInput);

        ComplexInput complexInput = (ComplexInput) htmlInput;
        Assertions.assertEquals(complexFieldName, complexInput.getName());
        Assertions.assertEquals(maxLength, complexInput.getMaxLength());
        Assertions.assertEquals(2, complexInput.getComponents().size());

        Assertions.assertEquals(firstComponentName, complexInput.getComponents().get(0).getName());
        Assertions.assertEquals(HtmlInputType.text, complexInput.getComponents().get(0).getType());
    }

    @Test
    public void searchFormBuilderService_ifNumericField_numberInputBuilt() {
        String inputName = "number";

        Field numericField = new Field();
        numericField.setName(inputName);
        numericField.setMaxLength(2);
        numericField.setType("Numeric");

        HtmlInput htmlInput = searchFormBuilderService.buildInput(numericField);
        Assertions.assertInstanceOf(NumberInput.class, htmlInput);

        NumberInput numberInput = (NumberInput) htmlInput;
        Assertions.assertEquals(inputName, numberInput.getName());
        Assertions.assertEquals(99, numberInput.getMax());
    }
}
