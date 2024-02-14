package com.meommu.meommuapi.mail.exception;

import com.meommu.meommuapi.mail.exception.errorCode.MailErrorCode;
import com.meommu.meommuapi.core.exception.InternalServerException;

public class MailTransferException extends InternalServerException {

	public MailTransferException() {
		super(MailErrorCode.MAIL_TRANSFER_ERROR);
	}
}
