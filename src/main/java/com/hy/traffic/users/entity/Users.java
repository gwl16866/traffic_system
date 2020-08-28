package com.hy.traffic.users.entity;

import com.baomidou.mybatisplus.annotation.TableName;
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
 * @since 2020-08-28
 */
@Data
@TableName("user")
public class Users implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;

    private String password;


}
