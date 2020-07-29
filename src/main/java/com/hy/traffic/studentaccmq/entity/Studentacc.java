package com.hy.traffic.studentaccmq.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Studentacc implements Serializable {
        private int id;
        private int studentid;
        private int saftyid;
        private int completion;

}
