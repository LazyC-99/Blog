package com.lzc.controller.article;

import com.lzc.bean.Article;
import com.lzc.bean.Comment;
import com.lzc.bean.Msg;
import com.lzc.bean.User;
import com.lzc.service.Impl.ArticleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.text.ParseException;

@RestController
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
    @GetMapping("/all")
    public Msg getAllArticle(){
        return articleService.AllArticle();
    }

    /**
     * 单篇文章查看
     * @param id
     * @return
     */
    @GetMapping("/topics/{id}")
    public Msg getArticle(@PathVariable Integer id){
        return articleService.getArticleById(id);
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
