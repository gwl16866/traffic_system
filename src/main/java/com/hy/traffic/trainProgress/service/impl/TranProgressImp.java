package com.hy.traffic.trainProgress.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hy.traffic.trainProgress.entity.StudentInfoEntity;
import com.hy.traffic.trainProgress.entity.Teachinfo;
import com.hy.traffic.trainProgress.mapper.TrainProgressMapper;
import com.hy.traffic.trainProgress.service.ITranProgressService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Transactional
public class TranProgressImp extends ServiceImpl<TrainProgressMapper, Teachinfo> implements ITranProgressService {
    @Autowired
    private TrainProgressMapper trainProgressMapper;



    public int selectok(String student,Integer completion,Integer saftyid){
     return    trainProgressMapper.selectok(student,completion,saftyid);
    }

    //查询安全教育的所有年份
    public List<Teachinfo> selectYear(){
       return trainProgressMapper.selectYear();
    };

    //  根据年份及月份查询条件内的数据
    public List<Teachinfo> selectByTime(String Year,String Month){
        if(!StringUtils.isEmpty(Year) && !StringUtils.isEmpty(Month)){
            if(Month.length()<=1){
                Month="0"+Month;
            }
        }
        return trainProgressMapper.selectByTime(Year+"-"+Month);
    };

    //    进度
//    查询某一年月里产与培训的总人数
    public Integer allNum(String Year,String Month){
        String yearMonth=null;
        if(!StringUtils.isEmpty(Year) && !StringUtils.isEmpty(Month)){
            if(Month.length()<=1){
                Month="-0"+Month;
                yearMonth=Year+Month;
            }
        }else {
            yearMonth= LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM"));
        }
        return trainProgressMapper.allNum(yearMonth);
    };

    //    查询某一年月里完成培训的人数
    public Integer successNum(String Year,String Month){
        String yearMonth=null;
        if(!StringUtils.isEmpty(Year) && !StringUtils.isEmpty(Month)){
            if(Month.length()<=1){
                Month="-0"+Month;
                yearMonth=Year+Month;
            }
        }else {
            yearMonth= LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM"));
        }
        return trainProgressMapper.successNum( yearMonth);
    };

    //     查询某一年月中某一培训主题的总人数
    public String themeAllNum(String YearMonth,String theme){
        return trainProgressMapper.themeAllNum(YearMonth,theme);
    };

    //     查询某一年月中某一培训主题的完成培训的人数
    public String successThemeNum(String YearMonth,String theme){
        return trainProgressMapper.successThemeNum(YearMonth,theme);
    };

    //    参训学员
    //    查询查询学员的信息
    public List<StudentInfoEntity> SelectCXInfo(Integer saftyid){
        return trainProgressMapper.SelectCXInfo(saftyid);
    };

    //    模糊查询学员的信息
    public List<StudentInfoEntity> LikeCXInfo(String selects,Integer saftyid,String inputs,Integer oneStatus){
        return trainProgressMapper.LikeCXInfo(selects,saftyid,inputs,oneStatus);
    };

    //    将table数据导出到Excel中
    public void exportExcel(HttpServletResponse resp, String Year,String Month){
        resp.setHeader("Content-Disposition", "attachment;filename=text.xls");
        resp.setContentType("application/x-excel");
//      创建Excel
        Workbook workbook=new HSSFWorkbook();
//        创建一张培训进度表
        workbook.createSheet("培训进度表");
        Sheet sheet=workbook.getSheetAt(0);//从第零行开始（设置表头）
        Row oneRow=sheet.createRow(0) ;
        oneRow.createCell(0).setCellValue("培训方式");
        oneRow.createCell(1).setCellValue("培训主题");
        oneRow.createCell(2).setCellValue("培训时间");
        oneRow.createCell(3).setCellValue("培训科目");
        oneRow.createCell(4).setCellValue("培训时长");
        oneRow.createCell(5).setCellValue("培训人数");
        oneRow.createCell(6).setCellValue("未完成人数");
        oneRow.createCell(7).setCellValue("未完成率");

        String startTime=null;
        if(!StringUtils.isEmpty(Year) && !StringUtils.isEmpty(Month)){
            if(Month.length()<=1){
                Month="-0"+Month;
                startTime=Year+Month;
            }
        }
        System.out.println("============"+startTime);
        List<Teachinfo> list=trainProgressMapper.selectExcel(startTime);

        for (int i = 0; i <list.size(); i++) {
//            创建一行数据
            Row towRow=sheet.createRow(i+1);
            towRow.createCell(0).setCellValue(list.get(i).getLearnType());
            towRow.createCell(1).setCellValue(list.get(i).getTheme());
            towRow.createCell(2).setCellValue(list.get(i).getTimes());
            towRow.createCell(3).setCellValue(list.get(i).getProject());
            towRow.createCell(4).setCellValue(list.get(i).getLearnTime());
            towRow.createCell(5).setCellValue(list.get(i).getAllproper());
            towRow.createCell(6).setCellValue(list.get(i).getOk());
            towRow.createCell(7).setCellValue(list.get(i).getBaifenbi()+"%");
        }
        try {
            workbook.write(resp.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
