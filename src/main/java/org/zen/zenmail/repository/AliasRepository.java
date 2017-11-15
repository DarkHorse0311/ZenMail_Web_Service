package org.zen.zenmail.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.zen.zenmail.model.databaseadditionalcontent.Alias;

import java.util.Optional;

public  interface AliasRepository extends JpaRepository<Alias, Long> {
    Optional<Alias> findOneByAddress(String address);

    @Query(value = "INSERT into alias value(address, goto, domain, created, modified, active)", nativeQuery = true)
    public void isert();
    //Optional<Alias> findOneByUsernameAndPassword(String username, String password);
}