package com.ms.data.service.builders;

import com.ms.data.dto.xml.Field;

public abstract class HtmlInputBuilderByNameAndType implements HtmlInputBuilder {

    protected abstract boolean canBuild(String name, String type);

    @Override
    public boolean canBuild(Field field) {
        return canBuild(field.getName(), field.getType());
    }
}
