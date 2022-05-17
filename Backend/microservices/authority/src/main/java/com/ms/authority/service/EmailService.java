package com.ms.authority.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import java.io.File;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender emailSender;
    private final SpringTemplateEngine templateEngine;
    @Value("${spring.mail.username}")
    private String from;

    @Async
    public void sendActivationLink(String email, String name, String link, String qrCode)
            throws IllegalStateException, MessagingException {
        final Context ctx = new Context();
        ctx.setVariable("name", name);
        ctx.setVariable("link", link);
        ctx.setVariable("qrCode", qrCode);
        //TODO: replace thymeleaf to something else
        final String body = this.templateEngine.process("emailConfirmation.html", ctx);
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        helper.setFrom(from);
        helper.setTo(email);
        helper.setText(body, true);
        helper.setSubject("Confirm your email");
        emailSender.send(mimeMessage);
    }

}