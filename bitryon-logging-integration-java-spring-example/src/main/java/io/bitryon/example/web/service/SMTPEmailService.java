package io.bitryon.example.web.service;

import org.springframework.stereotype.Service;

import io.bitryon.logger.Logger;
import io.bitryon.logger.annotation.Logging;
import jakarta.annotation.Resource;

@Service
public class SMTPEmailService {
	
	@Resource
	Logger logger;

	@Logging(sanitizerPatterns="*/MASK$3(recipient)", skipRandom=Integer.MAX_VALUE >>> 1, name="Send Verification Url To Customers")// mask the recipient JSON/*EmailService.sendByNoRepply*
	public String sendVerificationUrl(String recipient, String verificationUrl) {
		recipient = recipient.trim();
		String htmlContent = 
				  "<html><body>Please click on the <a href='"+verificationUrl+"'>link</a> "
				+ "or copy " + verificationUrl + " to your brower before it expires in 10 minutes.</body></html>";
		logger.text(htmlContent);// specially log it but still be count in the traces
		return htmlContent;
	}

}