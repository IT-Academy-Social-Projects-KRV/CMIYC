package com.ms.data.service.builders;

import com.ms.data.dto.form.input.HtmlInput;
import com.ms.data.dto.xml.Field;
import com.ms.data.service.SearchFormBuilderService;

public interface HtmlInputBuilder {

    boolean canBuild(Field field);

    HtmlInput build(Field field, SearchFormBuilderService searchFormBuilderService);
}
