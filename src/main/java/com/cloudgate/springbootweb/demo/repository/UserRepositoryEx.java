package com.cloudgate.springbootweb.demo.repository;

import com.cloudgate.springbootweb.demo.model.User;

import java.util.List;

public interface UserRepositoryEx {
    public List<User> findNo(String name);
}
