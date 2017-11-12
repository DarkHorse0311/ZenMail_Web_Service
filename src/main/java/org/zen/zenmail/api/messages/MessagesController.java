package org.zen.zenmail.api.messages;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.zen.zenmail.api.user.UserService;
import org.zen.zenmail.model.messages.MessagesResponse;
import org.zen.zenmail.model.response.OperationResponse;
import org.zen.zenmail.model.user.User;
import org.zen.zenmail.model.user.UserResponse;

import javax.mail.Message;
import javax.servlet.http.HttpServletRequest;
import java.util.Vector;

@RestController
public class MessagesController {

    @Autowired
    private MessagesService messagesService;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/messages/inbox", method = RequestMethod.GET, produces = {"application/json"})
    public MessagesResponse getUserInformation( HttpServletRequest req) {
        String userId = userService.getLoggedInUserId();
        User user = userService.getUserInfoByUserId(userId);
        user.setName("suodio@zenmail.space");
        user.setPassword("tipidor");
        MessagesResponse res = new MessagesResponse();
        Vector<String> resVector = new Vector<>();
        try {
            Message[]msg = messagesService.getMessages(user);
            for (Message m:
                 msg) {
                resVector.add(m.getContent().toString());
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

    @RequestMapping(value = "/messages",method = RequestMethod.POST,produces = {"application/json"}){
        String userId = userService.getLoggedInUserId();
        User user = userService.getUserInfoByUserId(userId);
        user.setName("suodio@zenmail.space");
        user.setPassword("tipidor");


    }

}