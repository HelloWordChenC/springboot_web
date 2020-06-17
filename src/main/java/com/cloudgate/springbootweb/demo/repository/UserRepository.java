package com.cloudgate.springbootweb.demo.repository;

import com.cloudgate.springbootweb.demo.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {

    public User findByNameAndId(String name,long id);


    public Page<User> findByName(String name,Pageable pageable);
}
