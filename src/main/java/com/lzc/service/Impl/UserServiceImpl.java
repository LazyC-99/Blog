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
        }else{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String format = sdf.format(new Date());
            user.setId(this.getMaxId()+1);
            user.setRegTime(sdf.parse(format));
            user.setAccessPermission(1);
            user.setStatus(1);
            if(userMapper.insertUser(user)==1){
                return Msg.success("添加成功!!");
            }else{
                return Msg.fail("添加失败!!");
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
            if(this.loginCheck(user.getName(),user.getPass())){
                return Msg.success("登录成功!!");
            }else{
                return Msg.fail("密码错误!!");
            }
        }else{
            return Msg.fail("用户不存在!!");
        }
    }


    /**
     * 获取用户中数
     * @return
     */
    public int getMaxId(){
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
    public boolean loginCheck(String username,String password){
        User user = userMapper.selectByName(username);
        if(user.getPass().equals(password)){
            return true;
        }else {
            return false;
        }
    }
}
