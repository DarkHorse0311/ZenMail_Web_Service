package org.zen.zenmail.identity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.zen.zenmail.model.user.User;
import org.zen.zenmail.repository.UserRepository;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private UserRepository userRepo;
    private final AccountStatusUserDetailsChecker detailsChecker = new AccountStatusUserDetailsChecker();

    @Override
    public final TokenUser loadUserByUsername(String username) throws UsernameNotFoundException, DisabledException {

        final User user = userRepo.findOneByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        TokenUser currentUser;
        if (user.getActive() == 1){
            currentUser = new TokenUser(user);
        }
        else{
            throw new DisabledException("User is not activated (Disabled User)");
            //If pending activation return a disabled user
            //currentUser = new TokenUser(user, false);
        }
        detailsChecker.check(currentUser);
        return currentUser;
    }
}
