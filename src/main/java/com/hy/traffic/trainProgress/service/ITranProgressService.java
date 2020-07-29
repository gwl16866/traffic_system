package com.hy.traffic.trainProgress.service;

import com.hy.traffic.trainProgress.entity.StudentInfoEntity;
import com.hy.traffic.trainProgress.entity.Teachinfo;

import java.util.List;

public interface ITranProgressService {

    //    查询培训进度展示页面的字段信息
//    public List<Teachinfo> selectTrainProgress(Integer currentPage, Integer pageSize);

    //查询安全教育的所有年份
    public List<Teachinfo> selectYear();

    //  根据年份及月份查询条件内的数据
    public List<Teachinfo> selectByTime(String Year,String Month);

    //    进度
//    查询某一年月里产与培训的总人数
    public Integer allNum(String Year,String Month);

    //    查询某一年月里完成培训的人数
    public Integer successNum(String Year,String Month);

    //     查询某一年月中某一培训主题的总人数
    public String themeAllNum(String YearMonth,String theme);

    //     查询某一年月中某一培训主题的完成培训的人数
    public String successThemeNum(String YearMonth,String theme);

    //    参训学员
    //    查询查询学员的信息
    public List<StudentInfoEntity> SelectCXInfo(Integer saftyid);

    //    模糊查询学员的信息
    public List<StudentInfoEntity> LikeCXInfo(String selects,Integer saftyid,String inputs,Integer oneStatus);


}
