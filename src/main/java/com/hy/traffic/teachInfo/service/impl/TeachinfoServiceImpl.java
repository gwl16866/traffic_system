package com.hy.traffic.teachInfo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hy.traffic.classdetails.entity.Classdetails;
import com.hy.traffic.classdetails.service.IClassdetailsService;
import com.hy.traffic.lookvediodetails.entity.Lookvediodetails;
import com.hy.traffic.lookvediodetails.service.ILookvediodetailsService;
import com.hy.traffic.studentInfo.entity.Studentinfo;
import com.hy.traffic.teachInfo.entity.*;
import com.hy.traffic.teachInfo.mapper.TeachinfoMapper;
import com.hy.traffic.teachInfo.service.ITeachinfoService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author gwl
 * @since 2020-07-25
 */
@Service
public class TeachinfoServiceImpl extends ServiceImpl<TeachinfoMapper, BatchQuestions> implements ITeachinfoService {
    @Autowired
    private TeachinfoMapper mapper;
    @Autowired
    ILookvediodetailsService iLookvediodetailsService;
    @Autowired
    IClassdetailsService iClassdetailsService;
    @Value("${img.vedioPath}")
    private String vedioPath;
    @Value("${img.file}")
    private String file;
    static ConcurrentHashMap<String,Integer> concurrentHashMap=new ConcurrentHashMap();

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
                list=list.stream().map(e->{
                    e.setVedio(vedioPath+e.getVedio());
                    return e;
                }).collect(Collectors.toList());
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
        add.setId(mapper.maxQid());
        StringBuilder duo =new StringBuilder();
        if(add.getTypes().equals("单选题")){
            duo.append(add.getDanAnswer()) ;
//            mapper.addQuestionsAnswer(add,dan);
        }else{

            for (int i = 0; i < add.getDuoAnswer().size(); i++) {
                duo.append(add.getDuoAnswer().get(i));
                if(i<add.getDuoAnswer().size()-1){
                    duo.append(",");
                }
            }
//            mapper.addQuestionsAnswer(add,duo.toString());
        }
        Integer q = mapper.addQuestionsManager(add,sb.toString(),duo.toString());
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
       List<SaftyInfos> saftyInfos= mapper.queryTrainList(cardId,new Date());
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
        List<TrainVedio> list=mapper.queryVedioByTrainId(trainId,id);
        list=list.stream().map(e->{
            e.setVedio(vedioPath+e.getVedio());
            return e;
        }).collect(Collectors.toList());
        return list;
    }

    @Override
    public List<TrainRecord> queryTrainRecord(String carId,String year) {
        //学生id
        Integer id =mapper.queryIdByCarId(carId);
        //培训列表
        List<TrainRecord> recordList =  mapper.queryTrainRecord(id,year,new Date());
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
        //处理视频快进问题
        String str = new StringBuilder().append(trainId).append(cardId).append(vedioId).toString();
        if (!concurrentHashMap.containsKey(str)) {
            return 0;
        }
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("id",vedioId);
        List<Classdetails> list=iClassdetailsService.list(queryWrapper);

        if(list.size()==0){
            return 0;
        }
        //获取视频信息
        /*File source = new File(new StringBuilder().append(file).append("vedio/").append(list.get(0).getVedio()).toString());
        Encoder encoder = new Encoder();
        it.sauronsoftware.jave.MultimediaInfo m = null;
        try {
            m = encoder.getInfo(source);
            long ls = m.getDuration()/1000;
            if (ls-concurrentHashMap.get(str)  > 5) {
                return 0;
            }
        } catch (EncoderException e) {
            e.printStackTrace();
            return 0;
        }*/

        concurrentHashMap.remove(str);

        Integer ids = mapper.updateVedioStatus(trainId,id,vedioId);
        Integer allOk = mapper.updateComplete(id,trainId);
        Integer ok=0;
        if(allOk == 0){
         ok =  mapper.updateEduStatus(id,trainId);
        }
        if(ok >0 && ids >0){
            return 1;
        }else{
            return 0;
        }
    }

    @Override
    public Integer updateVedioPlayTime(Integer trainId, String cardId, Integer vedioId,Integer playTime) {
        Integer id = mapper.queryIdByCarId(cardId);
        //处理视频快进问题
        String str = new StringBuilder().append(trainId).append(cardId).append(vedioId).toString();

        if (concurrentHashMap.containsKey(str)) {

            if (playTime - concurrentHashMap.get(str) > 5) {
                return 0;
            }
        } else {

            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("studentid", id);
            queryWrapper.eq("classid", vedioId);
            queryWrapper.eq("saftyeduId", trainId);
            List<Lookvediodetails> lookvediodetails = iLookvediodetailsService.list(queryWrapper);
            if (lookvediodetails.size() == 0) {
                return 0;
            }
            Lookvediodetails lookvediodetails1 = lookvediodetails.get(0);
            if (playTime - lookvediodetails1.getPlayTime() > 5) {
                return 0;
            }

        }
        concurrentHashMap.put(str, playTime);

        Integer ids = mapper.updateVedioPlayTime(trainId,id,vedioId,playTime);
        return ids;
    }

    @Override
    public Integer deleteTitleById(Integer id) {
        return mapper.deleteTitleById(id);
    }

    @Override
    public Integer deleteLeftTitleById(Integer id) {
        Integer a =mapper.deleteLeftTitleById(id);
        Integer b =mapper.deleteLeftTitleLessionById(id);
        if (a>0 && b>0){
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public Integer updateTitle(Integer id, String title,String times) {
        return mapper.updateTitle(id,title,times);
    }

    @Override
    public Integer leftUpdateTitle(Integer id, String title) {
        return mapper.leftUpdateTitle(id,title);
    }

    @Override
    public Workbook exportFile(){
        Workbook workbook = new HSSFWorkbook();
        Sheet workbookSheet = workbook.createSheet("题目集");
        Row row0 = workbookSheet.createRow(0);
        row0.createCell(0).setCellValue("题目");
        row0.createCell(1).setCellValue("类型");
        row0.createCell(2).setCellValue("选项");
        row0.createCell(3).setCellValue("答案");
        Row row1 = workbookSheet.createRow(1);
        row1.createCell(0).setCellValue("测试");
        row1.createCell(1).setCellValue("单选题");
        row1.createCell(2).setCellValue("A.测试,B.测试1,C.测试2");
        row1.createCell(3).setCellValue("A");
        Row row2 = workbookSheet.createRow(2);
        row2.createCell(0).setCellValue("测试");
        row2.createCell(1).setCellValue("多选题");
        row2.createCell(2).setCellValue("A.测试,B.测试1,C.测试2");
        row2.createCell(3).setCellValue("A,B");
        return workbook;
    }

    @Override
    public List<BatchQuestions> importFile(InputStream inputStream){
        List<BatchQuestions> mapList = new ArrayList<>();
        try {
            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheetAt(0);
            Row titleRow = sheet.getRow(0);
            int num = sheet.getLastRowNum();
            for (int i = 0; i <num ; i++) {
                BatchQuestions studentinfo = new BatchQuestions();
                Row row = sheet.getRow(i+1);
                for (int j = 0; j <row.getLastCellNum(); j++) {
                    Cell cell = row.getCell(j);
                    cell.setCellType(CellType.STRING);
                    String key = titleRow.getCell(j).getStringCellValue();
                    String value = cell.getStringCellValue();
                    System.out.print(key+value);
                    if(key.equals("题目")){
                        studentinfo.setQuestionTitle(value);
                    }else if(key.equals("类型")){
                        studentinfo.setQuestionType(value);
                    }else if(key.equals("选项")){
                        studentinfo.setOptions(value);
                    }else if(key.equals("答案")){
                        studentinfo.setAnswer(value);
                    }
                }
                mapList.add(studentinfo);
            }
        } catch (IOException | InvalidFormatException e) {
            e.printStackTrace();
        }
        return mapList;
    }

    @Override
    public Integer updateQuestionOfTitle(Integer id) {
        return mapper.updateQuestionOfTitle(id);
    }

    @Override
    public Integer deleteQuestion(Integer id) {
        return mapper.deleteQuestion(id);
    }

    @Override
    public boolean batchAddJie(TwoObjs twoObjs) {
        //Integer zhang = mapper.selectZhangs(twoObjs.getDj());
        int p=0;
        Integer gang = mapper.selectDagangs(twoObjs.getDj());
        for (int i = 0; i < twoObjs.getAddList().size(); i++) {
         p  =   mapper.addLession(twoObjs.getAddList().get(i).getXj(),twoObjs.getDj().toString(),twoObjs.getAddList().get(i).getVedio(),gang.toString());
        }
        if(p>0){
            return true;
        }else {
            return false;
        }

    }

    @Override
    public List<examQuestion> queryExamQuestion(Integer trainId) {
        List<examQuestion> list = mapper.queryExamQuestion(trainId);
        list = list.stream().map(e -> {
            if (e.getQuestionType().equals("单选题")) {
                e.setOptions(new StringBuffer().append(e.getOptions()).append(",").toString());
            }
            return e;
        }).collect(Collectors.toList());
        return list;
    }

    @Override
    public ExamScoreAndError testScore(ExamObject object) {
        //提交的答案  和  id
        List<ReceiveQuestionList> questions = object.getList();
        List<AnswerFiveObj> answerList = new ArrayList<>();
        List<ErrorQuestionDetails> errorDetails=new ArrayList<>();
        ExamScoreAndError obj= new ExamScoreAndError();
        for (int i = 0; i < questions.size(); i++) {
            //答案
          String answer = mapper.queryTrueAnswer(questions.get(i).getQuestionId());
          String title = mapper.queryquestionTitle(questions.get(i).getQuestionId());
          String ops = mapper.queryOptions(questions.get(i).getQuestionId());
            //new 出每条记录
            AnswerFiveObj fiveObj=new AnswerFiveObj();
            fiveObj.setQuestionId(questions.get(i).getQuestionId());
            fiveObj.setAnswer(questions.get(i).getAnswer());
            fiveObj.setTrueAnswer(answer);
          if(questions.get(i).getAnswer().equals(answer)){
              fiveObj.setScore(10);
          }else{
              fiveObj.setScore(0);
              //new出错题对象
              ErrorQuestionDetails err=new ErrorQuestionDetails();
              err.setTitle(title);
              err.setOption(ops);
              err.setFalseAnswer(questions.get(i).getAnswer());
              err.setTrueAnswer(answer);
              errorDetails.add(err);
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
        if(s>=sc){
            status =2;
        }
        obj.setStatus(status);
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
            obj.setScore(s);
            obj.setErrorQuestionDetails(errorDetails);
            return obj;
        }else {
            return null;
        }
    }
}
