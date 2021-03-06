package com.hy.traffic.studentInfo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Studentxiangqing {

    @TableId()
    private static final long serialVersionUID = 1L;

    /**
     * 学员主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("headimg")
    private String headImg;

    @TableField("password")
    private String password;

    @TableField("companyname")
    private String companyName;

    @TableField("realname")
    private String realName;

    @TableField("cardid")
    private String cardId;

    @TableField("linknum")
    private String linkNum;

    @TableField("busnum")
    private String busNum;

    @TableField("jobname")
    private String jobName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone = "Asia/Shanghai")
    @TableField("createtime")
    private LocalDateTime createTime;

    @TableField("status")
    private Integer status;

    @TableField("employednum")
    private String employedNum;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone = "Asia/Shanghai")
    @TableField("driverovertime")
    private LocalDateTime driverOverTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone = "Asia/Shanghai")
    @TableField("buscarefultime")
    private LocalDateTime busCarefulTime;

    @TableField("linkaddress")
    private String linkAddress;

    @TableField("jobtype")
    private String jobType;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone = "Asia/Shanghai")
    @TableField("induction")
    private LocalDateTime induction;

    @TableField("headImgStatus")
    private Integer headImgStatus;

    @TableField(exist = false)
    private Integer aid;
    @TableField(exist = false)
    private Integer astatus;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone = "Asia/Shanghai")
    @TableField(exist = false)
    private LocalDateTime startTime;
    @TableField(exist = false)
    private Integer score;

    @TableField(exist = false)
    private  String playtime;




}
