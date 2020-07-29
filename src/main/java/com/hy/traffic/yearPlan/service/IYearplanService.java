package com.hy.traffic.yearPlan.service;

import com.hy.traffic.yearPlan.entity.Yearplan;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author gwl
 * @since 2020-07-25
 */
public interface IYearplanService extends IService<Yearplan> {

    //查询全部
    List<Yearplan> selectYearplan(Yearplan yearplan);
    //查询用户详情
     List<Yearplan> selectOn(String id);
    //修改
    void upd(Yearplan yearplan);
    //删除
    void del(String id);
    //添加计划
    void tianjiaYearplan(String title,String bodys);

}
