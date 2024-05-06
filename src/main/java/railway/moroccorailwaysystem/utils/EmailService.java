package railway.moroccorailwaysystem.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class EmailService {

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.url}")
    private String BASE_URL;
    @Value("${spring.mail.sender}")
    private String MAIL_SENDER;
    @Value("${spring.mail.confirm-email.subject}")
    private String CONFIRM_MAIL_SUBJECT;
    @Value("${spring.mail.confirm-email.content}")
    private String CONFIRM_MAIL_CONTENT;

    @Value("${spring.mail.forgot-pass.subject}")
    private String FORGOT_PASS_SUBJECT;
    @Value("${spring.mail.forgot-pass.content}")
    private String FORGOT_PASS_CONTENT;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendConfirmationMail(String to, final String token) {
        String MAIL_CONTENT = CONFIRM_MAIL_CONTENT + "\r\n" + buildURL(token, "/confirm-email");
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom(MAIL_SENDER);
        mail.setTo(to);
        mail.setSubject(CONFIRM_MAIL_SUBJECT);
        mail.setText(MAIL_CONTENT);

        javaMailSender.send(mail);
    }

    public void sendForgotPasswordMail(final String to, final String token) {
        String MAIL_CONTENT = FORGOT_PASS_CONTENT + "\r\n" + buildURL(token, "/forgot-password");
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom(MAIL_SENDER);
        mail.setTo(to);
        mail.setSubject(FORGOT_PASS_SUBJECT);
        mail.setText(MAIL_CONTENT);

        javaMailSender.send(mail);
    }

    public String buildURL(final String token, final String TARGET_URL) {
        return UriComponentsBuilder.fromHttpUrl(BASE_URL)
                .path(TARGET_URL).queryParam("token", token)
                .toUriString();
    }
}
