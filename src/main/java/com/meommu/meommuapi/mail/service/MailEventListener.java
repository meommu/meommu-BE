package com.meommu.meommuapi.mail.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import com.meommu.meommuapi.kindergarten.service.SendCodeEvent;

@Component
public class MailEventListener {

	private final MailService mailService;

	public MailEventListener(MailService mailService) {
		this.mailService = mailService;
	}

	@Async
	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT, classes = SendCodeEvent.class)
	public void handle(SendCodeEvent event) {
		String title = "meommu 이메일 인증 메일";
		mailService.sendEmail(event.email(), title, event.code());
	}
}
