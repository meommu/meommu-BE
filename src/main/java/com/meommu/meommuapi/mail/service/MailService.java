package com.meommu.meommuapi.mail.service;

public interface MailService {
	void sendEmail(String toEmail, String title, String text);
}
