package com.hy.traffic.studentInfo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class Answer {

    private Integer id;
    private Integer questionId;
    private String answer;
    private Integer ofRecord;
    private String trueAnswer;

    @TableField(exist = false)
    private String questionType;
    @TableField(exist = false)
    private String questionTitle;
    @TableField(exist = false)
    private String options;





}
