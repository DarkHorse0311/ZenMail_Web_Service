package org.zen.zenmail.api.messages;


import com.sun.mail.imap.IMAPMessage;
import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.zen.zenmail.api.helpers.AuthHelper;
import org.zen.zenmail.model.messages.MessagesResponse;
import org.zen.zenmail.model.messages.InboxMessage;
import org.zen.zenmail.model.messages.SendMessageResponse;
import org.zen.zenmail.model.response.OperationResponse;
import org.zen.zenmail.model.user.User;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMultipart;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Vector;

@RestController
public class MessagesController {

    @Autowired
    private MessagesService messagesService;

    @RequestMapping(value = "/messages/inbox", method = RequestMethod.GET, produces = {"application/json"})
    public MessagesResponse getUserInformation(HttpServletRequest req, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!AuthHelper.isLoggined(auth)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return null;
        }

        String name = auth.getName();
        String password = (String) auth.getCredentials();
        User user = new User();
        user.setName(name);
        user.setPassword(password);
        Vector<InboxMessage> resVector = new Vector<>();
        MessagesResponse res = new MessagesResponse();

        try {
            Vector<IMAPMessage> messages = messagesService.getMessages(user);
            for (IMAPMessage m :
                    messages) {
                InboxMessage inboxMessage = new InboxMessage();
                if (m.getMessageID() == null) {
                    continue;
                }
                inboxMessage.id = m.getMessageID();
                inboxMessage.msg = getTextFromMessage(m);
                inboxMessage.time = m.getReceivedDate().toString();
                inboxMessage.subject = m.getSubject().toString();
                inboxMessage.from = ((InternetAddress) m.getFrom()[0]).getAddress();
                resVector.add(inboxMessage);
            }
            res.setOperationStatus(OperationResponse.ResponseStatusEnum.SUCCESS);
            res.setOperationMessage("ok");
            res.setMsgs(resVector);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getStackTrace());
            res.setOperationStatus(OperationResponse.ResponseStatusEnum.ERROR);
            res.setOperationMessage("ok");
        }
        return res;
    }

    @RequestMapping(value = "/messages", method = RequestMethod.POST, produces = {"application/json"})
    public SendMessageResponse sendMessage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        SendMessageResponse returnValue = new SendMessageResponse();
        if (!AuthHelper.isLoggined(auth)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            returnValue.setOperationMessage("auth failed!");
            returnValue.setOperationStatus(OperationResponse.ResponseStatusEnum.SUCCESS);
            return returnValue;
        }
        String name = auth.getName();
        String password = (String) auth.getCredentials();
        User user = new User();
        user.setName(name);
        user.setPassword(password);

        try {
            String jsonString = IOUtils.toString(request.getInputStream(), "UTF-8");
            JSONObject msgJson = new JSONObject(jsonString);

            String rawMsg = msgJson.getString("msg");
            String addressTo = msgJson.getString("to");
            String subject = msgJson.getString("subject");

            messagesService.sendMessage(addressTo, subject, rawMsg, user);
            returnValue.setOperationMessage("ok");
            returnValue.setOperationStatus(OperationResponse.ResponseStatusEnum.SUCCESS);
            return returnValue;
        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            returnValue.setOperationMessage(e.getMessage());
        } catch (MessagingException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            returnValue.setOperationMessage(e.getMessage());
        } catch (JSONException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            returnValue.setOperationMessage(e.getMessage());
        }
        returnValue.setOperationStatus(OperationResponse.ResponseStatusEnum.ERROR);
        return returnValue;
    }

    @RequestMapping(value = "/messages/inbox/{id}", method = RequestMethod.GET, produces = {"application/json"})
    public MessagesResponse getUserInformation(@PathVariable String id, HttpServletRequest req, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!AuthHelper.isLoggined(auth)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return null;
        }

        String name = auth.getName();
        String password = (String) auth.getCredentials();
        User user = new User();
        user.setName(name);
        user.setPassword(password);
        Vector<InboxMessage> resVector = new Vector<>();
        MessagesResponse res = new MessagesResponse();
        if (id != null) {
            IMAPMessage m = messagesService.getMsgById(id, user);
            try {
                InboxMessage inboxMessage = new InboxMessage();
                inboxMessage.id = m.getMessageID();
                inboxMessage.msg = getTextFromMessage(m);
                inboxMessage.time = m.getReceivedDate().toString();
                inboxMessage.subject = m.getSubject();
                inboxMessage.from = ((InternetAddress) m.getFrom()[0]).getAddress();
                res.setOperationStatus(OperationResponse.ResponseStatusEnum.SUCCESS);
                resVector.add(inboxMessage);
                res.setOperationMessage("ok");
                res.setMsgs(resVector);
                return res;
            } catch (MessagingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private String getTextFromMessage(Message message) throws MessagingException, IOException {
        String result = "";
        if (message.isMimeType("text/plain")) {
            result = message.getContent().toString();
        } else if (message.isMimeType("multipart/*")) {
            MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
            result = getTextFromMimeMultipart(mimeMultipart);
        }
        return result;
    }

    private String getTextFromMimeMultipart(
            MimeMultipart mimeMultipart) throws MessagingException, IOException {
        String result = "";
        int count = mimeMultipart.getCount();
        for (int i = 0; i < count; i++) {
            BodyPart bodyPart = mimeMultipart.getBodyPart(i);
            if (bodyPart.isMimeType("text/plain")) {
                result = result + "\n" + bodyPart.getContent();
                break; // without break same text appears twice in my tests
            } else if (bodyPart.isMimeType("text/html")) {
                String html = (String) bodyPart.getContent();
                //result = result + "\n" + org.jsoup.Jsoup.parse(html).text();
                result = html;
            } else if (bodyPart.getContent() instanceof MimeMultipart) {
                result = result + getTextFromMimeMultipart((MimeMultipart) bodyPart.getContent());
            }
        }
        return result;
    }
}