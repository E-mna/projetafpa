package com.company.service;

import com.company.entity.User;
import com.company.repository.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class UserService {

    @Inject
    UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.listAll();
    }
}
