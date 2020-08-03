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
@TableName("saftyedu")
public class Teachinfo implements Serializable {

    private static final long serialVersionUID = 1L;

    //id
    private Integer id;
    //培训主题
    private String theme;
    //开始时间
    private String startTime;
    //结束时间
    private String endTime;
    //培训科目
    private String project;
    //安全管理员
    private String manager;
    //考核员
    private String testPeople;
    //学习方式
    private Integer learnType;
    //培训状态
    private String status;
    //培训地点
    private String address;
    //培训人
    private String trainTeacher;
    //培训时长
    private String learnTime;
    //及格分数
    private  Integer passscore;
    //总人数
    private  Integer allProper;
    //完成人数
    private  Integer okProper;
}
