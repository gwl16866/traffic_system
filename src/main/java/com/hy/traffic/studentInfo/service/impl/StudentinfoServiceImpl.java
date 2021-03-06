package com.hy.traffic.studentInfo.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.util.StringUtil;
import com.hy.traffic.studentInfo.entity.Answer;
import com.hy.traffic.studentInfo.entity.Studentinfo;
import com.hy.traffic.studentInfo.entity.Studentxiangqing;
import com.hy.traffic.studentInfo.mapper.AnswerMapper;
import com.hy.traffic.studentInfo.mapper.StudentinfoMapper;
import com.hy.traffic.studentInfo.service.IStudentinfoService;
import com.hy.traffic.studentInfo.utils.ReturnJson;
import com.hy.traffic.teachInfo.mapper.TeachinfoMapper;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.io.InputStream;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author zhangduo
 * @Description //TODO 学员服务实现类
 * @Date 13:12 2020/7/25
 * @Param
 * @return
 **/
@Service
public class StudentinfoServiceImpl extends ServiceImpl<StudentinfoMapper, Studentinfo> implements IStudentinfoService {

    @Autowired
    StudentinfoMapper studentinfoMapper;

    @Autowired
    AnswerMapper answerMapper;

    @Autowired
    private TeachinfoMapper mapper;
    /**
     * @Author zhangduo
     * @Description //TODO 根据条件查询所有学员信息
     * @Date 13:13 2020/7/25
     * @Param []
     * @return java.util.List<com.hy.traffic.studentInfo.entity.Studentinfo>
     **/
    public IPage<Studentinfo> queryAllStudentInFo(Studentinfo studentinfo, String createTimes, String endTimes, Integer currentPage, Integer pageSize){
        if(!StringUtils.isEmpty(createTimes) && !StringUtils.isEmpty(endTimes)){
            DateTimeFormatter dt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            studentinfo.setCreateTime(LocalDateTime.parse(createTimes.replace("T"," "),dt));
            studentinfo.setEndTime(LocalDateTime.parse(endTimes.replace("T"," "),dt));
        }
        return this.studentinfoMapper.queryAllStudentInFo(new Page<>(currentPage,pageSize),studentinfo);
    }

    /**
     * @Author zhangduo
     * @Description //TODO 批量新增学员
     * @Date 12:40 2020/7/25
     * @Param [studentinfoList]
     * @return com.hy.traffic.studentInfo.utils.ReturnJson
     **/
    @RequestMapping("insterStudent")
    public ReturnJson insterStudent(Studentinfo studentinfo){
        if (!StringUtils.isEmpty(studentinfo) && !StringUtils.isEmpty(studentinfo.getCardId())) {
            Integer stuCount = studentinfoMapper.queryByCardId(studentinfo);
            if(stuCount>0){return new ReturnJson(200, "该身份证号已经存在", null);}
            DateTimeFormatter dt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            if (!StringUtils.isEmpty(studentinfo.getBusCarefulTimes())) {
                studentinfo.setBusCarefulTime(LocalDateTime.parse(studentinfo.getBusCarefulTimes().replace("T", " "), dt));
            }
            if (!StringUtils.isEmpty(studentinfo.getDriverOverTimes())) {
                studentinfo.setDriverOverTime(LocalDateTime.parse(studentinfo.getDriverOverTimes().replace("T", " "), dt));
            }
            if (!StringUtils.isEmpty(studentinfo.getInductions())) {
                studentinfo.setInduction(LocalDateTime.parse(studentinfo.getInductions().replace("T", " "), dt));
            }
            studentinfo.setStatus(4);
            studentinfo.setCompanyName("枣阳市光武石化运输有限公司");
            studentinfo.setCreateTime(LocalDateTime.now());
            studentinfo.setHeadImgStatus(4);
            studentinfo.setPassword(studentinfo.getCardId().substring(12));
            Integer boo = studentinfoMapper.insert(studentinfo);
            if (boo < 0) {
                return new ReturnJson(400, "添加失败", null);
            }
        }else{
            return new ReturnJson(200, "信息未填写完整", null);
        }
        return new ReturnJson(200, "添加成功", null);
    }

