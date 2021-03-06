package com.hy.traffic.teachInfo.service;

import com.hy.traffic.studentInfo.entity.Studentinfo;
import com.hy.traffic.teachInfo.entity.*;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.InputStream;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author gwl
 * @since 2020-07-25
 */
public interface ITeachinfoService extends IService<BatchQuestions> {


  public List<Teachinfo> queryAllTeachinfo();
  public List<ClassDetail> queryFirstClassDetail(Integer cid);
  public List<ClassDetail> recursionHands(List<ClassDetail> list);
  public List<ClassDetail> queryZhangByClass(Integer id);
  public List<ClassDetail> queryJieByClass(Integer id);
  public List<QuestionsDetail> queryAllQuestion();
  public List<QuestionsDetail> queryAllQuestionById(Integer id);
  public Question queryOneAnswer(Integer id);
  public boolean addQuesObject(AddQuesObject add);
  public boolean addLession(AddLession add);
  public boolean addLessionsJie(AddLessions add);
  public boolean addLittleLes(AddLittleLes add);
  public List<OnlineTrain> queryOnlineTrainDetails(String cardId);
  public List<TrainVedio> queryVedioByTrainId(Integer trainId,String cardId);
  public List<TrainRecord> queryTrainRecord(String carId,String year);
  public StudentInfos queryStuInfor(String carId);
  public Integer updateVedioStatus(Integer trainId,String cardId,Integer vedioId);
  public List<examQuestion> queryExamQuestion(Integer trainId);
  public ExamScoreAndError testScore(ExamObject object);
  public Integer updateVedioPlayTime(Integer trainId, String cardId, Integer vedioId,Integer playTime);
  public Integer deleteTitleById(Integer id);
  public Integer deleteLeftTitleById(Integer id);
  public Integer updateTitle(Integer id,String title,String times);
  public Integer leftUpdateTitle(Integer id,String title);
  public Workbook exportFile();
  public List<BatchQuestions> importFile(InputStream inputStream);
  public Integer  updateQuestionOfTitle(Integer id);
  public Integer  deleteQuestion(Integer id);
  public boolean  batchAddJie(TwoObjs twoObjs);
}
