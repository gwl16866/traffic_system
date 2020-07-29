package com.hy.traffic.saftyEdu.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author gwl
 * @since 2020-07-25
 */
@Data
public class Saftyedu implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
//学习人数
    private Integer count;



    private String theme;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone = "Asia/Shanghai")
   private Date startTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone = "Asia/Shanghai")
    private Date endTime;

    private String project;

    private String lession;

    private String student;

    private String manager;

    private String testPeople;

    private Integer learnType;

    private Integer status;

    private String address;

    private String trainTeacher;

private String learnTime;
    @TableField(exist = false)
    private Integer jd;
    @TableField(exist = false)
    private Integer a;
    @TableField(exist = false)
    private Integer b;
}
