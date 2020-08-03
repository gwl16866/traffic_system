package com.hy.traffic.teachInfo.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class AnswerFiveObj implements Serializable {

    private Integer questionId;
    private String answer;
    private String trueAnswer;
    private Integer score;


}
