package org.zen.zenmail.model.databaseadditionalcontent;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_acl")
public class UserAcl {
    @Id
    @Getter @Setter private String username;
    @Getter @Setter private int spam_alias;
    @Getter @Setter private int tls_policy;
    @Getter @Setter private int spam_score;
    @Getter @Setter private int spam_policy;
    @Getter @Setter private int delimiter_action;
    @Getter @Setter private int syncjobs;
    @Getter @Setter private int eas_reset;
    @Getter @Setter private int eas_autoconfig;

    public UserAcl(String username){
        this.setUsername(username);
        this.setSpam_alias(1);
        this.setTls_policy(1);
        this.setSpam_score(1);
        this.setSpam_policy(1);
        this.setDelimiter_action(1);
        this.setSyncjobs(1);
        this.setEas_reset(1);
        this.setEas_autoconfig(1);
    }

    public UserAcl(){
        this.setUsername("empty");
        this.setSpam_alias(1);
        this.setTls_policy(1);
        this.setSpam_score(1);
        this.setSpam_policy(1);
        this.setDelimiter_action(1);
        this.setSyncjobs(1);
        this.setEas_reset(1);
        this.setEas_autoconfig(1);
    }
}
