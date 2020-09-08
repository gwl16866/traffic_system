package com.hy.traffic.teachInfo.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class TwoObjs implements Serializable {

    private List<Lessionss> addList;
    private Integer dj;


}
