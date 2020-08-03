package com.hy.traffic.teachInfo.controller;


import com.hy.traffic.teachInfo.entity.*;
import com.hy.traffic.teachInfo.service.ITeachinfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;
import java.util.UUID;

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
            File files =new File(req + "imgs/");
            //如果文件夹不存在则创建
            if  (!files .exists()  && !files .isDirectory())
            {
                System.out.println("//不存在");
                files.mkdir();
            } else
            {
                System.out.println("//目录存在");
            }
            file.transferTo(new File(req + "imgs/" + picName + extName));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Upload up = new Upload();
        up.setCode("0");
        up.setFilename("http://localhost:8081/imgs/" +picName + extName);
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
    public List<TrainVedio> queryVedioByTrainId(Integer trainId){
        return teachinfoService.queryVedioByTrainId(trainId);
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
     * @param examObject
     * @return
     */
    @RequestMapping("testScore")
    public Integer testScore(@RequestBody ExamObject examObject){
      return   teachinfoService.testScore(examObject);
    }










}
