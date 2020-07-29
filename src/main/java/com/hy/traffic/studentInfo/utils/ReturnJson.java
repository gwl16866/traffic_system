package com.hy.traffic.studentInfo.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @ClassName ReturnJson
 * @Description TODO
 * @Author zhangduo
 * @Date 2020/7/25 12:28
 * @Version 1.0
 */
@Data
@AllArgsConstructor
public class ReturnJson {
    private Integer code;
    private String message;
    private Object data;

}
