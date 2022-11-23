package com.nastudy.stubox.domain;

import org.springframework.util.StringUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.stream.Collectors;

@Converter
public class CategorySetConverter implements AttributeConverter<EnumSet<Category>, String> {
    @Override
    public String convertToDatabaseColumn(EnumSet<Category> attribute) {
        return attribute.stream().map(e -> e.name()).collect(Collectors.joining("|"));
    }

    @Override
    public EnumSet<Category> convertToEntityAttribute(String dbData) {
        if (!StringUtils.hasText(dbData)) return EnumSet.noneOf(Category.class);

        String[] dbDataArr = StringUtils.trimAllWhitespace(dbData).split("\\|");
        return Arrays.stream(dbDataArr).map(Category :: valueOf)
                .collect(Collectors.toCollection(()->EnumSet.noneOf(Category.class)));
    }
}
