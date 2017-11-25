package org.zen.zenmail.identity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.authority.AuthorityUtils;

public class TokenUser extends org.springframework.security.core.userdetails.User {
    @Getter @Setter String userName;
    @Getter @Setter String password;
    @Getter @Setter String roles;

    public TokenUser(String userName, String password, String roles) {
        super(userName, password, AuthorityUtils.createAuthorityList(roles));
        this.userName = userName;
        this.password = password;
        this.roles = roles;
    }

    public TokenUser getUser() {
        return this;
    }
}
