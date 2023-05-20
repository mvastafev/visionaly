package com.proj.visionaly.service;

import com.proj.visionaly.models.Role;
import com.proj.visionaly.models.User;
import com.proj.visionaly.repository.IRoleRepository;
import com.proj.visionaly.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.*;
import java.util.Collection;
import java.util.List;

@Slf4j
@Service("UserService")
@RequiredArgsConstructor
@Transactional
public class UserService implements UserDetailsService, IUserService {

    private final IUserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final IRoleRepository roleRepo;

    @Override
    public UserDetails loadUserByUsername(String username){
        User user = userRepo.findByUsername(username);
        checkUserIfNull(user, username);
        Collection<SimpleGrantedAuthority> roles = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName())).toList();

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), roles);
    }

    //нужны ли здесь проверки на инъекции?
    @Override
    public User saveUser(User user) {
        log.info("New user {} saved", user.getUsername());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.saveAndFlush(user);
    }

    @Override
    public User findUserByEmail(String email) {
        User user = userRepo.findByEmail(email);
        checkUserIfNull(user, email);
        return user;
    }

    //single responsibility нарушен
    @Override
    public Role saveRole(Role role) {
        log.info("Adding a new role=" + role.getName() + "...");
        return roleRepo.saveAndFlush(role);
    }

    public User findAllUserInfo(String username) {
        log.info("Collection all info of user = " + username + "...");
        return userRepo.findAllUserInfo(username);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        User user = userRepo.findByUsername(username);
        checkUserIfNull(user, username);
        Role role = roleRepo.findByName(roleName);
        checkRoleIfNull(role, roleName);

        log.info("New role {} added to user {}", roleName, username);
        //данные будут обновлены автоматически после окончания транзакции
        user.getRoles().add(role);
    }

    //надо будет добавить пагинацию
    @Override
    public List<User> getUsers() {
        log.info("Fetching all users");
        return userRepo.findAll();
    }

    private void checkUserIfNull(User user, String param) throws EntityNotFoundException {
        if(user == null) {
            log.error("User is not found. (param = " + param + ").");
            throw new EntityNotFoundException("User is not found. (param = " + param + ").");
        }
    }

    private void checkRoleIfNull(Role role, String roleName) throws EntityNotFoundException {
        if(role == null) {
            log.error("Role with name= " + roleName + " not found.");
            throw new EntityNotFoundException("Role with name= " + roleName + " not found.");
        }
    }
}
