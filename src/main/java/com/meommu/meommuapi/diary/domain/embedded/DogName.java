package com.meommu.meommuapi.diary.domain.embedded;

import java.util.Objects;

import com.meommu.meommuapi.diary.exception.InvalidDogNameFormatException;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class DogName {

	private static final int LIMIT_LENGTH = 10;

	@Column(name = "dog_name")
	private String value;

	protected DogName() {
	}

	private DogName(String value) {
		this.value = value;
	}

	public static DogName from(String value) {
		String trimValue = value.trim();
		validate(trimValue);
		return new DogName(trimValue);
	}

	private static void validate(String value) {
		if (value == null || value.length() > LIMIT_LENGTH || value.length() == 0) {
			throw new InvalidDogNameFormatException();
		}
	}

	public String getValue() {
		return value;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		DogName title = (DogName)o;
		return Objects.equals(value, title.value);
	}

	@Override
	public int hashCode() {
		return value != null ? value.hashCode() : 0;
	}
}

