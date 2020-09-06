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
public class User {
    private int Id;
    private String Name;
    private String Pass;
    private String Nickname;
    private String Email;
    private String Avatar;
    private Date RegTime;
    private Date LastLoginTime;
    private Integer AccessPermission;
    private Integer Status;
}
