package com.vibent.vibentback.common.mail;

import com.vibent.vibentback.common.util.TokenUtils;
import com.vibent.vibentback.user.User;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MailService {

    @Value("${vibent.client.url}")
    private String CLIENT_URL;

    @NonNull
    private TokenUtils tokenUtils;
    @NonNull
    private TemplateEngine templateEngine;
    @NonNull
    private JavaMailSender mailSender;

    public void sendChangeEmailConfirmationMail(User user, String newEmail) {
        String secret = tokenUtils.getEmailConfirmToken(user.getId(), newEmail);
        Context context = new Context();
        context.setVariable("firstName", user.getFirstName());
        context.setVariable("lastName", user.getLastName());
        context.setVariable("secret", secret);
        context.setVariable("clientUrl", CLIENT_URL);

        String content = templateEngine.process("changeMailConfirmTemplate", context);
        prepareAndSend(newEmail, "Vibent Account Email Change Confirmation", content);
    }

    public void sendConfirmationMail(User user) {
        String secret = tokenUtils.getEmailConfirmToken(user.getId(), user.getEmail());
        Context context = new Context();
        context.setVariable("firstName", user.getFirstName());
        context.setVariable("lastName", user.getLastName());
        context.setVariable("secret", secret);
        context.setVariable("clientUrl", CLIENT_URL);

        String content = templateEngine.process("mailConfirmTemplate", context);
        prepareAndSend(user.getEmail(), "Vibent Registration Confirmation", content);
    }

    private void prepareAndSend(String recipient, String subject, String content) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("Vibent");
            messageHelper.setTo(recipient);
            messageHelper.setSubject(subject);
            messageHelper.setText(content, true);
        };

        try {
            mailSender.send(messagePreparator);
        } catch (Exception e) {
            log.error("Failed sending account confirmation email, user must request a new one");
        }
    }

}
