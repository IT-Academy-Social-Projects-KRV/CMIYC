package com.ms.data.service.builders;

import com.ms.data.dto.xml.Field;

public abstract class HtmlInputBuilderByType implements HtmlInputBuilder {

    @Override
    public boolean canBuild(Field field) {
        return canBuild(field.getType());
    }

    protected abstract boolean canBuild(String type);
}
