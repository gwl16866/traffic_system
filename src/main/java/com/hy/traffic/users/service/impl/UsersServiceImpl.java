package com.hy.traffic.users.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hy.traffic.users.entity.Users;
import com.hy.traffic.users.mapper.UsersMapper;
import com.hy.traffic.users.service.IUsersService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2020-08-28
 */
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements IUsersService {

}
