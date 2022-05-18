package com.ms.data.service.builders.complex;

import com.ms.data.dto.form.input.ComplexInput;
import com.ms.data.dto.form.input.HtmlInput;
import com.ms.data.dto.form.input.NumberInput;
import com.ms.data.dto.xml.Field;
import com.ms.data.dto.xml.FieldComponent;
import com.ms.data.service.SearchFormBuilderService;
import com.ms.data.service.builders.HtmlInputBuilderByType;
import org.springframework.stereotype.Component;

@Component
public class DateObjectInputBuilder extends HtmlInputBuilderByType {

    @Override
    public boolean canBuild(String type) {
        return "Date".equals(type);
    }

    @Override
    public HtmlInput build(Field field, SearchFormBuilderService searchFormBuilderService) {
        ComplexInput dateInput = new ComplexInput(
                field.getName(), field.getDescription(), field.getMaxLength()
        );

        for (FieldComponent component : field.getComponents()) {
            Field componentAsField = component.asField(field);
            HtmlInput htmlInput = searchFormBuilderService.buildInput(componentAsField);

            if(htmlInput instanceof NumberInput) {
                NumberInput numberInput = (NumberInput) htmlInput;
                switch (htmlInput.getName().toLowerCase()) {
                    case "day":
                    case "date": {
                        numberInput.setMin(1);
                        numberInput.setMax(31);
                        break;
                    }
                    case "month": {
                        numberInput.setMin(1);
                        numberInput.setMax(12);
                        break;
                    }
                    case "year": {
                        numberInput.setMin(1800);
                        numberInput.setMax(9999);
                        break;
                    }
                }
            }

            dateInput.getComponents().add(htmlInput);
        }

        return dateInput;
    }
}
