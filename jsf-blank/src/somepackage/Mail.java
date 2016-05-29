package somepackage;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class Mail {
private String passwd="Core2duo";
private String username="zoheirdoudou29@gmail.com";
private String email;
private String name;
private String subject;


public String getUsername() {
	return username;
}



public String getPasswd() {
	return passwd;
}



public void setPasswd(String passwd) {
	this.passwd = passwd;
}



public String getEmail() {
return email;
}



public void setEmail(String email) {
this.email = email;
}



public String getSubject() {
return subject;
}



public void setSubject(String subject) {
this.subject = subject;
}


public String send(){

Properties props = new Properties();


props.put("mail.smtp.auth", "true");
props.put("mail.smtp.starttls.enable","true");
props.put("mail.smtp.host","smtp.gmail.com" );
props.put("mail.smtp.port", "587");

/* props.setProperty("mail.smtp.port", "25");

 * *props.setProperty("mail.host", "ASPMX.L.GOOGLE.COM");
props.setProperty("mail.transport.protocol", "smtp");

props.setProperty("mail.user", "zoheirdoudou29@gmail.com");
props.setProperty("mail.password", "Core2duo");
*/
Session mailSession = Session.getInstance(props, new javax.mail.Authenticator() {
    protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
        return new javax.mail.PasswordAuthentication(username, "Core2duo");
    }
});
//mailSession.setDebug(true);

try {
//transport = mailSession.getTransport();


MimeMessage message = new MimeMessage(mailSession);
message.setSubject("HTML mail with images");
message.setFrom(new InternetAddress("zoheirdoudou29@gmail.com"));
message.setContent("<h1>"+subject+"</h1> " +
"<h3>"+email+"</h3> " +
"<h3>"+name+"</h3> " +
"<h5>"+passwd+"</h5>" , "text/html");
message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
//message.addRecipient(Message.RecipientType.TO,
//new InternetAddress(email));
//transport.connect();
Transport.send(message);
//message.getRecipients(Message.RecipientType.TO));
//transport.close();
} catch (NoSuchProviderException e) {
// TODO Auto-generated catch block
e.printStackTrace();
} catch (MessagingException e) {
// TODO Auto-generated catch block
e.printStackTrace();
}
return "ok";
}



public void setName(String name) {
this.name = name;
}

public String getName() {
return name;
}
} 