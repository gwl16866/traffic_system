package com.hy.traffic.teachInfo.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class AddQuesObject implements Serializable {
//    private String classId;
//    private String zhangs;
//    private String jies;
    private Integer id;
    private String xiaojies;
    private String a;
    private String b;
    private String c;
    private String d;
    private String titles;
    private String types;

    private String danAnswer;
    private List<String> duoAnswer;
    private String analyzes;



}
