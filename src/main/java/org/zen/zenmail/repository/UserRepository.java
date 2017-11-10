package org.zen.zenmail.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zen.zenmail.model.user.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findOneByUsername(String username);

    Optional<User> findOneByUsernameAndPassword(String username, String password);
}
