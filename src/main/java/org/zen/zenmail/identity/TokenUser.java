package org.zen.zenmail.identity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.zen.zenmail.model.user.User;

public class TokenUser extends org.springframework.security.core.userdetails.User {
    @Getter @Setter String userName;
    @Getter @Setter String password;
    @Getter @Setter String roles;
    @Getter @Setter String name;

    public TokenUser getTokenUser() {
        return this;
    }

    public TokenUser(String userName, String password, String roles, String name) {
        super(userName, password, AuthorityUtils.createAuthorityList(roles));
        this.userName = userName;
        this.password = password;
        this.roles = roles;
        this.name = name;
    }

}
