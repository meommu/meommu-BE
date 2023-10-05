package com.meommu.meommuapi.util.documentation;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;

import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor;
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor;

public class DocumentUtils {

	public static OperationRequestPreprocessor getDocumentRequest() {
		return preprocessRequest(
			modifyUris()
				.scheme("https")
				.host("port-0-meommu-api-jvvy2blm5wku9j.sel5.cloudtype.app")
				.removePort(),
			prettyPrint());
	}

	public static OperationResponsePreprocessor getDocumentResponse() {
		return preprocessResponse(prettyPrint());
	}
}
