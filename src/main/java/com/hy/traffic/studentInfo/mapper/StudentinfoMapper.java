package com.hy.traffic.studentInfo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hy.traffic.saftyEdu.entity.Saftyedu;
import com.hy.traffic.studentInfo.entity.Studentinfo;
import com.hy.traffic.studentInfo.entity.Studentxiangqing;
import com.hy.traffic.studentInfo.provider.StudentinfoProvider;
import com.hy.traffic.studentInfo.utils.Provider;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author gwl
 * @since 2020-07-25
 */
@Mapper
public interface StudentinfoMapper extends BaseMapper<Studentinfo> {

    //根据said查数据
    @Select("select * from studentinfo where id in(select stuid from saftydustudentinfo where said= #{value})")
    List<Studentinfo> queryStudentinfoByid(Integer said);

    //根据said查数据
    @Select("select * from saftyedu where id in(select said from saftydustudentinfo where stuid= #{value})")
    List<Saftyedu> queryStudentinfoBystuid(Integer stuid);

    @SelectProvider(type = StudentinfoProvider.class, method = "queryByid")
    List<Studentinfo> queryByid(Integer said, String select, String neirong);

    @Select("select * from studentinfo where id=#{value}")
    Studentinfo querystuByid(Integer id);

    @SelectProvider(type = Provider.class, method = "queryAllStudentInFo")
    IPage<Studentinfo> queryAllStudentInFo(@Param("ipage") IPage<Studentinfo> page, @Param("studentinfo") Studentinfo studentinfo);

    @UpdateProvider(type = Provider.class, method = "updateOneStudent")
    public int updateOneStudent(Integer id, Integer status);

    //登录
    @Select("select * from studentinfo where cardId=#{cardId} and password=#{password}")
    public Studentinfo selectStudentInfo(String cardId, String password);

    @Update("update studentinfo set password=#{password} where id=#{id}")
    public void updatePassword(Integer id, String password);

    @SelectProvider(type = Provider.class, method = "studentxiangqing")
    public List<Studentxiangqing> studentxiangqing(Integer id);

    @Select(" SELECT s.*,a.id as aid , a.status as astatus,a.startTime,a.score FROM  studentinfo s LEFT JOIN answerrecord a ON a.stuid=s.id WHERE s.id IN(SELECT stuid FROM saftydustudentinfo WHERE saftyId=#{value}) AND a.saftyId=#{value}")
    public List<Studentxiangqing> studentxiangqing2(Integer id);

    @Select("SELECT stuid FROM saftydustudentinfo WHERE saftyid=#{value}")
    public Integer[] stuid(Integer id);

    @Select("SELECT * FROM studentinfo WHERE id=#{value}")
    public Studentxiangqing stuentmq(Integer id);



}