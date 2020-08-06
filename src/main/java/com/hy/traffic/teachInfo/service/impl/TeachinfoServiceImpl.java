package com.hy.traffic.teachInfo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hy.traffic.teachInfo.entity.*;
import com.hy.traffic.teachInfo.mapper.TeachinfoMapper;
import com.hy.traffic.teachInfo.service.ITeachinfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author gwl
 * @since 2020-07-25
 */
@Service
public class TeachinfoServiceImpl extends ServiceImpl<TeachinfoMapper, Teachinfo> implements ITeachinfoService {
    @Autowired
    private TeachinfoMapper mapper;

    @Override
    public List<Teachinfo> queryAllTeachinfo() {
        return mapper.queryAllTeachinfo();
    }

    @Override
    public List<ClassDetail> queryFirstClassDetail(Integer cid) {
        return mapper.queryFirstClassDetail(cid);
    }
    public List<ClassDetail> secondClassDetail(Integer pid){
        return mapper.secondClassDetail(pid);
    }

    @Override
    public List<ClassDetail> recursionHands(List<ClassDetail> list) {
            if(null != list && !list.isEmpty()){
                for(ClassDetail cla:list){
                    List<ClassDetail> secondList=secondClassDetail(cla.getId());
                    cla.setList(secondList);
                    recursionHands(secondList);
                }
            }
            return list;
    }

    @Override
    public List<ClassDetail> queryZhangByClass(Integer id) {
        return mapper.queryZhangByClass(id);
    }

    @Override
    public List<ClassDetail> queryJieByClass(Integer id) {
        return mapper.queryJieByClass(id);
    }



    @Override
    public List<QuestionsDetail> queryAllQuestion() {
        return mapper.queryAllQuestion();
    }

    @Override
    public List<QuestionsDetail> queryAllQuestionById(Integer id) {
        return mapper.queryAllQuestionById(id);
    }

    @Override
    public Question queryOneAnswer(Integer id) {
        return mapper.queryOneAnswer(id);
    }

    @Override
    public boolean addQuesObject(AddQuesObject add) {
        StringBuilder sb =new StringBuilder();
        sb.append("A."+add.getA());
        sb.append(",");
        sb.append("B."+add.getB());
        sb.append(",");
        sb.append("C."+add.getC());
        sb.append(",");
        sb.append("D."+add.getD());
        //插入问题
        Integer q = mapper.addQuestionsManager(add,sb.toString());
        add.setId(mapper.maxQid());
        if(add.getTypes().equals("单选题")){
            String dan = add.getDanAnswer();
            mapper.addQuestionsAnswer(add,dan);
        }else{
            StringBuilder duo =new StringBuilder();
            for (int i = 0; i < add.getDuoAnswer().size(); i++) {
                duo.append(add.getDuoAnswer().get(i));
                if(i<add.getDuoAnswer().size()-1){
                    duo.append(",");
                }
            }
            mapper.addQuestionsAnswer(add,duo.toString());
        }
        return true;
    }

    @Override
    public boolean addLession(AddLession add) {
        if(!StringUtils.isEmpty(add.getD1())){
            //已选择大纲
            //添加章
            mapper.addLession(add.getZ(),null,null,add.getD1().toString());
            Integer z = mapper.maxCid();
            //添加节
            mapper.addLession(add.getJ(),z.toString(),null,add.getD1().toString());
            Integer j = mapper.maxCid();
            //添加小节
            mapper.addLession(add.getXj(),j.toString(),add.getVe(),add.getD1().toString());
        }else{
            //全文字
            //添加纲
            mapper.addGang(add.getD2());
            Integer t =mapper.maxTid();
            //添加章
            mapper.addLession(add.getZ(),null,null,t.toString());
            //添加节
            Integer z = mapper.maxCid();
            mapper.addLession(add.getJ(),z.toString(),null,t.toString());
            Integer j = mapper.maxCid();
            //添加小节
            mapper.addLession(add.getXj(),j.toString(),add.getVe(),t.toString());
        }
        return true;
    }

    @Override
    public boolean addLessionsJie(AddLessions add) {
        //mapper.addLession(add.getZhangjie(),null,null,add.getDagang());
        //Integer z = mapper.maxCid();
        //添加节
        mapper.addLession(add.getDajie(),add.getZhangjie(),null,add.getDagang());
        Integer j = mapper.maxCid();
        //添加小节
        mapper.addLession(add.getXiaojie(),j.toString(),add.getShipin(),add.getDagang());
        return true;
    }

    @Override
    public boolean addLittleLes(AddLittleLes add) {
        //添加小节
        mapper.addLession(add.getXj(),add.getDa(),add.getSp(),add.getBig());
        return true;
    }

