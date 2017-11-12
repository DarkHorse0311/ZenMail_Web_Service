package org.zen.zenmail.api.messages;


import org.springframework.stereotype.Service;
import org.zen.zenmail.model.user.User;

import javax.mail.*;
import java.util.Properties;

@Service
public class MessagesService {
    private static final String MAIL_SERVER_URL = "zenmail.space";
    private static final int MAIL_SERVER_IMAP_PORT = 993;
    private static final String MAIL_SERVER_SMTP_PORT = "587";
    private static final String INBOX_FOLDER_NAME ="INBOX"

    public Message[] getMessages(User user) {
        Session session = Session.getDefaultInstance(new Properties( ));
        Message []msg = {};
        try {
            Store store = session.getStore("imaps");
            store.connect(MAIL_SERVER_URL, MAIL_SERVER_IMAP_PORT, user.getName(), user.getPassword());
            Folder inbox = store.getFolder( INBOX_FOLDER_NAME );
            inbox.open( Folder.READ_ONLY );
            msg = inbox.getMessages();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return msg;
    }

    public void sendMessage(Message msg, User user){

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", MAIL_SERVER_URL);
        props.put("mail.smtp.port", MAIL_SERVER_SMTP_PORT);

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(user.getName(), user.getPassword());
                    }
                });
    }
}