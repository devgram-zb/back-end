package com.project.devgram.type.status;

import java.util.EnumSet;
import java.util.NoSuchElementException;
import java.util.Objects;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class StatusConvertor implements AttributeConverter<Status,String> {

	@Override
	public String convertToDatabaseColumn(Status attribute) {
		return attribute.getValue();
	}

	@Override
	public Status convertToEntityAttribute(String dbData) {
		return EnumSet
			.allOf(Status.class)
			.stream()
			.filter(status -> Objects.equals(dbData, status.getValue()))
			.findAny()
			.orElseThrow(NoSuchElementException::new);
	}
}
