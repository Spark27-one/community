package com.example.demo.service;

import com.example.demo.mapper.UserMapper;
import com.example.demo.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public void createOrUpdate(User user1) {
        User dbUser=userMapper.findByAccountId(user1.getAccountId());
        if (dbUser == null){
            user1.setGmtCreate(System.currentTimeMillis());
            user1.setGmtModified(user1.getGmtCreate());
            userMapper.insert(user1);
        }else {
            dbUser.setAvatarUrl(user1.getAvatarUrl());
            dbUser.setToken(user1.getToken());
            dbUser.setName(user1.getName());
            dbUser.setGmtModified(System.currentTimeMillis());
            userMapper.update(dbUser);
        }
    }
}
