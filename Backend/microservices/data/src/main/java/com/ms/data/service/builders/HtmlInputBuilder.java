package com.ms.data.service.builders;

import com.ms.data.dto.form.input.HtmlInput;
import com.ms.data.dto.xml.Field;

public interface JsonInputBuilder {

    boolean canBuild();

    HtmlInput build(Field field);
}
