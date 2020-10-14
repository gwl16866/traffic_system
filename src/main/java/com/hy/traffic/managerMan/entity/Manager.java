package com.hy.traffic.managerMan.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author gwl
 * @since 2020-07-25
 */
@Data
@TableName("managerMan")
public class Manager implements Serializable {


    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;


    @TableField(value = "name")
    private String name;

    @TableField(value = "cardId")
    private String cardId;

    @TableField(value = "linkNum")
    private String linkNum;

    @TableField(value = "job")
    private String job;

    @TableField(exist = false)
    private String headImg;




}
