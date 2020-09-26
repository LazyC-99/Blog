package com.lzc.service;

import com.lzc.bean.Article;
import com.lzc.bean.Comment;
import com.lzc.bean.Msg;

import java.text.ParseException;


public interface ArticleService {
    Msg addArticle(Article article) throws ParseException;

    Msg AllArticle();

    Msg updateArticle(Article article);

    Msg updateStatus(Integer id,Integer StatusId);

    Msg getArticleById(Integer id,Integer uId);

    Msg getArticleByUser(Integer UserId);

    //查询喜欢文章
    Msg likeArticle(Integer Id);


    Msg addComment(Comment comment);

    Msg delComment(Integer commentId);
    //标记like
    Msg like(Integer id,Integer articleid);

    Msg dislike(Integer id, Integer articleid);

    Msg searchByContent(String search);
}
