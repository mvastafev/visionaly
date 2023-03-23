package com.proj.visionaly.service;

import com.proj.visionaly.models.User;
import com.proj.visionaly.models.UserInfo;
import com.proj.visionaly.repository.IUserInfoRepository;
import com.proj.visionaly.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.*;

@Transactional
@Slf4j
@Service("UserInfoService")
@RequiredArgsConstructor
public class UserInfoService implements IUserInfoService{

    private IUserInfoRepository userInfoRepository;

    private IUserRepository userRepository;


    @Override
    public UserInfo getUserInfoByUsername(String username) {
        UserInfo userInfo = userInfoRepository.getUserInfoByUsername(username);
        checkUserInfoIfNull(userInfo, username);
        return userInfo;
    }

    @Override
    public UserInfo updateUserInfo(UserInfo userInfo, String username) {
        User user = userRepository.findByUsername(username);
        checkUserInfoIfNull(user, username);
        checkUserInfoIfNull(userInfo, username);
        user.setUserInfo(userInfo);
        return userInfo;
    }

    private <T> void checkUserInfoIfNull(T userInfo, String param) throws EntityNotFoundException {
        if(userInfo == null) {
            log.error("User information is not found. (param = " + param + ").");
            throw new EntityNotFoundException("User information is not found. (param = " + param + ").");
        }
    }
}
