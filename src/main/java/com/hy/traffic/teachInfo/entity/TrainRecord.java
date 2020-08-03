package com.hy.traffic.teachInfo.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class TrainRecord implements Serializable {

    private Integer id;
    private String theme;
    private String startTime;
    private String endTime;
    private String completion;
    private List<AnswerRecord> score;


}
