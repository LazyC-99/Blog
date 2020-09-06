package com.lzc.dao;

import com.lzc.bean.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {

    int deleteUserById(int userId);

    int insertUser(User user);

    User selectByName(String username);

    List<User> selectAll();


    int UserCount();

    int update(User user);
}
