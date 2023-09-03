package lapicito.backend.service;

import lapicito.backend.dto.MailDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

@Service
public class MailService implements IMailservice {

    @Autowired
    JavaMailSender javaMailSender;


    @Override
    public void enviarMailBienvenida(String to, String userName) throws MessagingException, UnsupportedEncodingException {

       /* SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(to);
        simpleMailMessage.setFrom("noreply.lapicito@gmail.com");
        simpleMailMessage.setSubject("Bienvenido!!!");
        simpleMailMessage.setText("bienvenido a lapicito");

        javaMailSender.send(simpleMailMessage);
        */
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        String contenido = "    <div style=\"width: auto; height: 200px; text-align: center;\">\n" +
                "        <img src=\"https://i.postimg.cc/Xq2C9p41/logo2-0.png\" alt=\"\">\n" +
                "    </div>\n" +
                "    <div style=\"text-align: center; width: auto; height: 50px; background-color: #51bfa6;\">\n" +
                "        <h1 style=\"font-family: 'Gill Sans', 'Gill Sans MT', Calibri, 'Trebuchet MS', sans-serif;\">Bienvenido <strong>"+ userName +"</strong> !!!</h1>\n" +
                "    </div>\n" +
                "    <div style=\"text-align: center; width: auto; height: 100px;\">\n" +
                "        <h2 style=\"font-family: 'Gill Sans', 'Gill Sans MT', Calibri, 'Trebuchet MS', sans-serif;\">Tu usuario ya fue registrado.</h2>\n" +
                "        <h2 style=\"font-family: 'Gill Sans', 'Gill Sans MT', Calibri, 'Trebuchet MS', sans-serif;\">Es momento de que empieces a disfrutar de nuestra plataforma</h2>\n" +
                "    </div>\n" +
                "    <div style=\"width: auto; height: 200px; text-align: center;\">\n" +
                "        <img src=\"https://i.postimg.cc/L6PVcFBq/women.png\" alt=\"\">\n" +
                "    </div>";

        helper.setFrom("noreply.lapicito@gmail.com", "Lapicito");
        helper.setTo(to);
        helper.setSubject("BIENVENIDO A LAPICITO.");
        helper.setText(contenido, true);

        javaMailSender.send(message);

    }
}
