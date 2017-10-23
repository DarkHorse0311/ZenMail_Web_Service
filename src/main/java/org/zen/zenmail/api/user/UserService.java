package org.zen.zenmail.api.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.zen.zenmail.model.user.User;
import org.zen.zenmail.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

    public String getLoggedInUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return "nosession";
        }
        return auth.getName();
    }


    public User getLoggedInUser() {
        String loggedInUserId = this.getLoggedInUserId();
        System.out.format("\n1. Inside >> getLoggedInUser: %s", loggedInUserId);
        User user = this.getUserInfoByUserId(loggedInUserId);
        System.out.format("\n2. After Find User: %s", loggedInUserId);
        return user;
    }

    public User getUserInfoByUserId(String userId) {
        User user = this.userRepo.findOneByUserId(userId).orElseGet(() -> new User());
        return user;
    }

    public boolean insertOrSaveUser(User user) {
        this.userRepo.save(user);
        return true;
    }

    public boolean addNewUser(User user) {
        if(userRepo.findOneByUserId(user.getUserId()).isPresent()){
            return false;
        }
        else{
            return this.insertOrSaveUser(user);
        }
    }

}
