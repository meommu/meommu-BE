package com.meommu.meommuapi.mail.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.meommu.meommuapi.mail.exception.MailTransferException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional(readOnly = true)
@Service
public class MailService {

	private final JavaMailSender emailSender;

	public MailService(JavaMailSender emailSender) {
		this.emailSender = emailSender;
	}

	@Transactional
	public void sendEmail(
		String toEmail,
		String title,
		String text) {
		SimpleMailMessage emailForm = createEmailForm(toEmail, title, text);
		try {
			emailSender.send(emailForm);
		} catch (RuntimeException e) {
			throw new MailTransferException();
		}
	}

	private SimpleMailMessage createEmailForm(
		String toEmail,
		String title,
		String text) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(toEmail);
		message.setSubject(title);
		message.setText(text);

		return message;
	}
}
