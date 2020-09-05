package com.lzc.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Category {
    private int Id;
    private String Index;
    private int ParentId;
    private String Name;
}
