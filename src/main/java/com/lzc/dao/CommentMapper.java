package com.lzc.dao;

import com.lzc.bean.Comment;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentMapper {
    int insertComment(Comment comment);

    int delComment(Integer commentId);

    List<Comment> getCommentByArticle(Integer ArticleId);

    List<Comment> getCommentUser(Integer UserId);

    int countCommentByArticle(Integer ArticleId);

    int countComment();
}
