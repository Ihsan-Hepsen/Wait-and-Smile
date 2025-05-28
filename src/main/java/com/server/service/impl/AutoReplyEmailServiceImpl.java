package com.server.service.impl;

import com.server.service.AutoReplyEmailService;
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
                Hi there! 👋
                
                We're thrilled you joined our waitlist. You're #36 in line. We'll keep you updated every step of the way!
                
                Cheers,
                EPPIC Inc.
                """;
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(receiverEmail);
        message.setSubject("Thank you! We'll let you know.");
        message.setText(body);
        message.setFrom("info@vibio.co");

        mailSender.send(message);
    }

}
