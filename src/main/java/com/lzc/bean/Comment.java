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
    private Integer Id;
    private String Content;
    private Integer ArticleId;
    private Integer UserId;
    private Date CreateTime;
    private Integer Status;
}
