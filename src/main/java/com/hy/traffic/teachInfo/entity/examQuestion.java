package com.hy.traffic.teachInfo.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class examQuestion implements Serializable {

    private Integer id;
    private String  questionTitle;
    private String questionType;
    private String options;

}
