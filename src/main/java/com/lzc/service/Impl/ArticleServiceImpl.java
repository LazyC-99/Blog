package com.lzc.service.Impl;

import com.lzc.bean.Article;
import com.lzc.bean.Comment;
import com.lzc.bean.Msg;
import com.lzc.bean.User;
import com.lzc.dao.ArticleMapper;
import com.lzc.dao.CommentMapper;
import com.lzc.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    ArticleMapper articleMapper;
    @Autowired
    CommentMapper commentMapper;

    /**
     * 添加文章
     * @param article
     * @return
     * @throws ParseException
     */
    @Override
    public Msg addArticle(Article article) {
        try {
            article.setId(this.getTotalArticle()+1);
            article.setCommentCount(0);
            article.setViewCount(0);
            article.setLikeCount(0);
            article.setCreateTime(cur_time());
            article.setUpdateTime(cur_time());
            article.setStatus(1);
            articleMapper.insertArticle(article);
        } catch (ParseException e) {
            e.printStackTrace();
            return Msg.fail("文章添加异常!!");
        }
        return Msg.success("文章添加成功!!");
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

    @Override
    public Msg likeArticle(Integer Id) {
        List<Article> likeArticles = articleMapper.getLikeArticles();
        if (likeArticles.size()>0){
            return Msg.success("查询喜欢文章!!").add("articles",likeArticles);
        }else {
            return Msg.success("没有喜欢的文章!!");
        }
    }


    /**
     * 根据id查文章和评论并增加阅读数
     * @param id
     * @return
     */
    @Override
    public Msg getArticleById(Integer id,Integer uId) {
        Article article = articleMapper.getArticleById(id);
        article.setViewCount(article.getViewCount()+1);
        if(article!=null){
            //收藏状态
            boolean like = false;
            articleMapper.updateArticle(article);
//            articleMapper.setViewCount(article.getId(),article.getViewCount()+1);
            //查询此文章瓶评论
            List<Comment> comments = commentMapper.getCommentByArticle(article.getId());
            like = articleMapper.getLikeInfo(uId,id)==1?true:false;
            return Msg.success("文章查询成功!!").add("articleInfo",article).add("articleComment",comments).add("articleLike",like);
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
        try {
            comment.setId(commentMapper.countComment()+1);
            comment.setCreateTime(cur_time());
            comment.setStatus(1);
            commentMapper.insertComment(comment);
            //为文章增加评论数
            Article curArticle =  articleMapper.getArticleById(comment.getArticleId());
            curArticle.setCommentCount(curArticle.getCommentCount()+1);
            articleMapper.updateArticle(curArticle) ;
            return Msg.success("评论成功!!");
        } catch (ParseException e) {
            e.printStackTrace();
            return Msg.fail("评论异常!!");
        }

    }

    /**
     * 喜欢文章
     * @param id
     * @param articleid
     * @return
     */
    @Override
    public Msg like(Integer id, Integer articleid) {
        if (articleMapper.insertLikeInfo(id, articleid)==1){
            return Msg.success("喜欢成功!!");
        }else{
            return Msg.fail("喜欢失败!!");
        }
    }

    /**
     * 取消喜欢文章
     * @param id
     * @param articleid
     * @return
     */
    @Override
    public Msg dislike(Integer id, Integer articleid) {
        if (articleMapper.delLikeInfo(id, articleid)==1){
            return Msg.success("取消star成功!!");
        }else{
            return Msg.fail("取消star失败!!");
        }
    }

    /**
     * 删除评论
     * @param commentId
     * @return
     */
    @Override
    public Msg delComment(Integer commentId) {
        commentMapper.delComment(commentId);
        return Msg.success("评论删除成功!!");
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
