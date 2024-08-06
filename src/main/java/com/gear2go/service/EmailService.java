package com.gear2go.service;


import com.gear2go.domain.Mail;
import com.gear2go.exception.FailedToSendEmailException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final MailCreatorService mailCreatorService;

    public void send(final Mail mail) throws FailedToSendEmailException{
        log.info("Starting email preparation...");
        try {
            MimeMessagePreparator mailMessage = createMimeMessage(mail);
            javaMailSender.send(mailMessage);
            log.info("Email has been sent.");
        } catch (MailException e) {
            log.error("Failed to process email sending: " + e.getMessage(), e);
            throw new FailedToSendEmailException();
        }
    }


    private MimeMessagePreparator createMimeMessage(final Mail mail) {
        return mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(mail.mailTo());
            messageHelper.setSubject(mail.subject());
            messageHelper.setText(mailCreatorService.buildForgotPasswordMail(mail.message()), true);
        };
    }


    private SimpleMailMessage createMailMessage(final Mail mail) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(mail.mailTo());
        mailMessage.setSubject(mail.subject());
        mailMessage.setText(mailCreatorService.buildForgotPasswordMail(mail.message()));
        return mailMessage;
    }
}