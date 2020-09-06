package com.lzc.controller.article;

import com.lzc.bean.Article;
import com.lzc.bean.Msg;
import com.lzc.service.Impl.ArticleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @PostMapping("/create")
    public Msg createArticle(Article article){
        System.out.println("添加新文章");
        try {
            articleService.addArticle(article);
            return Msg.success("文章添加成功!!");
        } catch (ParseException e) {
            e.printStackTrace();
            return Msg.fail("文章添加异常!!");
        }
    }

    /**
     * 查询所有文章
     * @return
     */
    @PostMapping("/all")
    public Msg getAllArticle(){
        return articleService.AllArticle();
    }

    /**
     * 单篇文章查询
     * @param id
     * @return
     */
    @PostMapping("/single")
    public Msg getArticle(Integer id){
        return articleService.getArticleById(id);
    }

    /**
     * 用户文章查询
     * @param UserId
     * @return
     */
    @PostMapping("/userArticle")
    public Msg getUserArticle(Integer UserId){
        return articleService.getArticleByUser(UserId);
    }

    /**
     * 删除文章
     * @param id
     * @return
     */
    @PostMapping("/del")
    public Msg delArticle(Integer id){
        return articleService.updateStatus(id,0);
    }

    /**
     * 更新文章
     * @param article
     * @return
     */
    @PostMapping("/update")
    public Msg delArticle(Article article){
        return articleService.updateArticle(article);
    }

}