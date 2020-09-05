package com.lzc.bean;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class Msg {
    private boolean code;
    private String msg;
    private Map<String,Object> extend=new HashMap<String, Object>();

    public  static Msg success(String msg){
        Msg result = new Msg();
        result.setCode(true);
        result.setMsg(msg);
        return result;
    }

    public  static Msg fail(String msg){
        Msg result = new Msg();
        result.setCode(false);
        result.setMsg(msg);
        return result;
    }
    public Msg add(String key, Object value) {
        this.getExtend().put(key,value);
        return this;
    }
}
