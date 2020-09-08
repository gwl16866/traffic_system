package com.hy.traffic.managerMan.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.hy.traffic.managerMan.entity.Manager;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author gwl
 * @since 2020-07-25
 */
public interface IManagerService extends IService<Manager> {

    public List<Manager> selectList();


}
