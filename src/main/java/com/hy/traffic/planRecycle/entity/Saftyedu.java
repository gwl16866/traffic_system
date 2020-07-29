package com.hy.traffic.planRecycle.entity;

import lombok.Data;

import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author gwl
 * @since 2020-07-25
 */
@Data
public class Saftyedu implements Serializable {

    private static final long serialVersionUID = 1L;

    private String theme;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private String project;

    private String lession;

    private String student;

    private String manager;

    private String testPeople;

    private String learnType;

    private Integer status;

    private String address;

    private String trainTeacher;


}
