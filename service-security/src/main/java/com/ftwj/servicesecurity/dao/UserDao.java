package com.ftwj.servicesecurity.dao;

import com.ftwj.servicesecurity.vo.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User,Integer> {
    User findByUsername(String username);

}
