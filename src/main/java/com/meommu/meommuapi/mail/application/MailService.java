package com.meommu.meommuapi.mail.application;

public interface MailService {
	void sendEmail(String toEmail, String title, String text);
}
