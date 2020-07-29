package com.hy.traffic.teachInfo.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Question implements Serializable {

    private String questionTitle;
    private String options;
    private String answer;
    private String analyzes;


}
