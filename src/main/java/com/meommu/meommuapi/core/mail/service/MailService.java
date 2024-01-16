package com.meommu.meommuapi.core.mail.service;

public interface MailService {
	void sendEmail(String toEmail, String title, String text);
}
