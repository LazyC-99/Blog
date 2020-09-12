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

    Msg getArticleById(Integer id);

    Msg getArticleByUser(Integer UserId);

    Msg addComment(Comment comment);

    Msg delComment(Integer commentId);
}
