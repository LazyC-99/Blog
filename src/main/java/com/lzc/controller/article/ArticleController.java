package com.lzc.controller.article;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lzc.bean.Article;
import com.lzc.bean.Comment;
import com.lzc.bean.Msg;
import com.lzc.bean.User;
import com.lzc.service.Impl.ArticleServiceImpl;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    ArticleServiceImpl articleService;

    /**
     * 添加新文章
     * @param article
     * @return
     */
    @PutMapping("/create")
    public Msg createArticle(Article article, HttpSession session){
        User userInfo = (User)session.getAttribute("userInfo");
        article.setUserId(userInfo.getId());
        return articleService.addArticle(article);
    }

    /**
     * 查询所有文章
     * @return
     */
    @GetMapping("/list")
    public String getAllArticle(@RequestParam(value = "currentPage",defaultValue ="1")Integer currentPage,Model model){
        PageHelper.startPage(currentPage,3);
        PageHelper.orderBy("article_id asc");

        PageInfo pageInfo = new PageInfo((List) articleService.AllArticle().getExtend().get("articles"));
        model.addAttribute("msg",pageInfo);
        return "index";
    }

    /**
     * 单篇文章查看
     * @param id
     * @return
     */
    @GetMapping("/topics/{id}")
    public String getArticle(@PathVariable Integer id,Model model){
        model.addAttribute("msg",articleService.getArticleById(id));
        return "blog-detail";
    }

    /**
     * 用户文章查询
     * @param UserId
     * @return
     */
    @GetMapping("/userArticle")
    public Msg getUserArticle(Integer UserId){
        return articleService.getArticleByUser(UserId);
    }

    /**
     * 删除文章
     * @param id
     * @return
     */
    @DeleteMapping("/del")
    public Msg delArticle(Integer id){
        return articleService.updateStatus(id,0);
    }

    /**
     * 更新文章
     * @param article
     * @return
     */
    @PostMapping("/update")
    public Msg updateArticle(Article article){
        return articleService.updateArticle(article);
    }


    /**
     * 发表评论
     * @param comment
     * @return
     */
    @PutMapping("/comment")
    public Msg createComment(Comment comment,HttpSession session){
        User userInfo = (User)session.getAttribute("userInfo");
        comment.setUserId(userInfo.getId());
        return articleService.addComment(comment);
    }

    @DeleteMapping("/comment")
    public Msg delComment(Integer commentId){
        return articleService.delComment(commentId);
    }



}
