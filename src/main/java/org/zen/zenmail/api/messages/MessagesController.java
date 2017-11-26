package org.zen.zenmail.api.messages;


import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.zen.zenmail.api.helpers.AuthHelper;
import org.zen.zenmail.model.messages.MessagesResponse;
import org.zen.zenmail.model.response.OperationResponse;
import org.zen.zenmail.model.user.User;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Vector;

@RestController
public class MessagesController {

    @Autowired
    private MessagesService messagesService;

    @RequestMapping(value = "/messages/inbox", method = RequestMethod.GET, produces = {"application/json"})
    public MessagesResponse getUserInformation( HttpServletRequest req, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(!AuthHelper.isLoggined(auth)){
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return null;
        }
        String name = auth.getName();
        String password = (String) auth.getCredentials();
        User user = new User();
        user.setName(name);
        user.setPassword(password);
        MessagesResponse res = new MessagesResponse();
        Vector<String> resVector = new Vector<>();
        try {
            Vector<String > messages = messagesService.getMessages(user);
            for (String m:
                 messages) {
                resVector.add(m);
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
    public void sendMessage(HttpServletRequest request, HttpServletResponse response){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(!AuthHelper.isLoggined(auth)){
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
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

            messagesService.sendMessage(addressTo,subject,rawMsg,user);
        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } catch (MessagingException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }catch (JSONException e){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

}