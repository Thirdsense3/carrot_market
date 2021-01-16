package CarrotMarket.CarrotMarket.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;


public class MailSender {

    public void sendMail(String Email, StringBuffer Message, JavaMailSender javaMailSender) throws MessagingException, IOException {

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setSubject("Carrot Market 이메일 인증");
        helper.setTo(Email);
        // 발신자 변경 제한
        //helper.setFrom("CarrotMarket");
        helper.setText("인증번호 : "+Message.toString());
        javaMailSender.send(message);
    }
}