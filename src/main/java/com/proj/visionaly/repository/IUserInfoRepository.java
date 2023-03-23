package com.proj.visionaly.repository;

import com.proj.visionaly.models.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IUserInfoRepository extends JpaRepository<UserInfo, UUID> {

    @Query("select ui from UserInfo ui join User u where u.username = ?1")
    UserInfo getUserInfoByUsername(String username);

}
