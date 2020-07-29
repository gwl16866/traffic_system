package com.hy.traffic.yearPlan.controller;


import com.hy.traffic.yearPlan.entity.Yearplan;
import com.hy.traffic.yearPlan.service.IYearplanService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
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
@RequestMapping("/yearPlan/yearplan")
public class YearplanController {

    @Resource
    private IYearplanService yearplanService;

    @CrossOrigin
    @ResponseBody
    @RequestMapping("/selectYearplan")
    public List<Yearplan> selectYearplan(Yearplan yearplan){
        return yearplanService.selectYearplan(yearplan);
    }

    @CrossOrigin
    @ResponseBody
    @RequestMapping("/selectOn")
    public List<Yearplan> selectOn(String id){
        return yearplanService.selectOn(id);
    }

    @CrossOrigin
    @ResponseBody
    @RequestMapping("/tianjiaYearplan")
    public Integer tianjiaYearplan(String title,String bodys){
            /*Yearplan yearplan=new Yearplan();
            yearplan.setTitle(title);*/
           /* yearplan.setBodys(bodys);*/
            yearplanService.tianjiaYearplan(title,bodys);
        return 1;
    }

    @CrossOrigin
    @RequestMapping("/upd")
    public void upd(String title,String bodys,Integer id){
        Yearplan yearplan=new Yearplan();
        yearplan.setTitle(title);
        yearplan.setBodys(bodys);
        yearplan.setId(id);
        yearplanService.upd(yearplan);
    }

    @CrossOrigin
    @RequestMapping("/del")
    public String del(String id){
        yearplanService.del(id);
        return "0";
    }


}
