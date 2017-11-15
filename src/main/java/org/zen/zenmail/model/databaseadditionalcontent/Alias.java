package org.zen.zenmail.model.databaseadditionalcontent;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "alias")
public class Alias {
    @Id
    @Getter @Setter
    private String address;
    @Column(name = "goto")
    @Getter @Setter private String gto;
    @Getter @Setter private String domain;
    @Getter @Setter private String created;
    @Getter @Setter private String modified;
    @Getter @Setter private int active;

    public Alias(String addres, String gto, String domain, String created, String modified, int active){
        this.setAddress(addres);
        this.setGto(gto);
        this.setDomain(domain);
        this.setCreated(created);
        this.setModified(modified);
        this.setActive(active);
    }

    public Alias(){
        this.setAddress("empty");
        this.setGto("empty");
        this.setDomain("emyty");
        this.setCreated(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()).toString());
        this.setModified(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()).toString());
        this.setActive(0);
    }
}
