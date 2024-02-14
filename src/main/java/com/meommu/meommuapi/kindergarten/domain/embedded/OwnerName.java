package com.meommu.meommuapi.kindergarten.domain.embedded;

import java.util.Objects;

import com.meommu.meommuapi.kindergarten.exception.InvalidOwnerNameFormatException;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class OwnerName {

	private static final int LIMIT_MIN_LENGTH = 2;
	private static final int LIMIT_MAX_LENGTH = 8;

	@Column(name = "owner_name")
	private String value;

	protected OwnerName() {
	}

	private OwnerName(String value) {
		this.value = value;
	}

	public static OwnerName from(String value) {
		String trimValue = value.trim();
		validate(trimValue);
		return new OwnerName(trimValue);
	}

	public static void validate(String value) {
		if (value == null || !(value.length() >= LIMIT_MIN_LENGTH && value.length() <= LIMIT_MAX_LENGTH)) {
			throw new InvalidOwnerNameFormatException();
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
		OwnerName name = (OwnerName)o;
		return Objects.equals(value, name.value);
	}

	@Override
	public int hashCode() {
		return value != null ? value.hashCode() : 0;
	}
}
