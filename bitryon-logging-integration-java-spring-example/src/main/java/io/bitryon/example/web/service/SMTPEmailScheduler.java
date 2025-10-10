package io.bitryon.example.web.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.stereotype.Service;

import io.bitryon.logger.Logger;
import io.bitryon.logger.annotation.Logging;
import io.bitryon.logger.provider.LoggerFactory;
import jakarta.annotation.Resource;

@Service
@Logging
public class SMTPEmailScheduler {
	
	@Resource
	SMTPEmailService smtpEmailService;

	private static final Logger logger = LoggerFactory.getAsyncLogger();

	@Logging(reset=true)// reset the context so each call will be new ids
	public void runMailRunnable() {
		if (mailRunnable!=null) {
			mailRunnable.run();
			mailRunnable = null;
		}
	}
    
	private Runnable mailRunnable = null;
	/**
	 * If the thread is created by another un-related thread, it needs to setup the log id manually in order to trace.
	 * In case it's the scheduler thread that is created from the main thread not the http pool thread.
	 * 
	 * @param recipient
	 * @param verificationUrl
	 */
	public void unHookedThreadDelivery(String recipient, String verificationUrl) {
//		logger.log("unHookedThreadDelivery");
		final String logId = logger.getStepLogId();
		mailRunnable = new Runnable() {// it will be running by another scheduler thread
			@Override
			public void run() {
				logger.setStepLogId(logId);// must set the log id for the un-related thread
				smtpEmailService.sendVerificationUrl(recipient, verificationUrl);
			}
		};
	}

	private ExecutorService executor = Executors.newFixedThreadPool(2);
	/**
	 * The executor threads are created from the http pool thread in case of, no deed setup the log id
	 * 
	 * @param recipient
	 * @param verificationUrl
	 */
	public void childThreadDelivery(String recipient, String verificationUrl) {
//		logger.log("childThreadDelivery");
//		new Thread(new Runnable() {
//			@Override
//			public void run() {
//				smtpEmailService.sendVerificationUrl(recipient, verificationUrl);
//			}
//		}).start();
		
		executor.submit(new Runnable() {
			@Override
			public void run() {
				smtpEmailService.sendVerificationUrl(recipient, verificationUrl);
			}
		});
	}

}