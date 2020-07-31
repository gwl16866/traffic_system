package com.hy.traffic.studentInfo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hy.traffic.studentInfo.entity.Info;
import com.hy.traffic.studentInfo.entity.Studentinfo;
import com.hy.traffic.studentInfo.service.impl.StudentinfoServiceImpl;
import com.hy.traffic.studentInfo.utils.ReturnJson;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
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
     * @return java.util.List<com.hy.traffic.studentInfo.entity.Studentinfo>
     * @Author zhangduo
     * @Description //TODO 根据条件查询所有学员信息
     * @Date 12:39 2020/7/25
     * @Param []
     **/
    @RequestMapping("queryAllStudentInFo")
    public IPage<Studentinfo> queryAllStudentInFo(Studentinfo studentinfo, String createTimes, String endTimes, Integer currentPage, Integer pageSize) {
        return this.studentinfoService.queryAllStudentInFo(studentinfo, createTimes, endTimes, currentPage, pageSize);
    }

    /**
     * @return com.hy.traffic.studentInfo.utils.ReturnJson
     * @Author zhangduo
     * @Description //TODO 批量新增学员
     * @Date 12:40 2020/7/25
     * @Param [studentinfoList]
     **/
    @RequestMapping("insterStudent")
    public ReturnJson insterStudent(Studentinfo studentinfo) {
        return studentinfoService.insterStudent(studentinfo);
    }

    /**
     * @return
     * @Author zhangduo
     * @Description //TODO 查询某个学员信息
     * @Date 19:32 2020/7/25
     * @Param
     **/
    @RequestMapping("queryStudentById")
    public Studentinfo queryStudentById(Integer id) {
        return studentinfoService.queryStudentById(id);
    }

    /**
     * @return com.hy.traffic.studentInfo.utils.ReturnJson
     * @Author zhangduo
     * @Description //TODO 修改某一个学员信息
     * @Date 12:40 2020/7/25
     * @Param [studentinfo]
     **/
    @RequestMapping("updateStudent")
    public ReturnJson updateStudent(Studentinfo studentinfo) {
        return studentinfoService.updateStudent(studentinfo);
    }

    /**
     * @return com.hy.traffic.studentInfo.utils.ReturnJson
     * @Author zhangduo
     * @Description //TODO 修改学员状态信息
     * @Date 12:40 2020/7/25
     * @Param [studentinfo]
     **/
    @RequestMapping("updateOneStudent")
    public ReturnJson updateOneStudent(Integer id, Integer status) {
        return studentinfoService.updateOneStudent(id, status);
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
        List<Studentinfo> studentinfoList = null;
        try {
            studentinfoList = studentinfoService.importFile(multipartFile.getInputStream());
            Boolean boo = studentinfoService.saveBatch(studentinfoList);
            return new ReturnJson(200, "导入成功", null);
        } catch (IOException e) {
            e.printStackTrace();
            return new ReturnJson(400, "导入失败", null);
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
        Workbook workbook = studentinfoService.exportFile();
        try {
            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/denglu")
    public Info denglu(String cardId, String password) {
        Info info = new Info();
        Studentinfo studentinfo = studentinfoService.selectStudentInfo(cardId, password);

        if (!StringUtils.isEmpty(studentinfo)) {
            if (!StringUtils.isEmpty(studentinfo.getId())) {
                info.setCode(200);
                info.setMessage("登录成功");
                if (StringUtils.isEmpty(studentinfo.getHeadImg())) {
                    info.setMessage("暂无头像");
                } else {
                    info.setMessage("头像已上传");
                }
            } else {
                info.setCode(400);
                info.setMessage("登录失败");
                return info;
            }
        }
        return info;
    }

    //修改密码
    @RequestMapping("updatePassword")
    public Info updatePassword(Integer id, String password) {
        Info info = new Info();
        try {
            studentinfoService.updatePassword(id, password);
            info.setCode(200);
            info.setMessage("修改成功");
        }catch (Exception e){
            info.setCode(400);
            info.setMessage("修改失败");
        }
        return info;
    }

}
