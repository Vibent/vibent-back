package com.vibent.vibentback.common.mail;

import com.vibent.vibentback.common.util.TokenUtils;
import com.vibent.vibentback.distributionlist.DistributionList;
import com.vibent.vibentback.event.Event;
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

import java.util.Collection;
import java.util.Set;

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
    @Autowired
    private MailTaskExecutor mailTaskExecutor;

    public void sendEventInviteMail(User inviter, Event event, Set<String> recipients) {
        String secret = tokenUtils.getEventInviteToken(event.getId());
        Context context = new Context();
        context.setVariable("inviterFirstName", inviter.getFirstName());
        context.setVariable("inviterLastName", inviter.getLastName());
        context.setVariable("eventTitle", event.getTitle());
        context.setVariable("secret", secret);
        context.setVariable("clientUrl", CLIENT_URL);

        String content = templateEngine.process("eventInviteTemplate", context);
        prepareAndSend(recipients, "Vibent Event Invitation", content);
    }

    public void sendDistributionListInviteMail(User inviter, DistributionList distributionList, Set<String> recipients) {
        String secret = tokenUtils.getDistributionListInviteToken(distributionList.getId());
        Event exampleEvent = distributionList.getEvents().iterator().next();
        Context context = new Context();
        context.setVariable("inviterFirstName", inviter.getFirstName());
        context.setVariable("inviterLastName", inviter.getLastName());
        context.setVariable("distributionListTitle", distributionList.getTitle());
        context.setVariable("eventTitle", exampleEvent.getTitle());
        context.setVariable("secret", secret);
        context.setVariable("clientUrl", CLIENT_URL);

        String content = templateEngine.process("distributionListInviteTemplate", context);
        prepareAndSend(recipients, "Vibent Distribution List Invitation", content);
    }

    public void sendEventInviteByDistributionListNotification(User inviter, DistributionList distributionList, Event event, Set<User> recipients) {
        Context context = new Context();
        context.setVariable("inviterFirstName", inviter.getFirstName());
        context.setVariable("inviterLastName", inviter.getLastName());
        context.setVariable("distributionListTitle", distributionList.getTitle());
        context.setVariable("eventTitle", event.getTitle());
        context.setVariable("clientUrl", CLIENT_URL);

        String content = templateEngine.process("eventInviteByDistributionListNotification", context);
        prepareAndSend(recipients, "Vibent Event Invitation", content);
    }

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

    public void sendPasswordResetMail(User user) {
        String secret = tokenUtils.getPasswordResetToken(user.getId());
        Context context = new Context();
        context.setVariable("firstName", user.getFirstName());
        context.setVariable("lastName", user.getLastName());
        context.setVariable("secret", secret);
        context.setVariable("clientUrl", CLIENT_URL);

        String content = templateEngine.process("passwordResetTemplate", context);
        prepareAndSend(user.getEmail(), "Vibent Password Reset", content);
    }


    private void prepareAndSend(Collection<User> recipients, String subject, String content) {
        for (User recipient : recipients) {
            prepareAndSend(recipient.getEmail(), subject, content);
        }
    }

    private void prepareAndSend(Iterable<String> recipientEmails, String subject, String content) {
        for (String recipientEmail : recipientEmails) {
            prepareAndSend(recipientEmail, subject, content);
        }
    }

    private void prepareAndSend(String recipientEmail, String subject, String content) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("Vibent");
            messageHelper.setTo(recipientEmail);
            messageHelper.setSubject(subject);
            messageHelper.setText(content, true);
        };

        mailTaskExecutor.execute(() -> {
            try {
                mailSender.send(messagePreparator);
            } catch (Exception e) {
                log.error("Failed sending email to {} : {}", recipientEmail, e.getMessage());
            }
        });
    }
}
