package com.cloudgate.springbootweb.demo.repository.Impl;

import com.base.dao.BaseDaoI;
import com.cloudgate.springbootweb.demo.model.User;
import com.cloudgate.springbootweb.demo.repository.UserRepositoryEx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserRepositoryExImpl  implements UserRepositoryEx {

    @Autowired
    private  BaseDaoI<User> baseDaoI;

    @Override
    public List<User> findNo(String name) {
        String hql = "from User where name =:name";
        Map patams = new HashMap();
        patams.put("name",name);
       return  this.baseDaoI.find(hql,patams);
    }
}
