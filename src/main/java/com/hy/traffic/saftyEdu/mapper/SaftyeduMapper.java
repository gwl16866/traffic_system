package com.hy.traffic.saftyEdu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hy.traffic.saftyEdu.entity.MqBean;
import com.hy.traffic.saftyEdu.entity.SaftyduStudentinfo;
import com.hy.traffic.saftyEdu.entity.Saftyedu;
import com.hy.traffic.saftyEdu.entity.Tree;
import com.hy.traffic.studentInfo.entity.Studentinfo;
import com.hy.traffic.teachInfo.entity.ClassDetail;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

@Mapper
public interface SaftyeduMapper extends BaseMapper<Saftyedu> {

    //默认查询所有并且分页
    @Select("SELECT * FROM saftyedu")
    List<Saftyedu> querySaftyedu();
    //根据日期进行条件查询并分页
    @Select("SELECT * FROM saftyedu WHERE DATE_FORMAT(startTime,'%m')=DATE_FORMAT(#{value},'%m')")
    List<Saftyedu> querySaftyeduMonth(Date yuefen);

    @Select("Select * from saftydustudentinfo where said=#{value}")
    List<SaftyduStudentinfo> querySaftyduStudentinfoByid(Integer said);

    //默认查询所有并且分页
    @Select("SELECT * FROM saftyedu where id=#{value}")
    Saftyedu querySaftyeduByid(Integer id);


    @SelectProvider(type = SaftyEduProvider.class, method = "selectSaftyEdu")
    public List<Saftyedu> selectSaftyEdu(Integer learnType);

    //查询参训人数
    @Select("select count(*) from studentacc where saftyid=#{id}")
    public Integer selectStudentCount(Integer id);

    //修改状态
    @Update("update saftyedu set status=#{status} where id=#{id}")
    public void updateStatus(Integer id, Integer status);

    //根据安全教育课程查询学生
    @Select("SELECT sa.*,si.*  FROM studentinfo si,studentacc sa,saftyedu se WHERE si.id=sa.studentid AND sa.saftyid=se.id AND se.id=#{id}")
    public List<Studentinfo> selectStudent(Integer id);

    //在安全教育中删除学生
    @Delete("delete from studentacc where studentid=${studentid} and saftyid=${saftyid}")
    public void deleteStudent(Integer studentid, Integer saftyid);

    //查询所有学员
    @Select("select * from studentinfo where status=1 and headImgStatus=5 and id not in(select studentid from studentacc where saftyid=#{saftyid})")
    public List<Studentinfo> selectAllStudent(Integer saftyid);

    //批量添加学员
    @Insert("insert into studentacc (studentid,saftyid,completion) values(#{studentid},#{saftyid},1)")
    public void batchAddStudent(Integer saftyid, Integer studentid);

    @Select("select * from studentinfo where status=1 and headImgStatus=5 ")
    public List<Studentinfo> selectAllStu();

    @Select("SELECT id,oneTitle FROM classdetails  WHERE vedio is NOT NULL")
    public List<Tree> queryclassDetail();

    @Insert("insert into saftyedu(theme,startTime,endTime,lession,manager,testPeople,learnType,status,learnTime) values(#{theme},#{startTime},#{endTime},#{lession},#{manager},#{testPeople},#{learnType},1,#{learnTime})")
    public void addSaftyEdu(String theme, String startTime, String endTime, String lession, String manager, String testPeople,Integer learnType , String learnTime);

    @Select("select max(id) from saftyedu")
    public Integer selectMaxId();

    @Delete("delete from saftyedu where id=#{id}")
    public void deleteSaftyedu(Integer id);

    @Select("select * from saftyedu where id=#{id}")
    public Saftyedu selectlession(Integer id);

    @Select("select * from classdetails where id in (${id})")
    public List<ClassDetail> classDetailList(String id);

    @SelectProvider(type = MqBean.class,method = "year")
    public Integer year(String time,Integer i,Integer completion,Integer learnType);

    @SelectProvider(type =MqBean.class,method = "num")
    public List<Saftyedu> num(String time,Integer learnType);

}
