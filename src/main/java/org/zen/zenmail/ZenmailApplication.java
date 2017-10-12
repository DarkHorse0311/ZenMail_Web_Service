package org.zen.zenmail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {"org.zen.zenmail.repository"})
@EntityScan(basePackages = {"org.zen.zenmail.model"})
@EnableTransactionManagement
public class ZenmailApplication {
    public static void main(String[] args) {
        SpringApplication.run(ZenmailApplication.class, args);
    }
}
