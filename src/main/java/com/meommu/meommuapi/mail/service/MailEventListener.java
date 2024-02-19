package com.meommu.meommuapi.mail.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import com.meommu.meommuapi.kindergarten.service.PasswordFindEvent;

@Component
public class MailEventListener {

	private final MailService mailService;

	public MailEventListener(MailService mailService) {
		this.mailService = mailService;
	}

	@Async
	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT, classes = PasswordFindEvent.class)
	public void handle(PasswordFindEvent event) {
		mailService.sendEmail(event.email(), event.title(), event.code());
	}
}
