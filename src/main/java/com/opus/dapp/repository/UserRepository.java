package com.opus.dapp.repository;

import com.opus.dapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    // Optional<User> findByUsername(String username);

}
