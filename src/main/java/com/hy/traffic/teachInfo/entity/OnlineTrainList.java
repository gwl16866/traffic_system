package com.hy.traffic.teachInfo.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class OnlineTrainList implements Serializable {


            private String title;
            private List<OnlineTrain> list;



}
