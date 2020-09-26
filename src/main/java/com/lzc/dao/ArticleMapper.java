package com.lzc.dao;

import com.lzc.bean.Article;

import java.util.List;

public interface ArticleMapper {
    int insertArticle(Article user);


    int updateArticle(Article user);

    int setViewCount(Integer Id,Integer ViewCount);

    List<Article> getAllArticles();

    List<Article> getLikeArticles();

    int countArticle();

    Article getArticleById(Integer id);

    List<Article> getArticleByUser(Integer userId);

    int insertLikeInfo(Integer uId,Integer aId);

    int delLikeInfo(Integer uId,Integer aId);

    int getLikeInfo(Integer uId,Integer aId);

    List<Article> searchBy(String search);
}
