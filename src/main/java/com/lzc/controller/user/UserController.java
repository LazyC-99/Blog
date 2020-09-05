package com.lzc.controller.user;
import com.lzc.bean.Msg;
import com.lzc.bean.User;
import com.lzc.service.Impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @PostMapping("/reg")
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
    @PostMapping("/login")
    public Msg login(User user){
        return userService.loginUser(user);
    }

    /**
     * 退出登录
     * @return
     */
    @PostMapping("/logout")
    public Msg exit(HttpSession session){
        session.removeAttribute("userInfo");
        return Msg.success("退出登录!!");

    }

}
