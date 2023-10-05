package com.meommu.meommuapi.util.documentation;

import static org.springframework.restdocs.snippet.Attributes.*;

public class DocumentFormatGenerator {

	public static Attribute getConstraints(String key, String value) {
		return key(key).value(value);
	}
}
