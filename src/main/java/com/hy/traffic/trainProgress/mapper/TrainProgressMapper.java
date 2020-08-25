package com.hy.traffic.trainProgress.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hy.traffic.trainProgress.entity.Teachinfo;
import com.hy.traffic.trainProgress.entity.Vedio;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface TrainProgressMapper extends BaseMapper<Teachinfo> {

    @Update("UPDATE saftyedu SET STATUS=#{status} WHERE id=#{ThemeId} ")
    Integer deleteTheme(Integer ThemeId, String status);

    @SelectProvider(type = LikeSelect.class,method = "queryThemTable")
    public List<Teachinfo> queryThemTable(String YearMonth);

    @SelectProvider(type = LikeSelect.class,method = "queryAllProperAndOkProper")
    public Teachinfo queryAllProperAndOkProper(String YearMonth);
    //查询安全教育的所有年份
    @Select("SELECT DISTINCT DATE_FORMAT(startTime,'%Y')as startTime FROM saftyedu ORDER BY startTime DESC ")
    public List<Teachinfo> selectYear();

    @SelectProvider(type = LikeSelect.class,method = "queryAllPeiXunClass")
    public List<Vedio> queryAllPeiXunClass(Integer Id);

}