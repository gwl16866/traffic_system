package com.hy.traffic.studentInfo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hy.traffic.studentInfo.entity.Answer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AnswerMapper extends BaseMapper<Answer> {

    @Select("SELECT a.*,q.`questionType`,q.`questionTitle`,q.`options` FROM answer a INNER JOIN questionsmanager q ON a.`questionId`=q.`id` WHERE a.`ofRecord`=#{value} ")
    public List<Answer> queryallanswer(Integer id);

}
