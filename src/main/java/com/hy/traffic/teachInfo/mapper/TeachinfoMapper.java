package com.hy.traffic.teachInfo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hy.traffic.teachInfo.entity.*;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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


    /**
     * 根据培训id查旗下视频
     */
    @Select("SELECT cl.`oneTitle`,cl.`id`,cl.`vedio`,cl.`vedioTime`,lv.playTime,lv.`status` FROM  saftyclass sc,lookvediodetails lv,classdetails cl WHERE sc.`classId`=lv.`classId` AND cl.`id`=sc.`classId` " +
            " AND sc.`saftyId`=#{trainId} and studentId=#{stuId}")
    public  List<TrainVedio> queryVedioByTrainId(Integer trainId,Integer stuId);


    /**
     * 根据学生id 查询他参加的培训
     */
    @Select("SELECT s.id,s.`theme`,date_format(s.`startTime`, '%Y-%m-%d') startTime,date_format(s.`endTime`, '%Y-%m-%d') endTime,si.`completion` FROM saftyedu s,saftydustudentinfo si WHERE s.`id` =si.`saftyId` AND si.`stuId`=#{id} AND YEAR(startTime)=#{year}")
    public List<TrainRecord> queryTrainRecord(Integer id,String year);


    /**
     * //根据培训id查询考试记录
     * @param saftyId
     * @param stuId
     * @return
     */
    @Select("SELECT startTime,endTime,STATUS,score FROM answerrecord WHERE saftyid=#{saftyId} AND stuid =#{stuId} order by startTime desc")
    public List<AnswerRecord> queryAnswerRecord(Integer saftyId,Integer stuId);


    /**
     * 返回个人信息
     * @param cardId
     * @return
     */
    @Select("SELECT realName,linkNum,busNum,cardId,companyName FROM studentinfo WHERE cardId=#{cardId}")
    public StudentInfos queryStuInfor(String cardId);


    /**
     * 修改视频已观看
     * @param trainId
     * @param stuId
     * @param vedioId
     * @return
     */
    @Update("update lookvediodetails set status=2 where classid=#{vedioId} and studentid=#{stuId} and saftyeduId=#{trainId}")
    public Integer updateVedioStatus(Integer trainId,Integer stuId,Integer vedioId);

    /**
     * 修改视频播放时长
     * @param trainId
     * @param stuId
     * @param vedioId
     * @return
     */
    @Update("update lookvediodetails set playTime=#{playTime} where classid=#{vedioId} and studentid=#{stuId} and saftyeduId=#{trainId}")
    public Integer updateVedioPlayTime(Integer trainId,Integer stuId,Integer vedioId,Integer playTime);

    /**
     * 根据培训id  查询题目
     * @param trainId
     * @return
     */
    @Select("SELECT id,questionTitle,questionType,OPTIONS FROM questionsmanager WHERE oftitleid IN (SELECT sc.`classId` FROM saftyedu s,saftyclass sc WHERE s.`id`=sc.`saftyId` AND s.`id`=#{trainId})")
    public List<examQuestion> queryExamQuestion(Integer trainId);

    /**
     * 根据题目id查答案
     * @param qid
     * @return
     */
    @Select("SELECT answer FROM questionsmanager WHERE id=#{qid}")
    public String queryTrueAnswer(Integer qid);

    /**
     * 根据题目id查标题
     * @param qid
     * @return
     */
    @Select("SELECT questionTitle FROM questionsmanager WHERE id=#{qid}")
    public String queryquestionTitle(Integer qid);

    /**
     * 根据题目id查选项
     * @param qid
     * @return
     */
    @Select("SELECT options FROM questionsmanager WHERE id=#{qid}")
    public String queryOptions(Integer qid);


    /**
     * 查询过关分数
     * @param id
     * @return
     */
    @Select("SELECT passscore FROM saftyedu WHERE id=#{id}")
    public Integer queryScore(Integer id);

    /**
     * 插入记录表
     * @param saftyId
     * @param stuId
     * @param startTime
     * @param endTime
     * @param status
     * @param score
     * @return
     */
    @Insert("insert into answerrecord (saftyId,stuId,startTime,endTime,status,score) values(#{saftyId},#{stuId},#{startTime},#{endTime},#{status},#{score})")
    public Integer inserExamRecord(Integer saftyId,Integer stuId,String startTime,String endTime,Integer status,Integer score);


    /**
     * 插入考试详细表
     * @param questionId
     * @param answer
     * @param ofRecord
     * @param trueAnswer
     * @return
     */
    @Insert("insert into answer(questionId,answer,ofRecord,trueAnswer) values(#{questionId},#{answer},#{ofRecord},#{trueAnswer})")
    public Integer insertExamDetail(Integer questionId,String answer,Integer ofRecord,String trueAnswer);

    @Select("select max(id) from answerrecord")
    public Integer queryMaxExamId();



}
