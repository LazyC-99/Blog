package com.lzc.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Article {
    private int Id;
    private int UserId;
    private int CategoryId;
    private String Title;
    private String Content;
    private int CommentCount;
    private int ViewCount;
    private int LikeCount;
    private Date CreateTime;
    private Date UpdateTime;
    private int Status;
}
