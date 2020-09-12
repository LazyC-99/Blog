package com.lzc.dao;

import com.lzc.bean.Article;

import java.util.List;

public interface ArticleMapper {
    int insertArticle(Article user);


    int updateArticle(Article user);

    int setViewCount(Integer Id,Integer ViewCount);

    List<Article> getAllArticles();

    int countArticle();

    Article getArticleById(Integer id);

    List<Article> getArticleByUser(Integer userId);
}
