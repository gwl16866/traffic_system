package com.hy.traffic.trainProgress.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName(value = "studentinfo")
public class StudentInfoEntity {
   @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
   private String headImg;
   private String companyName;
   private String realName;
   private String cardId;
   private String linkNum;
   private String busNum;
   private String jobName;
   private LocalDateTime createTime;
   private Integer status;
   private String employedNum;
   private LocalDateTime driverOverTime;
   private LocalDateTime busCarefulTime;
   private String linkAddress;
   private String jobType;
   private LocalDateTime induction;
   private Integer completion;

}
