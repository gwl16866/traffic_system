package com.hy.traffic.trainProgress.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hy.traffic.trainProgress.entity.StudentInfoEntity;
import com.hy.traffic.trainProgress.entity.Teachinfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

@Mapper
public interface TrainProgressMapper extends BaseMapper<Teachinfo> {

//查询安全教育的所有年份
    @Select("SELECT DISTINCT DATE_FORMAT(startTime,'%Y')as nianfen FROM saftyedu ORDER BY startTime DESC ")
    public List<Teachinfo> selectYear();

//  根据年份及月份查询条件内的数据
    @SelectProvider(type =LikeSelect.class,method = "selectByTime")
    public List<Teachinfo> selectByTime(String YearMonth);

    @SelectProvider(type =LikeSelect.class,method = "selectok")
    public int selectok(String student,Integer completion,Integer saftyid);


//    进度
//    查询某一年月里产与培训的总人数
    @SelectProvider(type =LikeSelect.class,method = "allNum")
    public Integer allNum(String YearMonth);

//    查询某一年月里完成培训的人数
    @SelectProvider(type =LikeSelect.class,method = "successNum")
    public Integer successNum(String YearMonth);

    //     查询某一年月中某一培训主题的总人数
    @SelectProvider(type =LikeSelect.class,method = "themeAllNum")
    public String themeAllNum(String YearMonth,String theme);

    //     查询某一年月中某一培训主题的完成培训的人数
    @SelectProvider(type =LikeSelect.class,method = "successThemeNum")
    public String successThemeNum(String YearMonth,String theme);


    //    参训学员
   //    查询查询学员的信息
    @SelectProvider(type =LikeSelect.class,method = "SelectCXInfo")
    public List<StudentInfoEntity> SelectCXInfo(Integer saftyid);

    //    模糊查询学员的信息
    @SelectProvider(type =LikeSelect.class,method = "LikeCXInfo")
    public List<StudentInfoEntity> LikeCXInfo(String selects,Integer saftyid,String inputs,Integer oneStatus);

//    导出Excel查询
    @SelectProvider(type =LikeSelect.class,method = "selectExcel")
    public List<Teachinfo> selectExcel(String startTime);


}
