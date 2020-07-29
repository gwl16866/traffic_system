package com.hy.traffic.trainProgress.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author gwl
 * @since 2020-07-25
 */
@Data
@TableName(value = "saftyedu")
public class Teachinfo implements Serializable {

    private static final long serialVersionUID = 1L;

    //id
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    //培训主题
    private String theme;
    //开始时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone = "Asia/Shanghai")
    private LocalDateTime startTime;
    //结束时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone = "Asia/Shanghai")
    private LocalDateTime endTime;
    //培训科目
    private String project;
    //培训课程
    private String lession;
    //参训学员
    private String student;
    //安全管理员
    private String manager;
    //考核员
    private String testPeople;
    //学习方式
    private String learnType;
    //培训状态
    private Integer status;
    //培训地点
    private String address;
    //培训人
    private String trainTeacher;
   //下拉框查询时间
   @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone = "Asia/Shanghai")
    private LocalDate selectTime;
//    培训时长
    private String learnTime;

//   查询下拉框的年份
    private String nianfen;

    private Integer allproper;  //参训所有人
    private Integer ok;          //已完成人
    private Integer No;          //未完成人
    private  Integer bfb;         //所占百分比
    private String times;
    private Float baifenbi;




}
