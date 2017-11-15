package org.zen.zenmail.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zen.zenmail.model.databaseadditionalcontent.UserAcl;

import java.util.Optional;

public interface UserAclRepository extends JpaRepository<UserAcl, Long> {
    Optional<UserAcl> findOneByUsername(String username);

    //Optional<Alias> findOneByUsernameAndPassword(String username, String password);
}