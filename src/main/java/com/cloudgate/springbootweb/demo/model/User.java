package com.cloudgate.springbootweb.demo.model;

import com.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name="t_user")
public class User extends BaseEntity {

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String sex;
   /* @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "orgId")
    @JsonIgnore
    private Organization organization;*/

   /* @ManyToMany(cascade={CascadeType.MERGE,CascadeType.PERSIST})
    @JoinTable(name="t_user_role",joinColumns={@JoinColumn(name="userId")},
            inverseJoinColumns={@JoinColumn(name="roleId")} )
    private List<Role> roleList;*/

    public User() {
    }

    ;

   /* public User(String name, String sex, Organization organization) {
        this.name = name;
        this.sex = sex;
        this.organization = organization;
    }
*/

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

   /* public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }
*/}