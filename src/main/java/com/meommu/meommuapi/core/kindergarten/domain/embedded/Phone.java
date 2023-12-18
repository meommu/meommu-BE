package com.meommu.meommuapi.core.kindergarten.domain.embedded;

import java.util.Objects;
import java.util.regex.Pattern;

import com.meommu.meommuapi.core.kindergarten.exception.InvalidPhoneFormatException;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Phone {

	private static final Pattern PATTERN = Pattern.compile("^\\d{3}-\\d{3,4}-\\d{4}");

	@Column(name = "phone")
	private String value;

	protected Phone() {
	}

	private Phone(String value) {
		this.value = value;
	}

	public static Phone from(String value) {
		validate(value);
		return new Phone(value);
	}

	public static void validate(String value) {
		if (value == null || !PATTERN.matcher(value).matches()) {
			throw new InvalidPhoneFormatException();
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
		Phone name = (Phone)o;
		return Objects.equals(value, name.value);
	}

	@Override
	public int hashCode() {
		return value != null ? value.hashCode() : 0;
	}
}
