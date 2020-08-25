package com.hy.traffic.yearPlan.controller;


import com.hy.traffic.studentInfo.entity.Studentinfo;
import com.hy.traffic.studentInfo.utils.ReturnJson;
import com.hy.traffic.yearPlan.entity.Yearplan;
import com.hy.traffic.yearPlan.service.IYearplanService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.time.Year;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author gwl
 * @since 2020-07-25
 */
@RestController
@RequestMapping("/yearPlan/yearplan")
public class YearplanController {

    @Resource
    private IYearplanService yearplanService;

    @CrossOrigin
    @ResponseBody
    @RequestMapping("/selectYearplan")
    public List<Yearplan> selectYearplan(Yearplan yearplan){
        return yearplanService.selectYearplan(yearplan);
    }

    @CrossOrigin
    @ResponseBody
    @RequestMapping("/selectOn")
    public List<Yearplan> selectOn(String id){
        return yearplanService.selectOn(id);
    }

    @CrossOrigin
    @ResponseBody
    @RequestMapping("/tianjiaYearplan")
    public Integer tianjiaYearplan(String title,String bodys){
            /*Yearplan yearplan=new Yearplan();
            yearplan.setTitle(title);*/
           /* yearplan.setBodys(bodys);*/
            yearplanService.tianjiaYearplan(title,bodys);
        return 1;
    }

    @CrossOrigin
    @RequestMapping("/upd")
    public void upd(String title,String bodys,Integer id){
        Yearplan yearplan=new Yearplan();
        yearplan.setTitle(title);
        yearplan.setBodys(bodys);
        yearplan.setId(id);
        yearplanService.upd(yearplan);
    }

    @CrossOrigin
    @RequestMapping("/del")
    public String del(String id){
        yearplanService.del(id);
        return "0";
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
        Workbook workbook = yearplanService.exportFile();
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
        List<Yearplan> yearPlanList = null;
        try {
            yearPlanList = yearplanService.importFile(multipartFile.getInputStream());
            Boolean boo = yearplanService.saveBatch(yearPlanList);
            return new ReturnJson(200, "导入成功", null);
        } catch (IOException e) {
            e.printStackTrace();
            return new ReturnJson(400, "导入失败", null);
        }
    }


}
