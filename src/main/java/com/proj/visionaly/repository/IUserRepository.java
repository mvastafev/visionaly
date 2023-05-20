package com.proj.visionaly.repository;

import com.proj.visionaly.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IUserRepository extends JpaRepository<User, UUID> {
    User findByUsername(String username);
    User findByEmail(String email);

    @Query("select u from User u left join UserInfo ui where u.username = ?1")
    User findAllUserInfo(String username);

    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}
