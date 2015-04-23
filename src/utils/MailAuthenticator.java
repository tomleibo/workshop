package utils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailAuthenticator {

    private String host;
    private String username;
    private String password;

    public MailAuthenticator(String host, String username, String password) {
        this.host = host;
        this.username = username;
        this.password = password;
    }

    public void sendVerificationMail(String toAddressStr, String name){
        try {
            String from = username;
            String pass = password;
            //Setting mail properties
            Properties props = System.getProperties();
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.user", from);
            props.put("mail.smtp.password", pass);
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.auth", "true");
            //props.put("mail.debug", "true");
            //setting the session to be gmail.
            Session session = Session.getInstance(props, new GMailAuthenticator(username, password));
            MimeMessage message = new MimeMessage(session);
            Address fromAddress = new InternetAddress(from);
            Address toAddress = new InternetAddress(toAddressStr);
            //setting message properties
            message.setFrom(fromAddress);
            message.setRecipient(Message.RecipientType.TO, toAddress);
            message.setSubject(name + ", verify your account");
            message.setText("Hi " + name + ",\n Please verify your account by replying this mail!\n Greetings,\n Yuval, Shai, Tom, Roee and Hadar");
            Transport transport = session.getTransport("smtp");
            transport.connect(host, from, pass);
            message.saveChanges();
            //Sending message
            Transport.send(message);
            transport.close();
        }catch(Exception ex){
            System.err.println(ex.getMessage());
        }
    }

    public boolean authorizedMailIncome(String mailAddress){
        System.out.println("enter!");
        long start = System.currentTimeMillis() / 1000;
        while(((System.currentTimeMillis()/1000) - start) < 600) {
            Properties props2=System.getProperties();
            props2.setProperty("mail.store.protocol", "imaps");
            Session session2=Session.getDefaultInstance(props2, null);
            try {
                Store store = session2.getStore("imaps");
                store.connect(host, username, password);
                Folder folder = store.getFolder("INBOX");//get inbox
                folder.open(Folder.READ_ONLY);//open folder only to read
                Message message[] = folder.getMessages();
                for (int i = 0; i < message.length; i++) {
                    //print subjects of all mails in the inbox
                    Address[] a = message[i].getAllRecipients();
                    for (int j = 0; j < a.length; j++) {
                        System.out.println("out!");
                        return true;
                    }
                }
                //close connections
                folder.close(true);
                store.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
