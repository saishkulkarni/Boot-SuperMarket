package org.jsp.super_market.helper;

import org.jsp.super_market.dto.Customer;
import org.jsp.super_market.dto.Merchant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class VerificationEmailSender {

	@Autowired
	JavaMailSender mailSender;

	public void sendEmail(Merchant merchant) {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		try {
			helper.setFrom("SuperMarket@gmail.com");
			helper.setTo(merchant.getEmail());
			helper.setSubject("Verification Mail");
			helper.setText("Hello " + merchant.getName() + " Your OTP for Mail verification is " + merchant.getOtp());
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		mailSender.send(message);

	}
	
	public void sendEmail(Customer customer) {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		try {
			helper.setFrom("SuperMarket@gmail.com");
			helper.setTo(customer.getEmail());
			helper.setSubject("Verification Mail");
			helper.setText("Hello " + customer.getName() + " Your OTP for Mail verification is " + customer.getOtp());
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		mailSender.send(message);

	}

}
