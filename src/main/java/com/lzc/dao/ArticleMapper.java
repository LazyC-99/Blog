package com.lzc.dao;

import com.lzc.bean.Article;

import java.util.List;

public interface ArticleMapper {
    int insertArticle(Article user);

    int deleteArticle(Integer id);

    int updateArticle(Article user);

    List<Article> getAllArticles();

    int countArticle();

    Article getArticleById(Integer id);

    List<Article> getArticleByUser(Integer userId);
}
