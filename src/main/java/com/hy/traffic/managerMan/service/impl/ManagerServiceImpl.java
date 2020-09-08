package com.hy.traffic.managerMan.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hy.traffic.managerMan.entity.Manager;
import com.hy.traffic.managerMan.mapper.ManagerMapper;
import com.hy.traffic.managerMan.service.IManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author gwl
 * @since 2020-07-25
 */
@Service
public class ManagerServiceImpl extends ServiceImpl<ManagerMapper, Manager> implements IManagerService {
    @Autowired
    private ManagerMapper managerMapper;


    @Override
    public List<Manager> selectList() {
        return managerMapper.selectList(null);
    }
}
