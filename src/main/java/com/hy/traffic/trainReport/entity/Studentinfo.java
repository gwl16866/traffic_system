package com.hy.traffic.trainReport.entity;

import lombok.Data;
import org.apache.ibatis.annotations.Mapper;

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
public class Studentinfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ͷ
     */
    private String headImg;

    private String companyName;

    private String realName;

    private String cardId;

    private String linkNum;

    private String busNum;

    private String jobName;

    private LocalDateTime createTime;

    private Integer status;

    private String employedNum;

    private LocalDateTime driverOverTime;

    private LocalDateTime busCarefulTime;

    private String linkAddress;

    private String jobType;

    private LocalDateTime induction;


}
