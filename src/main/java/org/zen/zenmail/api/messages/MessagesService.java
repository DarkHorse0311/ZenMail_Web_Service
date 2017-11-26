package org.zen.zenmail.api.messages;


import org.springframework.stereotype.Service;
import org.zen.zenmail.model.user.User;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Properties;
import java.util.Vector;

@Service
public class MessagesService {
    private static final String MAIL_SERVER_URL = "zenmail.space";
    private static final int MAIL_SERVER_IMAP_PORT = 993;
    private static final String MAIL_SERVER_SMTP_PORT = "587";
    private static final String INBOX_FOLDER_NAME = "INBOX";

    public Vector<String> getMessages(User user) {
        Session session = Session.getDefaultInstance(new Properties());
        Message[] msg = {};
        Vector<String> result = new Vector<>();
        try {
            Store store = session.getStore("imaps");
            store.connect(MAIL_SERVER_URL, MAIL_SERVER_IMAP_PORT, user.getName(), user.getPassword());
            Folder inbox = store.getFolder(INBOX_FOLDER_NAME);
            inbox.open(Folder.READ_ONLY);
            msg = inbox.getMessages();
            for (Message message :
                    msg) {
                result.add(getText(message));
            }
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void sendMessage(String addressTo,String subject,String text, User user) throws MessagingException {

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

    private boolean textIsHtml = false;

    private String getText(Part p) throws
            MessagingException, IOException {
        if (p.isMimeType("text/*")) {
            String s = (String) p.getContent();
            textIsHtml = p.isMimeType("text/html");
            return s;
        }

        if (p.isMimeType("multipart/alternative")) {
            // prefer html text over plain text
            Multipart mp = (Multipart) p.getContent();
            String text = null;
            for (int i = 0; i < mp.getCount(); i++) {
                Part bp = mp.getBodyPart(i);
                if (bp.isMimeType("text/plain")) {
                    if (text == null)
                        text = getText(bp);
                    continue;
                } else if (bp.isMimeType("text/html")) {
                    String s = getText(bp);
                    if (s != null)
                        return s;
                } else {
                    return getText(bp);
                }
            }
            return text;
        } else if (p.isMimeType("multipart/*")) {
            Multipart mp = (Multipart) p.getContent();
            for (int i = 0; i < mp.getCount(); i++) {
                String s = getText(mp.getBodyPart(i));
                if (s != null)
                    return s;
            }
        }

        return null;
    }
}