    @Override
    public List<OnlineTrain> queryOnlineTrainDetails(String cardId) {
       List<SaftyInfos> saftyInfos= mapper.queryTrainList(cardId);
       //查询身份证
       Integer id = mapper.queryIdByCarId(cardId);

        List<OnlineTrain> list = new ArrayList<>();

        for (int i = 0; i < saftyInfos.size(); i++) {
            OnlineTrain onlineTrain = new OnlineTrain();
            onlineTrain.setId(saftyInfos.get(i).getId());
            onlineTrain.setTitle(saftyInfos.get(i).getTheme());
            //总数
            Integer counts=mapper.queryAllSaftyStuVedioCounts(saftyInfos.get(i).getId(),id);
            Integer okCounts=mapper.queryOkSaftyStuVedioCounts(saftyInfos.get(i).getId(),id);
            Integer bili = 0;
            if (okCounts > 0){
                bili = okCounts * 100 / counts;
            }

            DecimalFormat f = new DecimalFormat("0");
            f.setRoundingMode(RoundingMode.HALF_UP);
            onlineTrain.setStudyDetail(f.format(bili)+"%");
            list.add(onlineTrain);
        }
        return list;
    }

    @Override
    public List<TrainVedio> queryVedioByTrainId(Integer trainId,String cardId) {
        Integer id = mapper.queryIdByCarId(cardId);
        return mapper.queryVedioByTrainId(trainId,id);
    }

    @Override
    public List<TrainRecord> queryTrainRecord(String carId,String year) {
        //学生id
        Integer id =mapper.queryIdByCarId(carId);
        //培训列表
        List<TrainRecord> recordList =  mapper.queryTrainRecord(id,year);
        for (int i = 0; i < recordList.size(); i++) {
            List<AnswerRecord> answerList =  mapper.queryAnswerRecord(recordList.get(i).getId(),id);
            recordList.get(i).setScore(answerList);
        }
        return recordList;
    }

    @Override
    public StudentInfos queryStuInfor(String carId) {
        return mapper.queryStuInfor(carId);
    }

    @Override
    public Integer updateVedioStatus(Integer trainId, String cardId, Integer vedioId) {
        Integer id = mapper.queryIdByCarId(cardId);
        Integer ids = mapper.updateVedioStatus(trainId,id,vedioId);
        return ids;
    }

    @Override
    public Integer updateVedioPlayTime(Integer trainId, String cardId, Integer vedioId,Integer playTime) {
        Integer id = mapper.queryIdByCarId(cardId);
        Integer ids = mapper.updateVedioPlayTime(trainId,id,vedioId,playTime);
        return ids;
    }

    @Override
    public List<examQuestion> queryExamQuestion(Integer trainId) {
        return mapper.queryExamQuestion(trainId);
    }

    @Override
    public Integer testScore(ExamObject object) {
        //提交的答案  和  id
        List<ReceiveQuestionList> questions = object.getList();
        List<AnswerFiveObj> answerList = new ArrayList<>();
        for (int i = 0; i < questions.size(); i++) {
            //答案
          String answer = mapper.queryTrueAnswer(questions.get(i).getQuestionId());
            //new 出每条记录
            AnswerFiveObj fiveObj=new AnswerFiveObj();
            fiveObj.setQuestionId(questions.get(i).getQuestionId());
            fiveObj.setAnswer(questions.get(i).getAnswer());
            fiveObj.setTrueAnswer(answer);
          if(questions.get(i).getAnswer().equals(answer)){
              fiveObj.setScore(10);
          }else{
              fiveObj.setScore(0);
          }
            answerList.add(fiveObj);
        }
        Integer s=0;
        for (int j = 0; j < answerList.size(); j++) {
            s=s+answerList.get(j).getScore();
        }
        // 根据培训id查询本次培训过关分数
        Integer sc = mapper.queryScore(object.getReceiveScoreRecord().getSaftyId());
        Integer status=1;
        if(s>sc){
            status =2;
        }
        //插入记录  再插入详细
        Integer re = mapper.inserExamRecord(object.getReceiveScoreRecord().getSaftyId(),
                object.getReceiveScoreRecord().getStuId(),
                object.getReceiveScoreRecord().getStartTime(),
                object.getReceiveScoreRecord().getEndTime(),status,s);
        Integer id = mapper.queryMaxExamId();
        Integer re2=0;
        for (int a = 0; a < answerList.size(); a++) {
             re2 = mapper.insertExamDetail(answerList.get(a).getQuestionId(),
                    answerList.get(a).getAnswer(),id,answerList.get(a).getTrueAnswer());
        }
        if(re>0 && re2>0){
            return s;
        }else {
            return null;
        }
    }
}
