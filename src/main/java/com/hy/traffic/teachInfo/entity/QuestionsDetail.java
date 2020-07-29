package com.hy.traffic.teachInfo.entity;

import lombok.Data;

import javax.sql.rowset.serial.SerialArray;
import java.io.Serializable;

@Data
public class QuestionsDetail implements Serializable {
    private Integer id;
    private String questionTitle;
    private String oneTitle;
    private String questionType;




}