    /**
     * @Author zhangduo
     * @Description //TODO 查询某个学员信息
     * @Date 19:32 2020/7/25
     * @Param
     * @return
     **/
    @RequestMapping()
    public Studentinfo queryStudentById(Integer id){
        return studentinfoMapper.selectById(id);
    }

    /**
     * @Author zhangduo
     * @Description //TODO 修改学员信息
     * @Date 13:13 2020/7/25
     * @Param [studentinfo]
     * @return com.hy.traffic.studentInfo.utils.ReturnJson
     **/
    public ReturnJson updateStudent(Studentinfo studentinfo){
        if(!StringUtils.isEmpty(studentinfo)){
            if(!studentinfo.getCardId().equals(studentinfo.getStudentOCardId())){
                System.out.println(studentinfo.getCardId().equals(studentinfo.getStudentOCardId())+"==================");
                Integer stuCount = studentinfoMapper.queryByCardId(studentinfo);
                if(stuCount>0){
                    return new ReturnJson(400,"该身份证号已经存在",null);
                }
            }
            DateTimeFormatter dt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            if(!StringUtils.isEmpty(studentinfo.getCreateTimes())){
                studentinfo.setCreateTime(LocalDateTime.parse(studentinfo.getCreateTimes().replace("T"," "),dt));
            }
            if(!StringUtils.isEmpty(studentinfo.getBusCarefulTimes())){
                studentinfo.setBusCarefulTime(LocalDateTime.parse(studentinfo.getBusCarefulTimes().replace("T"," "),dt));
            }
            if(!StringUtils.isEmpty(studentinfo.getDriverOverTimes())){
                studentinfo.setDriverOverTime(LocalDateTime.parse(studentinfo.getDriverOverTimes().replace("T"," "),dt));
            }
            if(!StringUtils.isEmpty(studentinfo.getInductions())){
                studentinfo.setInduction(LocalDateTime.parse(studentinfo.getInductions().replace("T"," "),dt));
            }
            int boo = studentinfoMapper.updateById(studentinfo);
            if( boo < 1 ){
                return new ReturnJson(400,"修改失败",null);
            }
        }else{
            return new ReturnJson(200, "信息未填写完整", null);
        }
        return new ReturnJson(200,"修改成功",null);
    }
    /**
     * @Author zhangduo
     * @Description //TODO 审核学员信息
     * @Date 13:13 2020/7/25
     * @Param [studentinfo]
     * @return com.hy.traffic.studentInfo.utils.ReturnJson
     **/
    public ReturnJson shenHeStudent(Studentinfo studentinfo){
        int boo = studentinfoMapper.updateById(studentinfo);
        if( boo == 1 ){
            return new ReturnJson(200,"审核成功",null);
        }else {
            return new ReturnJson(400,"审核失败",null);
        }
    }


    /**
     * @Author zhangduo
     * @Description //TODO 修改学员信息
     * @Date 13:13 2020/7/25
     * @Param [studentinfo]
     * @return com.hy.traffic.studentInfo.utils.ReturnJson
     **/
    public ReturnJson updateOneStudent(Integer id,Integer status){
        int boo = studentinfoMapper.updateOneStudent(id,status);
        if( boo == 1 ){
            return new ReturnJson(200,"激活成功",null);
        }else {
            return new ReturnJson(400,"激活失败",null);
        }
    }

