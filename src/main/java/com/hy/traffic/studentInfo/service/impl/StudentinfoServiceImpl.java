package com.hy.traffic.studentInfo.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hy.traffic.studentInfo.entity.ExportFile;
import com.hy.traffic.studentInfo.entity.Studentinfo;
import com.hy.traffic.studentInfo.mapper.StudentinfoMapper;
import com.hy.traffic.studentInfo.service.IStudentinfoService;
import com.hy.traffic.studentInfo.utils.ReturnJson;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.io.InputStream;
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
        DateTimeFormatter dt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        if(!StringUtils.isEmpty(studentinfo.getBusCarefulTimes())){
            studentinfo.setBusCarefulTime(LocalDateTime.parse(studentinfo.getBusCarefulTimes().replace("T"," "),dt));
        }
        if(!StringUtils.isEmpty(studentinfo.getDriverOverTimes())){
            studentinfo.setDriverOverTime(LocalDateTime.parse(studentinfo.getDriverOverTimes().replace("T"," "),dt));
        }
        if(!StringUtils.isEmpty(studentinfo.getInductions())){
            studentinfo.setInduction(LocalDateTime.parse(studentinfo.getInductions().replace("T"," "),dt));
        }
        studentinfo.setStatus(4);
        studentinfo.setCompanyName("枣阳市光武石化运输有限公司");
        studentinfo.setCreateTime(LocalDateTime.now());
        studentinfo.setHeadImgStatus(4);
        Integer boo = studentinfoMapper.insert(studentinfo);
        if( boo>0 ){
            return new ReturnJson(200,"添加成功",null);
        }else {
            return new ReturnJson(400,"添加失败",null);
        }
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
        if( boo == 1 ){
            return new ReturnJson(200,"修改成功",null);
        }else {
            return new ReturnJson(400,"修改失败",null);
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
    public List<Studentinfo> importFile(InputStream inputStream){
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
                    if(key.equals("学员姓名")){
                        studentinfo.setRealName(value);
                    }else if(key.equals("身份证号")){
                        studentinfo.setCardId(value);
                    }else if(key.equals("联系电话")){
                        studentinfo.setLinkNum(value);
                    }else if(key.equals("车牌号码")){
                        studentinfo.setBusNum(value);
                    }else if(key.equals("联系地址")){
                        studentinfo.setLinkAddress(value);
                    }
                    studentinfo.setCreateTime(LocalDateTime.now());
                    studentinfo.setStatus(0);
                    studentinfo.setHeadImgStatus(4);
                    studentinfo.setCompanyName("枣阳市光武石化运输有限公司");
                }
                mapList.add(studentinfo);
            }
        } catch (IOException | InvalidFormatException e) {
            e.printStackTrace();
        }
        return mapList;
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
        return workbook;
    }
}