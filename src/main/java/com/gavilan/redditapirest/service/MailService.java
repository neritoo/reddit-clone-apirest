package com.gavilan.redditapirest.service;

import com.gavilan.redditapirest.exception.SpringRedditException;
import com.gavilan.redditapirest.model.NotificationEmail;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class  MailService {

    private final JavaMailSender mailSender;
    private final MailContentBuilder mailContentBuilder;

    @Async
    public void sendMail(NotificationEmail notificationEmail) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            // messageHelper.setFrom("redditclonesender-598aee@inbox.mailtrap.io");
            messageHelper.setFrom("gavilanemailsender@gmail.com");
            messageHelper.setTo(notificationEmail.getRecipient());
            messageHelper.setSubject(notificationEmail.getSubject());
            messageHelper.setText(mailContentBuilder.build(notificationEmail.getBody()), true);
        };

        try {
            mailSender.send(messagePreparator);
            log.info("Email sent!");
        } catch (MailException e) {
            throw new SpringRedditException("Exception ocurred when sending email to " + notificationEmail.getRecipient());
        }
    }
}
