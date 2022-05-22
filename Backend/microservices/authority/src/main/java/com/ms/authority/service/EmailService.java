package com.ms.authority.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.xml.bind.DatatypeConverter;

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
        String data = qrCode.substring(qrCode.indexOf("base64,") + 7);
        byte[] bytes = DatatypeConverter.parseBase64Binary(data);
        final String body = this.templateEngine.process("emailConfirmation.html", ctx);
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "utf-8");
        helper.setFrom(from);
        helper.setTo(email);
        helper.setText(body, true);
        helper.addInline("qrCode", new ByteArrayResource(bytes), "image/png");
        helper.setSubject("Confirm your email");
        emailSender.send(mimeMessage);
    }

}