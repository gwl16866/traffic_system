package com.hy.traffic.trainProgress.controller;

import com.hy.traffic.trainProgress.entity.Teachinfo;
import com.hy.traffic.trainProgress.entity.Vedio;
import com.hy.traffic.trainProgress.service.impl.TranProgressImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
@CrossOrigin
@RequestMapping("/trainProgress")
public class TrainProgress {

    @Autowired
    private TranProgressImp tranProgressImp;

    /**
     * @Author zhangduo
     * @Description //TODO 查询所有主体信息、  根据年份及月份查询条件内的数据
     * @Date 10:08 2020/8/1
     * @Param [YearMonth]
     * @return java.util.List<com.hy.traffic.trainProgress.entity.Teachinfo>
     **/
    @RequestMapping("queryThemTable")
    public List<Teachinfo> queryThemTable(String Year,String Month){
        return tranProgressImp.queryThemTable(Year,Month);
    };

    /**
     * @Author zhangduo
     * @Description //TODO 查询当前年当前月的总完成数/已完成数
     * @Date 11:48 2020/8/1
     * @Param []
     * @return java.util.List<com.hy.traffic.trainProgress.entity.Teachinfo>
     **/
    @RequestMapping("queryAllProperAndOkProper")
    public Teachinfo queryAllProperAndOkProper(String Year,String Month){
        return tranProgressImp.queryAllProperAndOkProper(Year,Month);
    }

    //查询安全教育的所有年份
    @RequestMapping("/selectYear.do")
    public List<Teachinfo> selectYear(){
        return tranProgressImp.selectYear();
    };

    //    将table数据导出到Excel中
    @RequestMapping("/exportExcel.do")
    public void exportExcel(HttpServletResponse resp, String Year,String Month) throws IOException {
        tranProgressImp.exportExcel(resp,Year,Month);
    }

    /**
     * @Author zhangduo
     * @Description //TODO 查询某个主题的课程
     * @Date 15:25 2020/8/1
     * @Param [Year, Month]
     * @return java.util.List<com.hy.traffic.trainProgress.entity.Teachinfo>
     **/
    @RequestMapping("queryAllPeiXunClass")
    public List<Vedio> queryAllPeiXunClass(Integer Id){
        return tranProgressImp.queryAllPeiXunClass(Id);
    };

}
