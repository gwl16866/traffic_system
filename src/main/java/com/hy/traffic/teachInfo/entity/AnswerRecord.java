package com.hy.traffic.teachInfo.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class AnswerRecord implements Serializable {

    private String startTime;
    private String endTime;
    private String STATUS;
    private String score;
    private String theme;
    private String learnType;

}
