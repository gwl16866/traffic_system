package com.hy.traffic.lookvediodetails.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author jobob
 * @since 2020-09-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Lookvediodetails implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 视频id
     */
    @TableField("classId")
    private Integer classId;

    /**
     * 学生id
     */
    @TableField("studentId")
    private Integer studentId;

    /**
     * 完成情况
     */
    private Integer status;

    @TableField("saftyeduId")
    private Integer saftyeduId;

    /**
     * 播放时间
     */
    @TableField("playTime")
    private Integer playTime;


}
