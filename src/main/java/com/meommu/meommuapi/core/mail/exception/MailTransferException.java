package com.meommu.meommuapi.core.mail.exception;

import com.meommu.meommuapi.core.mail.exception.errorCode.MailErrorCode;
import com.meommu.meommuapi.global.exception.InternalServerException;

public class MailTransferException extends InternalServerException {

	public MailTransferException() {
		super(MailErrorCode.MAIL_TRANSFER_ERROR);
	}
}
