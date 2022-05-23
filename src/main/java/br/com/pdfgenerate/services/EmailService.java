package br.com.pdfgenerate.services;

import java.io.ByteArrayOutputStream;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender javaMailSender;

	public void enviar(String nome, String emailDestinatario) {
		try {
			Email email = new SimpleEmail();
			email = emailAuthentication(email);

			email.setFrom("juanpapiro@gmail.com");
			email.setSubject("Testando envio de e-mail");
			email.setMsg("Olá " + nome + ", você recebeu um e-mail de teste.");
			email.addTo(emailDestinatario);

			email.send();
		} catch (EmailException e) {
			e.printStackTrace();
		}
	}

	public void sendForAttachment(String nome, String emailDestinatario, 
			String fileName, String contentType, ByteArrayOutputStream baos) {
		try {
						
//			//CRIA SESSSION
//			Session session = Session.getInstance(sessionConfig(), 
//					new DefaultAuthenticator("juanpapiro@gmail.com", "stbzomsmtgpmncou"));
//			
//			//INSTANCIA MIME_MESSAGE PARA RECEBER PARTES DO E-MAIL (CORPO E ANEXOS)
//			MimeMessage mimeMessage = new MimeMessage(session);
//			
//			//SETA REMETENTE, DESTINATÁRIO E ASSUNTO NO MIME_MESSAGE
//			mimeMessage.addFrom(new InternetAddress[] {new InternetAddress("juanpapiro@gmail.com")});
//			mimeMessage.setRecipients(Message.RecipientType.TO,
//					new InternetAddress[] {new InternetAddress("juanpapiro@hotmail.com")});
//			mimeMessage.setSubject("Email de teste com anexo.");
//
//			//GERA O CORPO DO E-MAIL
//			MimeBodyPart body = new MimeBodyPart();
//			body.setText(String.format("Olá %s, você recebeu um e-mail de teste.", nome));
			
			//CRIA MIME_MESSAGE E SETA REMETENTE, DESTINATÁRIO E ASSUNTO NO MIME_MESSAGE
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			mimeMessage.setRecipients(Message.RecipientType.TO, emailDestinatario);
			mimeMessage.setSubject("E-mail de teste");
			mimeMessage.setFrom("juanpapiro@hotmail.com");
			mimeMessage.setText(String.format("Olá %s, você recebeu um e-mail de teste.", nome));

			//GERA ANEXO A PARTIR DE BYTE_ARRAY_OUTPUT_STREAM
			ByteArrayDataSource bads = new ByteArrayDataSource(baos.toByteArray(), contentType);
			DataHandler dataHandler = new DataHandler(bads);
			MimeBodyPart attach = new MimeBodyPart();
			attach.setDataHandler(dataHandler);
			attach.setFileName(fileName);

			//GERA O MULTIPART COM CORPO DO E-MAIL E ANEXOS
			Multipart multipart = new MimeMultipart();
//			multipart.addBodyPart(body);
			multipart.addBodyPart(attach);

			//SETA MULTIPART NO MIME_MESSAGE
			mimeMessage.setContent(multipart);
			
			//ENVIA E-MAIL
			javaMailSender.send(mimeMessage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void send(String nome, String emailDestinatario) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setSubject("E-mail de teste");
		message.setText(String.format("Olá %s, você recebeu um e-mail de teste.", nome));
		message.setTo(emailDestinatario);
		message.setFrom("juanpapiro@gmail.com");

		try {
			javaMailSender.send(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Email emailAuthentication(Email email) {
		email.setHostName("smtp.gmail.com");
		email.setSmtpPort(587);
		email.setAuthenticator(new DefaultAuthenticator("juanpapiro@gmail.com", "stbzomsmtgpmncou"));
		email.setStartTLSRequired(true);
		email.setSSLOnConnect(true);
		return email;
	}

	private Properties sessionConfig() {
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", 587);
		props.put("mail.smtp.auth", "false");
		props.put("mail.debug", "true"); 
		props.put("mail.smtp.debug", "true");
		props.put("mail.mime.charset", "ISO-8859-1");
		props.put("mail.smtp.starttls.enable", "true");
		return props;
	}

}
