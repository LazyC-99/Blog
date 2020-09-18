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
    @ResponseBody
    public Msg createArticle(Article article, HttpSession session){
        User userInfo = this.getCurrentUser(session);
        if(userInfo==null){
            return Msg.fail("请先登录!!!");
        }else{
            article.setUserId(userInfo.getId());
            return articleService.addArticle(article);
        }
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
    public String getArticle(@PathVariable Integer id,Model model,HttpSession session){
        User user =this.getCurrentUser(session);
        if(user==null){
            user = new User();
            user.setId(0);
        }
        model.addAttribute("msg",articleService.getArticleById(id,user.getId()));
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
    @ResponseBody
    public Msg createComment(Comment comment,HttpSession session){
        User userInfo = this.getCurrentUser(session);
        if(userInfo==null){
            return Msg.fail("请先登录!!");
        }else{
            comment.setUserId(userInfo.getId());
            return articleService.addComment(comment);
        }
    }

    /**
     * 删除评论
     * @param commentId
     * @return
     */
    @DeleteMapping("/comment")
    public Msg delComment(Integer commentId){
        return articleService.delComment(commentId);
    }

    /**
     * star文章
     * @param session
     * @param Articleid
     * @return
     */
    @PostMapping("/star")
    @ResponseBody
    public Msg likeArticle(HttpSession session,Integer Articleid){
        User userInfo = this.getCurrentUser(session);
        if (userInfo!=null){
            return articleService.like(userInfo.getId(),Articleid);
        }else{
            return Msg.fail("请先登录!!");
        }

    }
    /**
     * 取消star文章
     * @param session
     * @param Articleid
     * @return
     */
    @PostMapping("/cancelStar")
    @ResponseBody
    public Msg dislikeArticle(HttpSession session,Integer Articleid){
        User userInfo = this.getCurrentUser(session);
        if (userInfo!=null){
            return articleService.dislike(userInfo.getId(),Articleid);
        }else{
            return Msg.fail("请先登录!!");
        }

    }
    /**
     * 查询喜欢文章
     * @param currentPage
     * @param session
     * @param model
     * @return
     */
    @GetMapping("/like")
    public String getArticleLike(@RequestParam(value = "currentPage",defaultValue ="1")Integer currentPage,HttpSession session,Model model){
        User user = this.getCurrentUser(session);
        PageHelper.startPage(currentPage,3);
        PageHelper.orderBy("article_id asc");

        PageInfo pageInfo = new PageInfo((List) articleService.likeArticle(user.getId()).getExtend().get("articles"));
        model.addAttribute("msg",pageInfo);
        return "blog-star";
    }


    public User getCurrentUser(HttpSession session){
        User userInfo = (User)session.getAttribute("userInfo");
        return userInfo;
    }

}
