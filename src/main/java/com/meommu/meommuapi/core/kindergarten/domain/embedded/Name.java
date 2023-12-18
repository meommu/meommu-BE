package com.meommu.meommuapi.core.kindergarten.domain.embedded;

import java.util.Objects;

import com.meommu.meommuapi.core.kindergarten.exception.InvalidNameFormatException;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Name {

	private static final int LIMIT_MIN_LENGTH = 2;
	private static final int LIMIT_MAX_LENGTH = 13;

	@Column(name = "name")
	private String value;

	protected Name() {
	}

	private Name(String value) {
		this.value = value;
	}

	public static Name from(String value) {
		String trimValue = value.trim();
		validate(trimValue);
		return new Name(value);
	}

	public static void validate(String value) {
		if (value == null || !(value.length() >= LIMIT_MIN_LENGTH && value.length() <= LIMIT_MAX_LENGTH)) {
			throw new InvalidNameFormatException();
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
		Name name = (Name)o;
		return Objects.equals(value, name.value);
	}

	@Override
	public int hashCode() {
		return value != null ? value.hashCode() : 0;
	}
}
