package com.cloudgate.springbootweb.demo.model;

import com.base.BaseEntity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="t_organization")
public class Organization extends BaseEntity {
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String code;
   /* @OneToMany(mappedBy = "organization", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private List<User> userList;
*/
    public Organization() {
    }


   /* public Organization(String name, String code, List<User> userList) {
        this.name = name;
        this.code = code;
        this.userList = userList;
    }*/
}