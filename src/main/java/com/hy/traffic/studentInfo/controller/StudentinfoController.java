package com.hy.traffic.studentInfo.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hy.traffic.studentInfo.entity.Studentinfo;
import com.hy.traffic.studentInfo.service.impl.StudentinfoServiceImpl;
import com.hy.traffic.studentInfo.utils.ReturnJson;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
/**
 * @Author zhangduo
 * @Description //TODO 前端控制器
 * @Date 12:38 2020/7/25
 * @Param
 * @return
 **/
@RestController
@RequestMapping("/studentinfo")
@CrossOrigin
public class StudentinfoController {
    @Autowired
    StudentinfoServiceImpl studentinfoService;

    /**
     * @Author zhangduo
     * @Description //TODO 根据条件查询所有学员信息
     * @Date 12:39 2020/7/25
     * @Param []
     * @return java.util.List<com.hy.traffic.studentInfo.entity.Studentinfo>
     **/
    @RequestMapping("queryAllStudentInFo")
    public IPage<Studentinfo> queryAllStudentInFo(Studentinfo studentinfo, String createTimes, String endTimes, Integer currentPage, Integer pageSize){
        return this.studentinfoService.queryAllStudentInFo(studentinfo,createTimes,endTimes,currentPage,pageSize);
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
        return studentinfoService.insterStudent(studentinfo);
    }

    /**
     * @Author zhangduo
     * @Description //TODO 查询某个学员信息
     * @Date 19:32 2020/7/25
     * @Param
     * @return
     **/
    @RequestMapping("queryStudentById")
    public Studentinfo queryStudentById(Integer id){
        return studentinfoService.queryStudentById(id);
    }

    /**
     * @Author zhangduo
     * @Description //TODO 修改某一个学员信息
     * @Date 12:40 2020/7/25
     * @Param [studentinfo]
     * @return com.hy.traffic.studentInfo.utils.ReturnJson
     **/
    @RequestMapping("updateStudent")
    public ReturnJson updateStudent(Studentinfo studentinfo){
        return studentinfoService.updateStudent(studentinfo);
    }

    /**
     * @Author zhangduo
     * @Description //TODO 修改学员状态信息
     * @Date 12:40 2020/7/25
     * @Param [studentinfo]
     * @return com.hy.traffic.studentInfo.utils.ReturnJson
     **/
    @RequestMapping("updateOneStudent")
    public ReturnJson updateOneStudent(Integer id,Integer status){
        return studentinfoService.updateOneStudent(id,status);
    }

    /**
     * @Author zhangduo
     * @Description //TODO 导入excel到数据库
     * @Date 10:51 2020/7/27
     * @Param
     * @return
     **/
    @PostMapping("importExcelToMySql")
    public ReturnJson importFile(@RequestParam("file") MultipartFile multipartFile, InputStream inputStream){
        List<Studentinfo> studentinfoList = null;
        try {
            studentinfoList = studentinfoService.importFile(multipartFile.getInputStream());
            Boolean boo = studentinfoService.saveBatch(studentinfoList);
            return new ReturnJson(200,"导入成功",null);
        } catch (IOException e) {
            e.printStackTrace();
            return new ReturnJson(400,"导入失败",null);
        }
    }
    /**
     * @Author zhangduo
     * @Description //TODO 导出模板到excel
     * @Date 10:51 2020/7/27
     * @Param
     * @return
     **/
    @GetMapping("exportFile")
    public void exportFile(HttpServletResponse response){
        response.setContentType("application/x-excel;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;fileName=yuangongbumen.xls");
        Workbook workbook = studentinfoService.exportFile();
        try {
            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
