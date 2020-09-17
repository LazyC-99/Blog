package com.lzc.controller.user;
import com.lzc.bean.Msg;
import com.lzc.bean.User;
import com.lzc.service.Impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.UUID;

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
    @PostMapping("/user/login")
    @ResponseBody
    public Msg login(User user, HttpSession session, Model model){
        Msg info =  userService.loginUser(user);
            session.setAttribute("userInfo",info.getExtend().get("userInfo"));
            model.addAttribute("msg",info);
            return info;
    }

    /**
     * 更新用户信息
     * @param user
     * @return
     */
    @PostMapping("/user")
    @ResponseBody
    public Msg update(@RequestParam("file")MultipartFile file, User user, HttpSession session, HttpServletRequest req){
        User userInfo = (User) session.getAttribute("userInfo");
        if(userInfo==null){
            return Msg.fail("请先登录!!");
        }
        if (!file.isEmpty()&&file!=null){
            // 获取文件存储路径（绝对路径）
            String path =  req.getServletContext().getRealPath("/WEB-INF/file");
            // 获取原文件名
            String fileName = file.getOriginalFilename();
            String uuid = UUID.randomUUID().toString().replaceAll("-","").toUpperCase();
            fileName=uuid+"_"+fileName;
            // 创建文件实例
            File filePath = new File(path, fileName);
            // 写入文件
            try {
                file.transferTo(filePath);
                user.setAvatar("/file/"+fileName);
            } catch (IOException e) {
                System.out.println("上传失败!!");
                e.printStackTrace();
            }
        }
        user.setId(userInfo.getId());
        if ( userService.updateUser(user).isCode()){
            session.removeAttribute("userInfo");
            session.setAttribute("userInfo",user);
        }
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
