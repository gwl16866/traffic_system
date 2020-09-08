package com.hy.traffic.teachInfo.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hy.traffic.studentInfo.entity.Studentinfo;
import com.hy.traffic.studentInfo.service.IStudentinfoService;
import com.hy.traffic.studentInfo.utils.ReturnJson;
import com.hy.traffic.teachInfo.entity.*;
import com.hy.traffic.teachInfo.service.ITeachinfoService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author gwl
 * @since 2020-07-25
 */
@RestController
@RequestMapping("/teachInfo")
@CrossOrigin
public class TeachinfoController {

    @Autowired
    private ITeachinfoService teachinfoService;
    @Autowired
    private IStudentinfoService iStudentinfoService;

    @Value("${img.filePath}")
    String imgFilePath;

    @Value("${img.file}")
    String imgFile;
    /**
     * 查询所有教育课题
     * @return
     */
    @RequestMapping("queryAllTeachinfo")
    public List<Teachinfo> queryAllTeachinfo(){

        return teachinfoService.queryAllTeachinfo();
    }


    /**
     * 根据大纲 查询课件 递归
     * @return
     */
    @RequestMapping("queryClassDetail")
    public List<ClassDetail> queryClassDetail(Integer cid){
        //先查询一级
        List<ClassDetail> first = teachinfoService.queryFirstClassDetail(cid);
        List<ClassDetail> all = teachinfoService.recursionHands(first);
           return all;
    }

    /**
     * 查询所有题目
     * @return
     */
    @RequestMapping("queryAllQuestion")
    public List<QuestionsDetail> queryAllQuestion(){
        return teachinfoService.queryAllQuestion();
    }


    /**
     * 查询大纲下所有题目
     * @return
     */
    @RequestMapping("queryAllQuestionById")
    public List<QuestionsDetail> queryAllQuestion(Integer id){
        return teachinfoService.queryAllQuestionById(id);
    }



    /**
     * 答案
     * @param id
     * @return
     */
    @RequestMapping("queryOneAnswer")
    public Question queryOneAnswer(Integer id){
        return teachinfoService.queryOneAnswer(id);
    }


    /**
     * 根据class id 查询章节
     * @param id
     * @return
     */
    @RequestMapping("queryZhangByClass")
    public List<ClassDetail> queryZhangByClass(Integer id){
        return teachinfoService.queryZhangByClass(id);
    }

    /**
     * 根据章查询 节
     * @param id
     * @return
     */
    @RequestMapping("queryJieByClass")
    public List<ClassDetail> queryJieByClass(Integer id){
        return teachinfoService.queryJieByClass(id);
    }


    /**
     * 添加问题和答案
     * @param addQuesObject
     * @return
     */
    @RequestMapping("addQues")
    public boolean addQues(@RequestBody() AddQuesObject addQuesObject){
        teachinfoService.addQuesObject(addQuesObject);
        return true;
    }

