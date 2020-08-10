package com.hy.traffic.teachInfo.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class ErrorQuestionDetails implements Serializable {


    private String title;
    private String option;
    private String falseAnswer;
    private String trueAnswer;


}