    /**
     * @Author zhangduo
     * @Description //TODO 导入excel到数据库
     * @Date 10:52 2020/7/27
     * @Param
     * @return
     **/
    public ReturnJson importFile(InputStream inputStream){
        List<Studentinfo> mapList = new ArrayList<>();
        try {
            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheetAt(0);
            Row titleRow = sheet.getRow(0);
            int num = sheet.getLastRowNum();
            for (int i = 0; i <num ; i++) {
                Studentinfo studentinfo = new Studentinfo();
                Row row = sheet.getRow(i+1);
                for (int j = 0; j <row.getLastCellNum(); j++) {
                    Cell cell = row.getCell(j);
                    cell.setCellType(CellType.STRING);
                    String key = titleRow.getCell(j).getStringCellValue();
                    String value = cell.getStringCellValue();
                    System.out.print(key+value);
                    studentinfo.setJobType("客运");
                    studentinfo.setJobName("驾驶员");
                    if(key.equals("学员姓名")){
                        studentinfo.setRealName(value);
                    }else if(key.equals("身份证号")){
                        Integer stuCount = studentinfoMapper.queryByCardIdstu(value);
                        if(stuCount>0){
                            return new ReturnJson(400,"批量导入有身份证号已经存在",null);
                        }
                        studentinfo.setCardId(value);
                        if(StringUtil.isNotEmpty(value) && value.length()>12){
                            studentinfo.setPassword(value.substring(12));
                        }
                    }else if(key.equals("联系电话")){
                        studentinfo.setLinkNum(value);
                    }else if(key.equals("车牌号码")){
                        studentinfo.setBusNum(value);
                    }else if(key.equals("联系地址")){
                        studentinfo.setLinkAddress(value);
                    }else if(key.equals("岗位名称")){
                        studentinfo.setJobName(value);
                    }
                    studentinfo.setCreateTime(LocalDateTime.now());
                    studentinfo.setStatus(0);
                    studentinfo.setHeadImgStatus(4);
                    studentinfo.setCompanyName("枣阳市光武石化运输有限公司");
                }
                mapList.add(studentinfo);
            }
            Boolean boo = new StudentinfoServiceImpl().saveBatch(mapList);
            if(!boo){return new ReturnJson(200, "导入失败", null);}
        } catch (IOException | InvalidFormatException e) {
            e.printStackTrace();
        }
        return new ReturnJson(200, "导入成功", null);
    }

    public Workbook exportFile(){
        Workbook workbook = new HSSFWorkbook();
        Sheet workbookSheet = workbook.createSheet("学员信息登记");
        Row row0 = workbookSheet.createRow(0);
        row0.createCell(0).setCellValue("学员姓名");
        row0.createCell(1).setCellValue("身份证号");
        row0.createCell(2).setCellValue("联系电话");
        row0.createCell(3).setCellValue("车牌号码");
        row0.createCell(4).setCellValue("联系地址");
        row0.createCell(5).setCellValue("岗位名称");

        Row row1 = workbookSheet.createRow(1);
        row1.createCell(0).setCellValue("张三");
        row1.createCell(1).setCellValue("485748596214563258");
        row1.createCell(2).setCellValue("15666666666");
        row1.createCell(3).setCellValue("鄂F55555");
        row1.createCell(4).setCellValue("湖北");
        row1.createCell(5).setCellValue("危货驾驶员");
        return workbook;
    }


    public Studentinfo selectStudentInfo(String cardId,String password){
        return  studentinfoMapper.selectStudentInfo(cardId,password);
    }

    public void updatePassword(String id,String password){
        Integer ids = mapper.queryIdByCarId(String.valueOf(id));
         studentinfoMapper.updatePassword(ids, password);
    }

    public List<Studentxiangqing> studentxiangqing(Integer id){
        return studentinfoMapper.studentxiangqing(id);
    }

    public List<Studentxiangqing> studentxiangqing2(Integer id,String aaa,String realName){
        return studentinfoMapper.studentxiangqing2(id,aaa,realName);
    }

    public Integer[] stuid(Integer id){
        return studentinfoMapper.stuid(id);
    }

    public Studentxiangqing stuentmq(Integer id,String aaa,String realName){
        return studentinfoMapper.stuentmq(id,aaa,realName);
    }
    public List<Answer> queryallAnswer(Integer id){
        return answerMapper.queryallanswer(id);
    }

    public Integer querySumPlayTime(Integer saftyId,Integer studentId){
//        DecimalFormat f = new DecimalFormat("0");
//        f.setRoundingMode(RoundingMode.HALF_UP);
        return studentinfoMapper.querySumPlayTime(saftyId,studentId);
    };
}