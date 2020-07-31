package com.hy.traffic.trainProgress.controller;

import com.hy.traffic.trainProgress.entity.StudentInfoEntity;
import com.hy.traffic.trainProgress.entity.Teachinfo;
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


    //查询安全教育的所有年份
    @RequestMapping("/selectYear.do")
    public List<Teachinfo> selectYear(){
        return tranProgressImp.selectYear();
    };

    //  根据年份及月份查询条件内的数据
    @RequestMapping("/selectByTime.do")
    public List<Teachinfo> selectByTime(String Year,String Month){
        List<Teachinfo> list=tranProgressImp.selectByTime(Year,Month);

        Integer allproper=0;
        Integer ok=0;
        Integer no=0;
        Integer bfb=0;
        for (Teachinfo teachinfo : list) {
            String student=teachinfo.getStudent();
            if(student=="" || student==null){
                allproper=0;
            }else
             if(student.indexOf(",")==-1){
                 allproper=1;
             }else {
                 allproper=student.split(",").length;
             }

            teachinfo.setAllproper(allproper);

            ok=tranProgressImp.selectok(student,2,teachinfo.getId());
            teachinfo.setOk(ok);
            no=tranProgressImp.selectok(student,1,teachinfo.getId());
            teachinfo.setNo(no);

            if(ok!=0){
                teachinfo.setBfb(ok*100/allproper);
            }else{
                teachinfo.setBfb(0);
            }

        }


        return list;
    };

    //    进度
//    查询某一年月里产与培训的总人数
    @RequestMapping("/allNum.do")
    public Integer allNum(String Year,String Month){
        return tranProgressImp.allNum( Year, Month);
    };

    //    查询某一年月里完成培训的人数
    @RequestMapping("/successNum.do")
    public Integer successNum(String Year,String Month){
        return tranProgressImp.successNum( Year, Month);
    };


    //     查询某一年月中某一培训主题的总人数
    @RequestMapping("/themeAllNum.do")
    public String themeAllNum(String YearMonth,String theme){
        return tranProgressImp.themeAllNum(YearMonth,theme);
    };

    //     查询某一年月中某一培训主题的完成培训的人数
    @RequestMapping("/successThemeNum.do")
    public String successThemeNum(String YearMonth,String theme){
        return tranProgressImp.successThemeNum(YearMonth,theme);
    };

    //    参训学员
    //    查询查询学员的信息
    @RequestMapping("/SelectCXInfo.do")
    public List<StudentInfoEntity> SelectCXInfo(Integer saftyid){
        return tranProgressImp.SelectCXInfo(saftyid);
    };

    //    模糊查询学员的信息
    @RequestMapping("/LikeCXInfo.do")
    public List<StudentInfoEntity> LikeCXInfo(String selects,Integer saftyid,String inputs,Integer oneStatus){
        System.out.println("安全教育id"+saftyid+"输入的信息"+inputs+"下拉框选中的信息"+selects+"状态"+oneStatus);
        return tranProgressImp.LikeCXInfo(selects,saftyid,inputs,oneStatus);
    };


    //    将table数据导出到Excel中
    @RequestMapping("/exportExcel.do")
    public void exportExcel(HttpServletResponse resp, String Year,String Month) throws IOException {
        tranProgressImp.exportExcel(resp,Year,Month);
    }








}
