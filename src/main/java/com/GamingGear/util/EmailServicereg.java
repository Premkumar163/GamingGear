package com.GamingGear.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServicereg {

	@Autowired
	private JavaMailSender mailSender;
	
	public void sendEmail(String to,String subject,String body) {
		
		SimpleMailMessage message=new SimpleMailMessage();
		message.setTo(to);
		message.setSubject(subject);
		message.setText(body);
		try {
			mailSender.send(message);
			System.out.println(" mailsender otp work ");
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(" mailsender not otp work ");
		}
		
		
		
	}
	
}
//
//package com.GamingGear.util;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.stereotype.Service;
//
//@Service
//public class EmailServicereg {
//	
//    @Autowired
//    private JavaMailSender mailSender;
//
//    public void sendEmail(String to, String subject, String body) {
//        System.out.println("Attempting to send email to: " + to);
//
//        try {
//            SimpleMailMessage message = new SimpleMailMessage();
//            message.setTo(to);
//            message.setSubject(subject);
//            message.setText(body);
//            mailSender.send(message);
//            System.out.println("Email sent successfully!");
//        } catch (Exception e) {
//            System.out.println("Failed to send email!");
//            e.printStackTrace();
//        }
//    }
//}
