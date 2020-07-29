package com.hy.traffic.studentaccmq.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hy.traffic.studentaccmq.entity.Studentacc;
import com.hy.traffic.studentaccmq.entity.StudentaccMq;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

@Mapper
public interface StudentaccmqMapper extends BaseMapper<Studentacc> {

    @Select("select * from studentacc")
    public List<Studentacc> queryall();


    @SelectProvider(type = StudentaccMq.class,method = "thenumber")
    public int thenumber(String studentid,Integer saftyid,Integer completion);
}
