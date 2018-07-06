package com.vibent.vibentback.common.mail;

import com.vibent.vibentback.common.util.TokenUtils;
import com.vibent.vibentback.user.User;
import lombok.AllArgsConstructor;
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

    public void sendConfirmationMail(User user){
        String token = tokenUtils.createConfirmEmailToken(user.getEmail());

        Context context = new Context();
        context.setVariable("firstName", user.getFirstName());
        context.setVariable("lastName", user.getLastName());
        context.setVariable("token", token);
        context.setVariable("clientUrl", CLIENT_URL);


        String content = templateEngine.process("mailConfirmEmailTemplate", context);
        prepareAndSend(user.getEmail(),"Vibent Registration Confirmation", content);
    }

    private void prepareAndSend(String recipient, String subject, String content) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("Vibent");
            messageHelper.setTo(recipient);
            messageHelper.setSubject(subject);
            messageHelper.setText(content, true);
        };

        mailSender.send(messagePreparator);
    }

}
