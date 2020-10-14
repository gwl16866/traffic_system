package com.hy.traffic.managerMan.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.hy.traffic.managerMan.entity.Manager;
import com.hy.traffic.managerMan.service.impl.ManagerServiceImpl;
import com.hy.traffic.studentInfo.entity.Studentinfo;
import com.hy.traffic.studentInfo.service.IStudentinfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author gwl
 * @since 2020-07-25
 */
@RestController
@RequestMapping("/manager")
public class ManagerController {

    @Autowired
//    private ManagerServiceImpl managerService;
    IStudentinfoService iStudentinfoService;
    @RequestMapping("insert")
    public boolean insert(Manager manager){
        Studentinfo studentinfo=new Studentinfo();
        studentinfo.setType("1");
        studentinfo.setRealName(manager.getName());
        studentinfo.setLinkNum(manager.getLinkNum());
        studentinfo.setCardId(manager.getCardId());
        studentinfo.setJobName(manager.getJob());
        studentinfo.setStatus(1);
        studentinfo.setPassword(manager.getCardId().substring(12));
        studentinfo.setCreateTime(LocalDateTime.now());
        studentinfo.setHeadImgStatus(5);
        studentinfo.setHeadImg(manager.getHeadImg());
        return iStudentinfoService.save(studentinfo);
    }


    @RequestMapping("selectList")
    public List<Manager> selectList(){
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("type","1");
        List<Studentinfo> list=iStudentinfoService.list(queryWrapper);
        List<Manager> list1=list.stream().map(e->{
            Manager manager=new Manager();
            manager.setId(e.getId());
            manager.setCardId(e.getCardId());
            manager.setJob(e.getJobName());
            manager.setLinkNum(e.getLinkNum());
            manager.setName(e.getRealName());
            manager.setHeadImg(e.getHeadImg());
            return manager;
        }).collect(Collectors.toList());
       return list1;
    }

    @RequestMapping("update")
    public boolean update(Manager manager){
        UpdateWrapper updateWrapper=new UpdateWrapper();
        updateWrapper.set("realName",manager.getName());
        updateWrapper.set("cardId",manager.getCardId());
        updateWrapper.set("linkNum",manager.getLinkNum());
        updateWrapper.set("jobName",manager.getJob());
        updateWrapper.set("headimg",manager.getHeadImg());
        updateWrapper.eq("id",manager.getId());
        return iStudentinfoService.update(updateWrapper);
    }

    @RequestMapping("delete")
    public boolean delete(Integer id){
        return iStudentinfoService.removeById(id);
    }






}
