package org.zen.zenmail.model.session;

import lombok.Data;

import java.util.List;

@Data
public class SessionItem {
    private String token;
    private String username;
    private String name;
    //private String lastName;
    private String email;
    //private List<String> roles;
}
