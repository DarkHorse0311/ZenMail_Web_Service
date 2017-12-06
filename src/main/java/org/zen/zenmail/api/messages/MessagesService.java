package org.zen.zenmail.api.messages;


import com.sun.mail.imap.IMAPMessage;
import org.springframework.stereotype.Service;
import org.zen.zenmail.model.user.User;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.Vector;

@Service
public class MessagesService {
    private static final String MAIL_SERVER_URL = "zenmail.space";
    private static final int MAIL_SERVER_IMAP_PORT = 993;
    private static final String MAIL_SERVER_SMTP_PORT = "587";
    private static final String INBOX_FOLDER_NAME = "INBOX";

    public Vector<IMAPMessage> getMessages(User user) {
        Session session = Session.getDefaultInstance(new Properties());
        Message[] msg = {};
        Vector<IMAPMessage> result = new Vector<>();
        try {
            Store store = session.getStore("imaps");
            store.connect(MAIL_SERVER_URL, MAIL_SERVER_IMAP_PORT, user.getName(), user.getPassword());
            Folder inbox = store.getFolder(INBOX_FOLDER_NAME);
            inbox.open(Folder.READ_ONLY);
            msg = inbox.getMessages();
            for (Message message :
                    msg) {
                result.add((IMAPMessage) message);
            }
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void sendMessage(String addressTo, String subject, String text, User user) throws MessagingException {

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
        Message msg = new MimeMessage(session);
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(addressTo));
        msg.setFrom(new InternetAddress(user.getName()));
        msg.setSubject(subject);
        msg.setText(text);
        Transport.send(msg);
    }

    public IMAPMessage getMsgById(String id, User user) {
        Session session = Session.getDefaultInstance(new Properties());
        Message[] msg = {};
        Vector<IMAPMessage> result = new Vector<>();
        try {
            Store store = session.getStore("imaps");
            store.connect(MAIL_SERVER_URL, MAIL_SERVER_IMAP_PORT, user.getName(), user.getPassword());
            Folder inbox = store.getFolder(INBOX_FOLDER_NAME);
            inbox.open(Folder.READ_ONLY);
            msg = inbox.getMessages();
            for (int i = 0; i < msg.length; ++i) {
                if (((IMAPMessage) msg[i]).getMessageID() != null && ((IMAPMessage) msg[i]).getMessageID().replace("<", "")
                        .replace(">", "").replace(".space", "").equals(id.replace("<", "")
                                .replace(">", ""))) {
                    return ((IMAPMessage) msg[i]);
                }
            }
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return null;
    }
}