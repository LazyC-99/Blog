package com.lzc.controller.user;
import com.lzc.bean.Msg;
import com.lzc.bean.User;
import com.lzc.service.Impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.text.ParseException;

@RestController
public class UserController {
    @Autowired
    private UserServiceImpl userService;

    /**
     * 注册用户
     * @param user
     * @return
     */
    @PutMapping("/user")
    public Msg reg(User user){
        try {
            return userService.addUser(user);
        } catch (ParseException e) {
            e.printStackTrace();
            return Msg.fail("注册异常!!");
        }
    }

    /**
     * 用户登录
     * @param user
     * @return
     */
    @GetMapping("/user")
    public Msg login(User user,HttpSession session){
        Msg info =  userService.loginUser(user);
        session.setAttribute("userInfo",info.getExtend().get("userInfo"));
        System.out.println("登录用户信息:"+info.getExtend());
        return info;
    }

    /**
     * 更新用户信息
     * @param user
     * @return
     */
    @PostMapping("/user")
    public Msg update(User user){
        return userService.updateUser(user);
    }
    /**
     * 退出登录
     * @return
     */
    @GetMapping("/logout")
    public Msg exit(HttpSession session){
        session.removeAttribute("userInfo");
        return Msg.success("退出登录!!");

    }

}
