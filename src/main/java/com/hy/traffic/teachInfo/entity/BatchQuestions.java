package com.hy.traffic.teachInfo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("questionsmanager")
public class BatchQuestions implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("oftitleid")
    private Integer  oftitleid;

    @TableField("questionTitle")
    private String   questionTitle;

    @TableField("questionType")
    private String   questionType;

    @TableField("options")
    private String   options;

    @TableField("answer")
    private String   answer;


}
