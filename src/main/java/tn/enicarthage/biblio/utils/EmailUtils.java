package tn.enicarthage.biblio.utils;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailUtils {
	
	@Autowired
	private JavaMailSender emailSender;
	
	public void sendSimpleMessage(String to,String subject,String text, List<String> list) throws MailAuthenticationException {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("amel.benyakhlef@gmail.com");
		message.setTo(to);
		message.setSubject(subject);
		message.setText(text);
		if (list !=null && list.size()>0)
			message.setCc(getCcArray(list));
		
		//send the message final
		emailSender.send(message);
	}
	
	
	
	//the method to get the list of Cc in the email
	private String[] getCcArray(List<String> cclist) {
		String[] cc =new String[cclist.size()];
		for (int i=0; i<cclist.size();i++) {
			cc[i]=cclist.get(i);
		}
		
		return cc;
	}
}
