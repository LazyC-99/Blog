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
public class Comment {
    private int Id;
    private String Content;
    private int ArticleId;
    private int UserId;
    private Date CreateTime;
    private int Status;
}
