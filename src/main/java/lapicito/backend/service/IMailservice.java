package lapicito.backend.service;

import lapicito.backend.dto.MailDto;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

public interface IMailservice {

    public void enviarMailBienvenida(String to, String userName) throws MessagingException, UnsupportedEncodingException;

}
