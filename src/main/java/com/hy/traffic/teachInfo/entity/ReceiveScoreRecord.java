package com.hy.traffic.teachInfo.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class ReceiveScoreRecord implements Serializable {

    private Integer saftyId;
    private Integer stuId;
    private String startTime;
    private String endTime;
    private String stuIds;

}
