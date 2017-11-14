package org.zen.zenmail.api.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.zen.zenmail.crypt.Hash;
import org.zen.zenmail.model.user.User;
import org.zen.zenmail.repository.UserRepository;

import java.util.Base64;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

    public String getSaltFromDB(String username){
        StringBuilder salt = new StringBuilder("");
        if(userRepo.findOneByUsername(username).isPresent()){
            User user = userRepo.findOneByUsername(username).get();
            String hashPassword = user.getPassword();
            for(int i = 43; i < hashPassword.length(); ++i){
                salt.append(hashPassword.charAt(i));
            }
        }
        return Base64.getDecoder().decode(salt.toString()).toString();
    }

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

    public User getUserInfoByUserId(String username) {
        User user = this.userRepo.findOneByUsername(username).orElseGet(() -> new User());
        return user;
    }

    public boolean insertOrSaveUser(User user) {
        this.userRepo.save(user);
        return true;
    }

    public boolean addNewUser(User user) {
        if(userRepo.findOneByUsername(user.getUsername()).isPresent()){
            return false;
        }
        else{
            Hash hash = new Hash();
            user.setPassword(hash.hashWithRandomSalt(user.getPassword()));
            return this.insertOrSaveUser(user);
        }
    }


}
