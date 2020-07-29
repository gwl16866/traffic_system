package com.hy.traffic.studyEvidence.mapper;

import com.hy.traffic.studentInfo.entity.Studentinfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface IStudyEvidenceMapper {

    @Select("SELECT * FROM studentinfo ")
    List<Studentinfo> queryStudentinfo();

}
