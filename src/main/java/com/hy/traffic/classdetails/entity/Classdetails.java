package com.hy.traffic.classdetails.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author jobob
 * @since 2020-09-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Classdetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField("oneTitle")
    private String oneTitle;

    @TableField("parentId")
    private Integer parentId;

    private String vedio;

    @TableField("classDetails")
    private Integer classDetails;

    @TableField("vedioTime")
    private Integer vedioTime;


}
