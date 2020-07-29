package com.hy.traffic.studentInfo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @ClassName ExportFile
 * @Description TODO
 * @Author zhangduo
 * @Date 2020/7/27 20:01
 * @Version 1.0
 */
@Data
@AllArgsConstructor
public class ExportFile implements Serializable {
    /**
     * 学员主键
     */
    private String realName;

    private String cardId;

    private String linkNum;

    private String busNum;

    private String linkAddress;


}
