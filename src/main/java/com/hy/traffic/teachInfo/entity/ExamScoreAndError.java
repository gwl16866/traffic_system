package com.hy.traffic.teachInfo.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ExamScoreAndError implements Serializable {

    private String score;
    private List<ErrorQuestionDetails> errorQuestionDetails;
    private Integer status;
}
