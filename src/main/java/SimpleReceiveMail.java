import javax.mail.*;
import java.io.IOException;
import java.util.Properties;

public class SimpleReceiveMail {

    public static void receiveEmail(final String userName,final String password){
        try {
            Properties properties = new Properties();
            properties.put("mail.store.protocol","imaps");

            Session session = Session.getDefaultInstance(properties, new Authenticator() {

                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(userName,password);
                }
            });

            Store store = session.getStore("imaps");
            store.connect("imap.gmail.com","***********@gmail.com","*************");

            Folder folder = store.getFolder("INBOX");       // Gelen kutusu
            folder.open(Folder.READ_ONLY);

            Message[] messages = folder.getMessages();
            for (int i = 0; i < messages.length ; i++) {
                Message message = messages[i];
                System.out.println("-------------------------");
                System.out.println("Email Number : " + (i+1));
                System.out.println("Subject :" + message.getSubject());
                System.out.println("From :" + message.getFrom()[0]);
                System.out.println("Text :" + message.getContent().toString());
            }
            folder.close();
            store.close();
        } catch (MessagingException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        final String userName = "***********@gmail.com";
        final String password = "*************";
        receiveEmail(userName,password);
    }
}
