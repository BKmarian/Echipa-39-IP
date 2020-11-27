package com.ensat.configuration;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Configurable
public class SmtpMailSender {

    final static String USERNAME = "cosminmanolescudans@gmail.com";

    final static String PASSWORD = "gabtudor";

    @Bean
    public JavaMailSenderImpl javaMailSenderImpl() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        //Set gmail email id
        mailSender.setUsername(USERNAME);
        //Set gmail email password
        mailSender.setPassword(PASSWORD);
        Properties prop = mailSender.getJavaMailProperties();
        prop.put("mail.transport.protocol", "smtp");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.debug", "true");
        return mailSender;
    }

    public static void sendMail(String email, String subject, String text) throws MessagingException {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(SmtpMailSender.class);
        ctx.refresh();
        JavaMailSenderImpl mailSender = ctx.getBean(JavaMailSenderImpl.class);
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mailMsg = new MimeMessageHelper(mimeMessage);
        mailMsg.setFrom(USERNAME);
        mailMsg.setTo(email);
        mailMsg.setSubject(subject);
        mailMsg.setText(text);
        mailSender.send(mimeMessage);
        System.out.println("---Done---");
    }
}