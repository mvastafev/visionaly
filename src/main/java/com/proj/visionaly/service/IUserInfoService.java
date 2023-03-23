package com.proj.visionaly.service;

import com.proj.visionaly.models.UserInfo;

public interface IUserInfoService {

    UserInfo getUserInfoByUsername(String username);

    UserInfo updateUserInfo(UserInfo userInfo, String username);

}
