package com.hy.traffic.users.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hy.traffic.studentInfo.utils.ReturnJson;
import com.hy.traffic.users.entity.Users;
import com.hy.traffic.users.service.IUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jobob
 * @since 2020-08-28
 */
@RestController
@RequestMapping("/users")
public class UsersController {
    @Resource
    IUsersService iUsersService;

    @RequestMapping("/find")
    public JSONObject find(String user, String password) {
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("name",user);
        queryWrapper.eq("password",password);
        List<Users> users=iUsersService.list(queryWrapper);
        JSONObject data=new JSONObject();
        if (users.size()==0){
            data.put("code","error");
            data.put("msg","账号或密码不正确");
            return data;
        }
        data.put("code","success");
        data.put("msg","登陆成功");
        return data;
    }
}
