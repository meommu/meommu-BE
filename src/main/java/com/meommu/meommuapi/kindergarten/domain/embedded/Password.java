package com.meommu.meommuapi.kindergarten.domain.embedded;

import java.util.Objects;
import java.util.regex.Pattern;

import com.meommu.meommuapi.kindergarten.exception.InvalidPasswordFormatException;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Password {

	private static final Pattern PATTERN = Pattern.compile(
		"^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,20}$");

	@Column(name = "password")
	private String value;

	protected Password() {
	}

	private Password(String value) {
		this.value = value;
	}

	public static Password of(Encryptor encryptor, String value) {
		validate(value);
		return new Password(encryptor.encrypt(value));
	}

	public static void validate(String value) {
		if (value == null || !PATTERN.matcher(value).matches()) {
			throw new InvalidPasswordFormatException();
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
		Password name = (Password)o;
		return Objects.equals(value, name.value);
	}

	@Override
	public int hashCode() {
		return value != null ? value.hashCode() : 0;
	}
}
