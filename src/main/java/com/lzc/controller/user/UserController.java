package com.lzc.controller.user;
import com.lzc.bean.Msg;
import com.lzc.bean.User;
import com.lzc.service.Impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.text.ParseException;

@Controller
public class UserController {
    @Autowired
    private UserServiceImpl userService;

    /**
     * 页面跳转
     * @param path
     * @return
     */
    @GetMapping("{path}")
    public String topage(@PathVariable String path){
        return path;
    }

    /**
     * 注册用户
     * @param user
     * @return
     */
    @PutMapping("/user")
    @ResponseBody
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
    public String login(User user, HttpSession session, Model model){
        Msg info =  userService.loginUser(user);
        session.setAttribute("userInfo",info.getExtend().get("userInfo"));
        model.addAttribute("msg",info);
        return "redirect:/article/list";
    }

    /**
     * 更新用户信息
     * @param user
     * @return
     */
    @PostMapping("/user")
    @ResponseBody
    public Msg update(User user){
        return userService.updateUser(user);
    }
    /**
     * 退出登录
     * @return
     */
    @GetMapping("/logout")
    public String exit(HttpSession session){
        session.removeAttribute("userInfo");
        return "redirect:/article/list";

    }

}
