package com.proj.visionaly.service;

import com.proj.visionaly.models.Role;
import com.proj.visionaly.models.User;

import java.util.List;

public interface IUserService {

    User saveUser(User user);

    User findUserByEmail(String email);

    Role saveRole(Role role);

    void addRoleToUser(String username, String roleName);

    List<User> getUsers();
}
