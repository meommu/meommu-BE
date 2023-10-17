package com.meommu.meommuapi.diary.domain.embedded;

import java.util.Objects;

import com.meommu.meommuapi.diary.exception.InvalidTitleFormatException;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Title {

	private static final int LIMIT_LENGTH = 20;

	@Column(name = "title")
	private String value;

	protected Title() {
	}

	private Title(String value) {
		this.value = value;
	}

	public static Title from(String value) {
		String trimValue = value.trim();
		validate(trimValue);
		return new Title(trimValue);
	}

	private static void validate(String value) {
		if (value.length() > LIMIT_LENGTH || value.length() == 0) {
			throw new InvalidTitleFormatException();
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
		Title title = (Title)o;
		return Objects.equals(value, title.value);
	}

	@Override
	public int hashCode() {
		return value != null ? value.hashCode() : 0;
	}
}

