package com.hy.traffic.studentInfo.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hy.traffic.managerMan.entity.Manager;
import com.hy.traffic.managerMan.service.IManagerService;
import com.hy.traffic.studentInfo.entity.Info;
import com.hy.traffic.studentInfo.entity.Studentinfo;
import com.hy.traffic.studentInfo.service.IStudentinfoService;
import com.hy.traffic.studentInfo.service.impl.StudentinfoServiceImpl;
import com.hy.traffic.studentInfo.utils.ReturnJson;
import com.hy.traffic.teachInfo.entity.Upload;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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

    @Autowired
    IStudentinfoService iStudentinfoService;
    @Autowired
    IManagerService iManagerService;

    @Value("${img.filePath}")
    String imgFilePath;

    @Value("${img.file}")
    String imgFile;
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
    public ReturnJson updateStudent(@RequestBody Map map) {
        Studentinfo studentinfo=JSONObject.parseObject(JSONObject.toJSONString(map.get("params")),Studentinfo.class);
        return studentinfoService.updateStudent(studentinfo);
    }

    /**
     * @return com.hy.traffic.studentInfo.utils.ReturnJson
     *      * @Author zhangduo
     *      * @Description //TODO 审核某一个学员信息
     *      * @Date 12:40 2020/7/25
     *      * @Param [studentinfo]
     */
    @RequestMapping("shenHeStudent")
    public ReturnJson shenHeStudent(@RequestBody Map map) {
        Studentinfo studentinfo=JSONObject.parseObject(JSONObject.toJSONString(map.get("params")),Studentinfo.class);
        return studentinfoService.shenHeStudent(studentinfo);
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
        try {
            return studentinfoService.importFile(multipartFile.getInputStream());
        } catch (IOException e) {
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
                if(studentinfo.getStatus()==0 || studentinfo.getStatus()==2){
                    info.setCode(400);
                    info.setMessage("账号不可用");
                    return info;
                }
                info.setCode(200);
                info.setMessage("登录成功");
                if (StringUtils.isEmpty(studentinfo.getHeadImg()) ||String.valueOf(studentinfo.getHeadImgStatus()).equals("6")) {
                    info.setMessage("暂无头像");
                } else {
                    info.setMessage("头像已上传");
                }
            } else {
                info.setCode(400);
                info.setMessage("登录失败");
                return info;
            }
        }else {
//            QueryWrapper queryWrapper=new QueryWrapper();
//            queryWrapper.eq("cardId",cardId);
//            List<Manager> managers=iManagerService.list(queryWrapper);
//            if(managers.size()>0 && password.equals(cardId.substring(12))){
//                info.setCode(200);
//                info.setMessage("头像已上传");
//                return info;
//            }
            info.setCode(400);
            info.setMessage("账号或密码错误");
        }
        return info;
    }

    //修改密码
    @RequestMapping("updatePassword")
    public Info updatePassword(String cardId, String password) {
        Info info = new Info();
        try {
            studentinfoService.updatePassword(cardId, password);
            info.setCode(200);
            info.setMessage("修改成功");
        }catch (Exception e){
            info.setCode(400);
            info.setMessage("修改失败");
        }
        return info;
    }

    //添加页面——上传文件
    @RequestMapping(value = "uploadFile")
    public Upload uploadFile(@RequestParam("file") MultipartFile file, HttpServletRequest request,String cardId) {/**/
        // 设置名称，不能重复，可以使用uuid
        String picName = UUID.randomUUID().toString();
        // 获取文件名
        String oriName = file.getOriginalFilename();
        // 获取后缀
        String extName = oriName.substring(oriName.lastIndexOf("."));
        String req="";

        Upload up = new Upload();
        up.setCode("0");

        try {
            //获取根路径
            req = request.getSession().getServletContext().getRealPath("/");
            //subReq=req.substring(0,31);
            // 开始上传
            File files =new File(imgFile + "imgs/");
            //如果文件夹不存在则创建
            if  (!files .exists()  && !files .isDirectory())
            {
                System.out.println("//不存在");
                files.mkdir();
            } else
            {
                System.out.println("//目录存在");
            }
            file.transferTo(new File(imgFile + "imgs/" + picName + extName));
            QueryWrapper queryWrapper=new QueryWrapper();
            queryWrapper.eq("cardId",cardId);
            List<Studentinfo> studentinfos = iStudentinfoService.list(queryWrapper);
            if (studentinfos.size() > 0) {
                Studentinfo studentinfo = studentinfos.get(0);
                studentinfo.setHeadImg(imgFilePath + picName + extName);
                iStudentinfoService.updateById(studentinfo);
            }

        } catch (Exception e) {
            e.printStackTrace();
            up.setCode("400");
        }
        up.setFilename(imgFilePath +picName + extName);
        return up;
    }

}
