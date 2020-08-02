package com.hy.traffic.teachInfo.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ExamObject implements Serializable {

    private ReceiveScoreRecord receiveScoreRecord;
    private List<ReceiveQuestionList> list;

}
