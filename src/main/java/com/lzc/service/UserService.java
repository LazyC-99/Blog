package com.lzc.service;

import com.lzc.bean.Msg;
import com.lzc.bean.User;

import java.text.ParseException;


public interface UserService {
     Msg addUser(User user) throws ParseException;

     Msg loginUser(User user);

     Msg updateUser(User user);
}
