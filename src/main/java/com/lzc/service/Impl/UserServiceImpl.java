package com.lzc.service.Impl;

import com.lzc.bean.Msg;
import com.lzc.bean.User;
import com.lzc.dao.UserMapper;
import com.lzc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;
    /**
     * 添加用户
     * @param user
     * @return
     */
    @Override
    public Msg addUser(User user) throws ParseException {
        if (this.userExist(user.getName())){
            return Msg.fail("账户名已存在!!");
        }else if(user==null||user.getName()==null||user.getName().equals("")||user.getPass()==null||user.getPass().equals("")){
            return Msg.fail("用户名密码不能为空!!");
        } else{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String format = sdf.format(new Date());
            user.setId(this.getTotalUser()+1);
            user.setRegTime(sdf.parse(format));
            user.setAccessPermission(1);
            user.setStatus(1);
            if(userMapper.insertUser(user)==1){
                return Msg.success("注册成功!!");
            }else{
                return Msg.fail("注册失败!!");
            }
        }
    }

    /**
     * 登录检查
     * @param user
     * @return
     */
    @Override
    public Msg loginUser(User user) {
        boolean exist = this.userExist(user.getName());
        if(exist){
            User userInfo = loginCheck(user.getName(), user.getPass());
            if(userInfo!=null){
                try {
                    userInfo.setLastLoginTime(cur_time());
                } catch (ParseException e) {
                    return Msg.fail("登录异常!!");
                }
                userMapper.update(userInfo);
                return Msg.success("登录成功!!").add("userInfo",userInfo);
            }else{
                return Msg.fail("密码错误!!");
            }
        }else{
            return Msg.fail("用户不存在!!");
        }
    }

    /**
     * 更新用户信息
     * @param user
     * @return
     */
    @Override
    public Msg updateUser(User user) {
        if(userMapper.update(user)==1){
            return Msg.success("修改成功!!");
        }else{
            return Msg.fail("修改失败!!");
        }
    }


    /**
     * 获取用户总数
     * @return
     */
    public int getTotalUser(){
        return userMapper.UserCount();
    }

    /**
     * 用户查询
     * @return
     */
    public boolean userExist(String username){
        ;
        return userMapper.selectByName(username)!=null;
    }

    /**
     * 登录验证
     * @param username
     * @param password
     * @return
     */
    public User loginCheck(String username,String password){
        User user = userMapper.selectByName(username);
        if(user.getPass().equals(password)){
            return user;
        }else {
            return null;
        }
    }

    /**
     * 获取当前时间
     * @return
     * @throws ParseException
     */
    public Date cur_time() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = sdf.format(new Date());
        Date cur_time = sdf.parse(format);
        return cur_time;
    }
}
