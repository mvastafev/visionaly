package com.proj.visionaly.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Table(name = "user")
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String username;
    private String password;
    private String email;

    @OneToOne(fetch = FetchType.LAZY)
    private UserInfo userInfo;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles = new ArrayList<>(); //зачем тут инициализация?
}
