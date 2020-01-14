package com.ftwj.servicesecurity.service;

import com.ftwj.servicesecurity.dao.UserDao;
import com.ftwj.servicesecurity.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private static final BCryptPasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();
    @Autowired
    private UserDao userDao;

    public User create(String username,String password){
        User user = new User();
        user.setUsername(username);
        password = "{bcrypt}"+PASSWORD_ENCODER.encode(password);
        user.setPassword(password);
        User result =  userDao.save(user);
        return result;
    }
    public static void main(String[] args){
        System.out.println("{bcrypt}"+PASSWORD_ENCODER.encode("zhuzhu7725"));
    }
}
