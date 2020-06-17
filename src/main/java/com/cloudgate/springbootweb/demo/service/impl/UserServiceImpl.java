package com.cloudgate.springbootweb.demo.service.impl;

import com.cloudgate.springbootweb.demo.model.User;
import com.cloudgate.springbootweb.demo.repository.UserRepository;
import com.cloudgate.springbootweb.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl  implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public void save(User user) {
        userRepository.save(user);
    }


}
