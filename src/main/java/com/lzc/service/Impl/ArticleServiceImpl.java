package com.lzc.service.Impl;

import com.lzc.bean.Article;
import com.lzc.bean.Comment;
import com.lzc.bean.Msg;
import com.lzc.bean.User;
import com.lzc.dao.ArticleMapper;
import com.lzc.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    ArticleMapper articleMapper;

    /**
     * 添加文章
     * @param article
     * @return
     * @throws ParseException
     */
    @Override
    public Msg addArticle(Article article) throws ParseException {
        article.setId(this.getTotalArticle()+1);
        article.setCommentCount(0);
        article.setViewCount(0);
        article.setLikeCount(0);
        article.setCreateTime(cur_time());
        article.setUpdateTime(cur_time());
        article.setStatus(1);
        articleMapper.insertArticle(article);
        return null;
    }

    /**
     * 更新文章
     * @param article)
     * @return
     */
    @Override
    public Msg updateArticle(Article article) {
        try {
            article.setUpdateTime(cur_time());
        } catch (ParseException e) {
            e.printStackTrace();
            return Msg.fail("修改文章失败!!时间设置失败!!");
        }
        if(articleMapper.updateArticle(article)!=1){
            return Msg.fail("修改文章失败!!");
        }else{
            return Msg.success("修改文章成功!!");
        }

    }

    /**
     * 更新文章状态
     * @param id 更新目标
     * @param StatusId=0 删除
     *          =1 存在
     *          =2 审核
     * @return
     */
    @Override
    public Msg updateStatus(Integer id,Integer StatusId) {
        Article article = new Article();
        article.setId(id);
        article.setStatus(StatusId);
        System.out.println("要更新的:"+article);
        if (StatusId==1){
            articleMapper.updateArticle(article);
            return Msg.success("文章已恢复!!");
        }else if(StatusId==0){
            articleMapper.updateArticle(article);
            return Msg.success("文章已删除!!");
        }else if(StatusId==2){
            articleMapper.updateArticle(article);
            return Msg.success("文章已提交审核!!");
        }else{
            return Msg.fail("修改文章状态失败!!");
        }
    }

    /**
     * 查询所有文章
     * @return
     */
    @Override
    public Msg AllArticle() {
        List<Article> allArticles = articleMapper.getAllArticles();
        if (allArticles.size()>0){
            return Msg.success("查询所有文章!!").add("articles",allArticles);
        }else {
            return Msg.success("没有任何文章!!");
        }
    }


    /**
     * 根据id查文章并增加阅读数
     * @param id
     * @return
     */
    @Override
    public Msg getArticleById(Integer id) {
        Article article = articleMapper.getArticleById(id);
        if(article!=null){
            article.setViewCount(article.getViewCount()+1);
            articleMapper.updateArticle(article);
            return Msg.success("文章查询成功!!").add("articleInfo",article);
        }else{
            return Msg.fail("文章不存在");
        }
    }

    /**
     * 根据用户查文章
     * @param UserId
     * @return
     */
    @Override
    public Msg getArticleByUser(Integer UserId) {
        List<Article> articles = articleMapper.getArticleByUser(UserId);
        if(articles.size()>0){
            return Msg.success("文章查询成功!!").add("UserArticleInfo",articles);
        }else{
            return Msg.fail("用户没有发布任何文章!!");
        }
    }

    /**
     * 获取文章总数
     * @return
     */
    public int getTotalArticle(){
        return articleMapper.countArticle();
    }

    /**
     * 添加评论并增加文章评论数
     * @param comment
     * @return
     */

    @Override
    public Msg addComment(Comment comment) {

        return null;
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
