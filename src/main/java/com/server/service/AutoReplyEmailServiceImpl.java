package com.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class AutoReplyEmailServiceImpl implements AutoReplyEmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void sendWelcomeEmail(String receiverEmail) {
        String body = """
                Hi there,
                
                Thank you for joining! We'll keep you updated with product updates and you'll be the first to learn when we launch it!
                
                Best wishes,
                The Team
                """;
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(receiverEmail);
        message.setSubject("Thank you! We'll let you know.");
        message.setText(body);
        message.setFrom("info@vibio.co");

        mailSender.send(message);
    }

}
