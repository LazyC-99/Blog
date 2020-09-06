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
    private Integer Id;
    private Integer UserId;
    private Integer CategoryId;
    private String Title;
    private String Content;
    private Integer CommentCount;
    private Integer ViewCount;
    private Integer LikeCount;
    private Date CreateTime;
    private Date UpdateTime;
    private Integer Status;
}
