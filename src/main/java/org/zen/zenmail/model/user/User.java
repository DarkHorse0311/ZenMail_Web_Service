package org.zen.zenmail.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "mailbox")
public class User {
    @Id
    private String username;
    @Getter @Setter private String password;
    @Getter @Setter private String name;
    @Getter @Setter private String maildir;
    @Getter @Setter private long quota;
    @Getter @Setter private String local_part;
    @Getter @Setter private String domain;
    @Getter @Setter private int tls_enforce_in;
    @Getter @Setter private int tls_enforce_out;
    @Getter @Setter private int kind;
    @Getter @Setter private int multiple_bookings;
    @Getter @Setter private int wants_tagged_subject;
    @Getter @Setter private String created;
    @Getter @Setter private String modified;
    @JsonIgnore @Getter @Setter private int active;

    public Role getRole(){
        return Role.USER;
    }

    public void setRole(Role role){

    }



    public String getname(String username){
        StringBuilder stringBuilder = new StringBuilder("");
        for(int i = 0; i < username.length(); ++i){
            if(username.charAt(i) == '@'){
                break;
            }
            stringBuilder.append(username.charAt(i));
        }
        return stringBuilder.toString();
    }

    public void setUsername(String username){
        this.username = username;
        String name = getname(username);
        this.setLocal_part(name);
        this.setMaildir("zenmail.space/" + name + "/");
    }

    public String getUsername(){
        return username;
    }

    public User(String username, String password, String name, String maildir, long quota, String local_part,
                String domain, int tls_enforce_in, int tls_enforce_out, int kind, int multiple_bookings, int wants_tagged_subject,
                String created, String modified, int active){
        this.setUsername(username);
        this.setPassword(password);
        this.setName(name);
        this.setMaildir(maildir);
        this.setQuota(quota);
        this.setLocal_part(local_part);
        this.setDomain(domain);
        this.setTls_enforce_in(tls_enforce_in);
        this.setTls_enforce_out(tls_enforce_out);
        this.setKind(kind);
        this.setMultiple_bookings(multiple_bookings);
        this.setWants_tagged_subject(wants_tagged_subject);
        this.setCreated(created);
        this.setModified(modified);
        this.setActive(active);
    }

    public User(String username, String password, String name, String domain, int quota){
        this(username + "@" + domain, password, name, "zenmail.space/" + username, quota,
                username, domain, 1, 1, 0, 0, 1,
                new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()).toString(),
                new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()).toString(), 1);
    }

    public User(String username, String password, String name){
        this(username + "@zenmail.space", password, name, "zenmail.space/" + username, 3072,
                username, "zenmail.space", 1, 1, 0, 0, 1,
                new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()).toString(),
                new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()).toString(), 1);
    }

    public User(){
        /*Date dateNow = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");*/
        this("new@zenmail.space", "PASSWORD", "NEW", "zenmail.space/new", 3072,
                "new", "zenmail.space", 1, 1, 0, 0, 1,
                new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()).toString(),
                new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()).toString(), 1);
    }
    /*@Getter @Setter private String userId;
    @Getter @Setter private String password = "";
    @Getter @Setter private String company;
    @Getter @Setter private String firstName;
    @Getter @Setter private String lastName;
    @Getter @Setter private String email;

    @JsonIgnore @Getter @Setter private int    securityProviderId;
    @JsonIgnore @Getter @Setter private int    defaultCustomerId;

    @JsonIgnore @Getter @Setter private String phone;
    @JsonIgnore @Getter @Setter private String address1;
    @JsonIgnore @Getter @Setter private String address2;
    @JsonIgnore @Getter @Setter private String country;
    @JsonIgnore @Getter @Setter private String postal;

    @Enumerated(EnumType.STRING)
    @Getter @Setter private Role role;

    @JsonIgnore @Getter @Setter private boolean isActive;

    @JsonIgnore @Getter @Setter private boolean isBlocked;
    @JsonIgnore @Getter @Setter private String  secretQuestion;
    @JsonIgnore @Getter @Setter private String  secretAnswer;
    @JsonIgnore @Getter @Setter private boolean enableBetaTesting;
    @JsonIgnore @Getter @Setter private boolean enableRenewal;

    public User(){
        this("new", "PASSWORD", Role.USER, "new", "new", true, "", "", "", "", "", "", "", "", true, false);
    }

    public User(String userId, String password, String firstName, String lastName){
        this(userId, password, Role.USER, firstName, lastName, true, "", "", "", "", "", "", "", "", true, false);
    }

    public User(String userId, String password, Role role, String firstName, String lastName){
        this(userId, password, role, firstName, lastName, true, "", "", "", "", "", "", "", "", true, false);
    }

    public User(String userId, String password, Role role, String firstName, String lastName, boolean isActive){
        this(userId, password, role, firstName, lastName, isActive, "", "", "", "", "", "", "", "", true, false);
    }

    public User(String userId, String password, Role role, String firstName, String lastName, boolean isActive,
         String company, String phone, String address1, String address2, String country, String postal,
         String secretQuestion, String secretAnswer, boolean enableRenewal, boolean enableBetaTesting){
        this.setUserId(userId);
        this.setEmail(userId);
        this.setPassword(new BCryptPasswordEncoder().encode(password));
        this.setRole(role);
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setActive(isActive);
        this.setCompany(company);
        this.setPhone(phone);
        this.setAddress1(address1);
        this.setAddress2(address2);
        this.setCountry(country);
        this.setPostal(postal);
        this.setSecretQuestion(secretQuestion);
        this.setSecretAnswer(secretAnswer);
        this.setEnableRenewal(enableRenewal);
        this.setEnableBetaTesting(enableBetaTesting);
    }

    public String getFullName(){
        return this.firstName + this.lastName;
    }*/
}
