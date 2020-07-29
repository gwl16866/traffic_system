package com.hy.traffic.teachInfo.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ClassDetail implements Serializable {

    private Integer id;
    private String oneTitle;
    private String vedio;
    private Integer classDetails;
    private Integer parentId;
    private List<ClassDetail> list;


}
