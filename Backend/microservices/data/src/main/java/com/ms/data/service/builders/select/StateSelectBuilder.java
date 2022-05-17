package com.ms.data.service.builders.select;

import com.ms.data.dto.form.input.HtmlInput;
import com.ms.data.dto.form.input.Select;
import com.ms.data.dto.form.input.SelectOption;
import com.ms.data.dto.xml.Field;
import com.ms.data.service.SearchFormBuilderService;
import com.ms.data.service.builders.HtmlInputBuilderByNameAndType;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StateSelectBuilder extends HtmlInputBuilderByNameAndType {

    private static final List<SelectOption> OPTIONS = List.of(
            new SelectOption("", "Unknown"),
            new SelectOption("AL", "Alabama"),
            new SelectOption("AK", "Alaska"),
            new SelectOption("AZ", "Arizona"),
            new SelectOption("AR", "Arkansas"),
            new SelectOption("CA", "California"),
            new SelectOption("CO", "Colorado"),
            new SelectOption("CT", "Connecticut"),
            new SelectOption("DE", "Delaware"),
            new SelectOption("DC", "District of Columbia"),
            new SelectOption("FL", "Florida"),
            new SelectOption("GA", "Georgia"),
            new SelectOption("HI", "Hawaii"),
            new SelectOption("ID", "Idaho"),
            new SelectOption("IL", "Illinois"),
            new SelectOption("IN", "Indiana"),
            new SelectOption("IA", "Iowa"),
            new SelectOption("KS", "Kansas"),
            new SelectOption("KY", "Kentucky"),
            new SelectOption("LA", "Louisiana"),
            new SelectOption("ME", "Maine"),
            new SelectOption("MD", "Maryland"),
            new SelectOption("MA", "Massachusetts"),
            new SelectOption("MI", "Michigan"),
            new SelectOption("MN", "Minnesota"),
            new SelectOption("MS", "Mississippi"),
            new SelectOption("MO", "Missouri"),
            new SelectOption("MT", "Montana"),
            new SelectOption("NE", "Nebraska"),
            new SelectOption("NV", "Nevada"),
            new SelectOption("NH", "New Hampshire"),
            new SelectOption("NJ", "New Jersey"),
            new SelectOption("NM", "New Mexico"),
            new SelectOption("NY", "New York"),
            new SelectOption("NC", "North Carolina"),
            new SelectOption("ND", "North Dakota"),
            new SelectOption("OH", "Ohio"),
            new SelectOption("OK", "Oklahoma"),
            new SelectOption("OR", "Oregon"),
            new SelectOption("PA", "Pennsylvania"),
            new SelectOption("RI", "Rhode Island"),
            new SelectOption("SC", "South Carolina"),
            new SelectOption("SD", "South Dakota"),
            new SelectOption("TN", "Tennessee"),
            new SelectOption("TX", "Texas"),
            new SelectOption("UT", "Utah"),
            new SelectOption("VT", "Vermont"),
            new SelectOption("VA", "Virginia"),
            new SelectOption("WA", "Washington"),
            new SelectOption("WV", "West Virginia"),
            new SelectOption("WI", "Wisconsin"),
            new SelectOption("WY", "Wyoming")
    );

    @Override
    protected boolean canBuild(String name, String type) {
        return "State".equals(name);
    }

    @Override
    public HtmlInput build(Field field, SearchFormBuilderService searchFormBuilderService) {
        Select select = new Select(field.getName(), field.getDescription());
        select.getOptions().addAll(OPTIONS);

        return select;
    }
}