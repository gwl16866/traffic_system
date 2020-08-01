package com.hy.traffic.teachInfo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hy.traffic.teachInfo.entity.*;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

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
public interface TeachinfoMapper extends BaseMapper<Teachinfo> {


    /**
     * 查询所有安全教育课题
     * @return
     */
    @Select("select * from teachinfo")
    public List<Teachinfo> queryAllTeachinfo();

    /**
     * 查询一级
     * @return
     */
    @Select("SELECT * FROM classdetails where classDetails =#{cid} and parentid is null")
    public List<ClassDetail> queryFirstClassDetail(Integer cid);

    /**
     * 查询二级
     * @return
     */
    @Select("SELECT * FROM classdetails WHERE parentid=#{pid}")
    public List<ClassDetail> secondClassDetail(Integer pid);

    /**
     * 查询所有题目
     * @return
     */
    @Select("SELECT q.id,q.questionTitle,c.oneTitle,q.questionType FROM questionsmanager q,classdetails c WHERE q.`ofTitleId` = c.`id`")
    public List<QuestionsDetail> queryAllQuestion();

    /**
     * 查询大纲下所有题目
     * @return
     */
    @Select("SELECT q.id,q.questionTitle,c.oneTitle,q.questionType FROM questionsmanager q,classdetails c WHERE oftitleId IN(SELECT id FROM classdetails WHERE classdetails=#{id}) and q.`ofTitleId` = c.`id`")
    public List<QuestionsDetail> queryAllQuestionById(Integer id);

    /**
     * 查询答案
     * @param id
     * @return
     */
    @Select("SELECT q.`questionTitle`,q.`options`,q.`answer`,q.`analyzes` FROM questionsmanager q WHERE q.id=#{id}")
    public Question queryOneAnswer(Integer id);

    /**
     * 根据id查章节
     * @param id
     * @return
     */
    @Select("SELECT * FROM classdetails WHERE classdetails=#{id} AND parentid IS NULL")
    public List<ClassDetail> queryZhangByClass(Integer id);

    /**
     * 根据章查节
     * @param id
     * @return
     */
    @Select("SELECT * FROM classdetails WHERE parentid=#{id}")
    public List<ClassDetail> queryJieByClass(Integer id);

    /**
     * 插入问题
     * @param addQuesObject
     * @param sb
     * @return
     */
    @Insert("insert into questionsmanager(oftitleid,questionTitle,questionType,options)values(#{addQuesObject.xiaojies},#{addQuesObject.titles},#{addQuesObject.types},#{sb})")
    public Integer addQuestionsManager(AddQuesObject addQuesObject,String sb);

    /**
     * 插入答案
     * @param addQuesObject
     * @return
     */
    @Insert("insert into answer (ofquestionid,answer,analyzes) values(#{addQuesObject.id},#{duo},#{addQuesObject.analyzes})")
    public Integer addQuestionsAnswer(AddQuesObject addQuesObject,String duo);


    /**
     * 添加课件
     * @param oneTitle
     * @param parentId
     * @param vedio
     * @param classdetails
     * @return
     */
    @Insert("insert into classdetails (onetitle,parentId,vedio,classdetails) values(#{oneTitle},#{parentId},#{vedio},#{classdetails})")
    public Integer addLession(String oneTitle,String parentId,String vedio,String classdetails);

    /**
     * 添加纲
     * @param gang
     * @return
     */
    @Insert("insert into teachinfo values(null,#{gang})")
    public Integer addGang(String gang);


    /**
     * 最大id
     * @return
     */
    @Select("select max(id) from questionsmanager")
    public Integer maxQid();

    /**
     * 最大cid
     * @return
     */
    @Select("select max(id) from classdetails")
    public Integer maxCid();

    /**
     * 最大tid
     * @return
     */
    @Select("select max(id) from teachinfo")
    public Integer maxTid();


    /**
     * 查询培训列表  根据身份证id
     * @param id
     * @return
     */
    @Select("SELECT s.`id`,s.`theme` FROM  saftyedu s,saftydustudentinfo si,studentinfo stu WHERE s.`id`=si.`saftyId` AND stu.`id`=si.`stuId` AND stu.`cardId`=#{id}")
    public List<SaftyInfos> queryTrainList(String id);

    /**
     * 根据身份证查id
     * @param carId
     * @return
     */
    @Select("select id from studentinfo where cardid = #{carId}")
    public Integer queryIdByCarId(String carId);

    /**
     * 查询该培训主题下某个学生所有要看的视频总数
     * @param sid
     * @param stuId
     * @return
     */
    @Select("SELECT COUNT(id) FROM lookvediodetails WHERE saftyeduid=#{sid} AND studentid=#{stuId}")
    public Integer queryAllSaftyStuVedioCounts(Integer sid,Integer stuId);


    /**
     * 查询该培训主题下某个学生所有要看的视频 已完成数
     * @param sid
     * @param stuId
     * @return
     */
    @Select("SELECT COUNT(id) FROM lookvediodetails WHERE saftyeduid=#{sid} AND studentid=#{stuId} AND STATUS=2")
    public Integer queryOkSaftyStuVedioCounts(Integer sid,Integer stuId);





}