    //添加页面——上传文件
    @RequestMapping(value = "uploadFile")
    public Upload uploadFile(@RequestParam("file") MultipartFile file, HttpServletRequest request) {/**/
        // 设置名称，不能重复，可以使用uuid
        String picName = UUID.randomUUID().toString();
        // 获取文件名
        String oriName = file.getOriginalFilename();
        // 获取后缀
        String extName = oriName.substring(oriName.lastIndexOf("."));
        String req="";
        try {
            //获取根路径
             req = request.getSession().getServletContext().getRealPath("/");
            //subReq=req.substring(0,31);
            // 开始上传
            File files =new File(imgFile + "vedio/");
            //如果文件夹不存在则创建
            if  (!files .exists()  && !files .isDirectory())
            {
                System.out.println("//不存在");
                files.mkdir();
            } else
            {
                System.out.println("//目录存在");
            }

            file.transferTo(new File(imgFile + "vedio/" + picName + extName));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Upload up = new Upload();
        up.setCode("0");
        up.setFilename(picName + extName);
        return up;
    }


    /**
     * 添加课件
     * @param addLession
     * @return
     */
    @RequestMapping("addLession")
    public boolean addLession(@RequestBody AddLession addLession){
        return teachinfoService.addLession(addLession);
    }


    /**
     * 添加课节
     * @param addLessions
     * @return
     */
    @RequestMapping("addLessionsJie")
    public boolean addLessionsJie(@RequestBody AddLessions addLessions){
        return teachinfoService.addLessionsJie(addLessions);
    }


    /**
     * 添加小节
     * @param addLittleLes
     * @return
     */
    @RequestMapping("addLittleLes")
    public boolean addLittleLes(@RequestBody AddLittleLes addLittleLes){
        return teachinfoService.addLittleLes(addLittleLes);
    }


    /**
     * 查询培训列表
     * @param cardId
     * @return
     */
    @RequestMapping("queryTrainList")
    public List<OnlineTrain> queryTrainList(String cardId){
       return teachinfoService.queryOnlineTrainDetails(cardId);
    }

    /**
     * 根据培训id查旗下视频
     * @param trainId
     * @return
     */
    @RequestMapping("queryVedioByTrainId")
    public List<TrainVedio> queryVedioByTrainId(Integer trainId,String cardId){
        return teachinfoService.queryVedioByTrainId(trainId,cardId);
    }

    /**
     * 根据身份证查他培训记录
     * @param cardId
     * @return
     */
    @RequestMapping("queryTrainRecord")
    public List<TrainRecord> queryTrainRecord(String cardId, String year){
        return teachinfoService.queryTrainRecord(cardId,year);
    }


    /**
     * 返回个人信息
     * @param cardId
     * @return
     */
    @RequestMapping("oneInfo")
    public StudentInfos oneInfo(String cardId){
        return teachinfoService.queryStuInfor(cardId);
    }

    /**
     * 修改 视频状态
     * @param trainId
     * @param cardId
     * @param vedioId
     * @return
     */
    @RequestMapping("updateVedioStatus")
    public Integer updateVedioStatus(Integer trainId,String cardId,Integer vedioId){
        Integer ok = teachinfoService.updateVedioStatus(trainId,cardId,vedioId);
        if (null != ok && ok >0){
            return 1;
        }else {
            return 0;
        }
    }
    /**
     * 修改 视频播放时长
     * @param trainId
     * @param cardId
     * @param vedioId
     * @return
     */
    @RequestMapping("updateVedioPlayTime")
    public Integer updateVedioPlayTime(Integer trainId,String cardId,Integer vedioId,Integer playTime){
        Integer ok = teachinfoService.updateVedioPlayTime(trainId,cardId,vedioId,playTime);
        System.out.println(ok);
        if (null != ok && ok >0){
            return 1;
        }else {
            return 0;
        }
    }
    /**
     * 查询题目根据培训id
     * @param trainId
     * @return
     */
    @RequestMapping("queryExamQuestion")
    public List<examQuestion> queryExamQuestion(Integer trainId){
        return teachinfoService.queryExamQuestion(trainId);
    }

    /**
     * 评分
     * @param
     * @return
     */
    @RequestMapping("testScore")
    public ExamScoreAndError testScore(@RequestBody Map map) {

        ExamObject result = JSONObject.parseObject(JSONObject.toJSONString(map), ExamObject.class);

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("cardId", result.getReceiveScoreRecord().getStuIds());
        List<Studentinfo> studentinfos = iStudentinfoService.list(queryWrapper);

        if (studentinfos.size() > 0) {
            result.getReceiveScoreRecord().setStuId(studentinfos.get(0).getId());
        }
        return teachinfoService.testScore(result);
    }


    /**
     *
     * @param id
     * @return
     */
    @RequestMapping("deleteTitleById")
    public Integer deleteTitleById(Integer id){
        Integer ok =teachinfoService.deleteTitleById(id);
        if (ok>0){
            return 1;
        }else {
            return 0;
        }
    }

    /**
     * 删除左标题
     * @param id
     * @return
     */
    @RequestMapping("deleteLeftTitleById")
    public Integer deleteLeftTitleById(Integer id){
        Integer ok =teachinfoService.deleteLeftTitleById(id);
        if (ok>0){
            return 1;
        }else {
            return 0;
        }
    }


    /**
     *
     * @param id
     * @param title
     * @return
     */
    @RequestMapping("updateTitle")
    public Integer updateTitle(Integer id,String title,String times){
        Integer ok =teachinfoService.updateTitle(id,title,times);
        if (ok>0){
            return 1;
        }else {
            return 0;
        }
    }

    /**
     *
     * @param id
     * @param title
     * @return
     */
    @RequestMapping("leftUpdateTitle")
    public Integer leftUpdateTitle(Integer id,String title){
        Integer ok =teachinfoService.leftUpdateTitle(id,title);
        if (ok>0){
            return 1;
        }else {
            return 0;
        }
    }

    /**
     * @return
     * @Author zhangduo
     * @Description //TODO 导出模板到excel
     * @Date 10:51 2020/7/27
     * @Param
     **/
    @GetMapping("exportFile")
    public void exportFile(HttpServletResponse response) {
        response.setContentType("application/x-excel;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;fileName=yuangongbumen.xls");
        Workbook workbook = teachinfoService.exportFile();
        try {
            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return
     * @Author zhangduo
     * @Description //TODO 导入excel到数据库
     * @Date 10:51 2020/7/27
     * @Param
     **/
    @PostMapping("importExcelToMySql")
    public ReturnJson importFile(@RequestParam("file") MultipartFile multipartFile, InputStream inputStream) {
        List<BatchQuestions> studentinfoList = null;
        try {
            studentinfoList = teachinfoService.importFile(multipartFile.getInputStream());
            Boolean boo = teachinfoService.saveBatch(studentinfoList);
            return new ReturnJson(200, "导入成功", null);
        } catch (IOException e) {
            e.printStackTrace();
            return new ReturnJson(400, "导入失败", null);
        }
    }

    /**
     * excel导入后修改题目所属课件
     * @return
     */
    @RequestMapping("updateQuestionOfTitle")
    public Integer  updateQuestionOfTitle(Integer id){
       return teachinfoService.updateQuestionOfTitle(id);
    }


    /**
     *
     * @return
     */
    @RequestMapping("deleteQuestion")
    public Integer deleteQuestion(Integer id){
      return   teachinfoService.deleteQuestion(id);
    }


    @RequestMapping("batchAddJie")
    public boolean batchAddJie(@RequestBody TwoObjs twoObjs){

        boolean b = teachinfoService.batchAddJie(twoObjs);
        if (b){
            return true;
        }else {
            return false;
        }


    }









}
