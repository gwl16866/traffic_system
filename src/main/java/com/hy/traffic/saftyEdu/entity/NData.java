package com.hy.traffic.saftyEdu.entity;

import lombok.Data;

@Data
public class NData {
    private String name;   //企业名称
    private Integer wcl;    //完成率
    private Integer jhs;    //计划数
    private Integer count;  //报名总人次
    private Integer okcount; //完成总人次
}
