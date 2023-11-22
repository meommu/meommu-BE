package com.meommu.meommuapi.mail.exception;

import com.meommu.meommuapi.common.exception.InternalServerException;
import com.meommu.meommuapi.mail.exception.errorCode.MailErrorCode;

public class MailTransferException extends InternalServerException {

	public MailTransferException() {
		super(MailErrorCode.MAIL_TRANSFER_ERROR);
	}
}
