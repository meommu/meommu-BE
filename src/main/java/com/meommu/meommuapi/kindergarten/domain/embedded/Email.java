package com.meommu.meommuapi.kindergarten.domain.embedded;

import java.util.Objects;
import java.util.regex.Pattern;

import com.meommu.meommuapi.kindergarten.exception.InvalidEmailFormatException;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Email {

	private static final Pattern PATTERN = Pattern.compile("^[a-z]{1}[a-z0-9_\\.]+@[a-z\\.]+\\.[a-zA-Z]+$");

	@Column(name = "email")
	private String value;

	protected Email() {
	}

	private Email(String value) {
		this.value = value;
	}

	public static Email from(String value) {
		String trimValue = value.trim();
		validate(trimValue);
		return new Email(trimValue);
	}

	public static void validate(String value) {
		if (!PATTERN.matcher(value).matches()) {
			throw new InvalidEmailFormatException();
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
		Email name = (Email)o;
		return Objects.equals(value, name.value);
	}

	@Override
	public int hashCode() {
		return value != null ? value.hashCode() : 0;
	}
}
