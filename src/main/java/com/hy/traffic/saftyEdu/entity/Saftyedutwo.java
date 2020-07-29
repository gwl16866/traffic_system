package com.hy.traffic.saftyEdu.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author gwl
 * @since 2020-07-25
 */
@Data
public class Saftyedutwo implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
//学习人数
    private Integer count;

    private String theme;

   private String startTime;


    private String endTime;

    private String project;

    private Integer[] lession;

    private Integer[] student;

    private String manager;

    private String testPeople;

    private Integer learnType;

    private Integer status;

    private String address;

    private String trainTeacher;

private String learnTime;
}
