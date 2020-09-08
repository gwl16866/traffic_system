package com.hy.traffic.managerMan.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.hy.traffic.managerMan.entity.Manager;
import com.hy.traffic.managerMan.service.impl.ManagerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

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
    private ManagerServiceImpl managerService;

    @RequestMapping("insert")
    public boolean insert(Manager manager){
        return managerService.save(manager);
    }


    @RequestMapping("selectList")
    public List<Manager> selectList(){
       return managerService.selectList();
    }

    @RequestMapping("update")
    public boolean update(Manager manager){
        UpdateWrapper updateWrapper=new UpdateWrapper();
        updateWrapper.set("name",manager.getName());
        updateWrapper.set("cardId",manager.getCardId());
        updateWrapper.set("linkNum",manager.getLinkNum());
        updateWrapper.set("job",manager.getJob());
        updateWrapper.eq("id",manager.getId());
        return managerService.update(updateWrapper);
    }

    @RequestMapping("delete")
    public boolean delete(Integer id){
        return managerService.removeById(id);
    }






}
