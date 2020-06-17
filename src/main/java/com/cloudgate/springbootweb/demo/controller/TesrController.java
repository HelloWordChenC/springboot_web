package com.cloudgate.springbootweb.demo.controller;


import com.cloudgate.springbootweb.demo.model.User;
import com.cloudgate.springbootweb.demo.repository.UserRepository;
import com.cloudgate.springbootweb.demo.repository.UserRepositoryEx;
import com.redis.RedisUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;


@RestController
public class TesrController {


    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Resource
    private UserRepository userRepository;

    @Resource
    private UserRepositoryEx userRepositoryEx;

    @Autowired
    private RedisUtils redisUtils;

    @ApiOperation(value = "controller请求测试")
    @RequestMapping(value = "/hello",method = RequestMethod.POST)
    public String hello() {
        log.info("controller请求测试----");
        return "hello world3";
    }


    @ApiOperation(value = "redis请求测试")
    @RequestMapping(value = "/helloRedis",method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "key", value = "key",paramType = "query")
    })
    public String helloRedis(
            @RequestParam(value = "key")String key) {
        //查询缓存中是否存在
        Object object = redisUtils.get(key);
        if(object==null){
            redisUtils.add(key, UUID.randomUUID().toString());
            return "ok";
        }else{
            //数据插入缓存（set中的参数含义：key值，user对象，缓存存在时间10（long类型），时间单位）
         return    redisUtils.get(key).toString();
        }

    }



    @ApiOperation(value = "查询所有")
    @RequestMapping(value = "/findAll",method = RequestMethod.POST)
    public List<User> selectList() {
        return userRepository.findAll();
    }


    @RequestMapping(value = "/testJpa",method = RequestMethod.POST)
    @ApiOperation(value = "通过名称查询接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "用户名",paramType = "query")
    })

          public List<User> testJpa(
            @RequestParam(value = "name") String name
    ) {
        return userRepositoryEx.findNo(name);
    }


    @ApiOperation(value = "通过名称和Id查询接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "用户名",paramType = "query"),
            @ApiImplicitParam(name = "id", value = "id",paramType = "query", required = true)
    })
    @RequestMapping(value = "/findByNameAndId",method = RequestMethod.POST)
    public User selectList(
            @RequestParam(value = "name") String name,
            @RequestParam(value = "id") long ids) {
        return userRepository.findByNameAndId(name, ids);
    }


    /**
     * 请求设置页数，每页多少行  页数从0开始
     * http://localhost:8081/findByNamePage?name=test_name&size=2&page=3
     * @param name
     * @param pageable
     * @return
     */
    @ApiOperation(value = "通过名称查询接口并且分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "用户名",paramType = "query"),
            @ApiImplicitParam(name = "page", value = "第几页",paramType = "query", required = true),
            @ApiImplicitParam(name = "size", value = "一页多少行",paramType = "query", required = true)
    })
    @RequestMapping(value = "/findByNamePage",method = RequestMethod.POST)
    public Page<User> findByNamePage(
            @RequestParam(value = "name") String name,
            @PageableDefault(sort = "createTime", direction = Sort.Direction.DESC)
                    Pageable pageable
    ) {
        return userRepository.findByName(name, pageable);
    }



    @ApiOperation("添加记录接口")
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public Map<String, Object> save() {
        Map result = new HashMap();
        try {
            User user = new User();
            user.setCreateTime(new Date());
            user.setName("test_name");
            user.setSex("test _sex");
            this.userRepository.save(user);
            result.put("result", "true");
            result.put("message", "添加成功");
        } catch (Exception ex) {
            result.put("result", "false");
            result.put("message", "添加失败" + ex.toString());
        }
        return result;
    }
}
