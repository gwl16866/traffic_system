package com.hy.traffic.trainProgress.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hy.traffic.trainProgress.entity.Teachinfo;
import com.hy.traffic.trainProgress.entity.Vedio;
import com.hy.traffic.trainProgress.mapper.TrainProgressMapper;
import com.hy.traffic.trainProgress.service.ITranProgressService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TranProgressImp extends ServiceImpl<TrainProgressMapper, Teachinfo> implements ITranProgressService {
    @Autowired
    private TrainProgressMapper trainProgressMapper;
    @Value("${img.vedioPath}")
    private String vedioPath;

    /**
     * @Author zhangduo
     * @Description //TODO 查询所有主体信息
     * @Date 10:08 2020/8/1
     * @Param [YearMonth]
     * @return java.util.List<com.hy.traffic.trainProgress.entity.Teachinfo>
     **/
    public List<Teachinfo> queryThemTable(String Year,String Month){
        if(!StringUtils.isEmpty(Year) && !StringUtils.isEmpty(Month)){
            if(Month.length()<=1){
                Month="0"+Month;
            }
        }
        return trainProgressMapper.queryThemTable(Year+"-"+Month);
    }

    /**
     * @Author zhangduo
     * @Description //TODO 查询当前年当前月的总完成数/已完成数
     * @Date 11:48 2020/8/1
     * @Param []
     * @return java.util.List<com.hy.traffic.trainProgress.entity.Teachinfo>
     **/
    public Teachinfo queryAllProperAndOkProper(String Year,String Month){
        if(!StringUtils.isEmpty(Year) && !StringUtils.isEmpty(Month)){
            if(Month.length()<=1){
                Month="0"+Month;
            }
        }
        Teachinfo teachinfo = trainProgressMapper.queryAllProperAndOkProper(Year+"-"+Month);
        return teachinfo;
    }

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
        oneRow.createCell(4).setCellValue("培训人数");
        oneRow.createCell(5).setCellValue("完成人数");
        oneRow.createCell(6).setCellValue("未完成率");

        String startTime=null;
        if(!StringUtils.isEmpty(Year) && !StringUtils.isEmpty(Month)){
            if(Month.length()<=1){
                Month="-0"+Month;
                startTime=Year+Month;
            }
        }
        List<Teachinfo> list=trainProgressMapper.queryThemTable(startTime);
        if(!StringUtils.isEmpty(list)){
            for (int i = 0; i <list.size(); i++) {
                float okpro = Float.parseFloat(String.valueOf(list.get(i).getOkProper()));
                float allpro = Float.parseFloat(String.valueOf(list.get(i).getAllProper()));
//            创建一行数据
                Row towRow=sheet.createRow(i+1);
                towRow.createCell(0).setCellValue(list.get(i).getLearnType()==1?"线上培训":"线下培训");
                towRow.createCell(1).setCellValue(list.get(i).getTheme());
                towRow.createCell(2).setCellValue(list.get(i).getStartTime()+"~"+list.get(i).getEndTime());
                towRow.createCell(3).setCellValue(list.get(i).getProject());
                towRow.createCell(4).setCellValue(list.get(i).getAllProper());
                towRow.createCell(5).setCellValue(list.get(i).getOkProper());
                towRow.createCell(6).setCellValue(String.format("%.2f",(okpro/allpro)*100)+"%");
            }
        }
            try {
                workbook.write(resp.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    //查询安全教育的所有年份
    public List<Teachinfo> selectYear(){
        return trainProgressMapper.selectYear();
    }

    /**
     * @Author zhangduo
     * @Description //TODO 查询某个主题的课程
     * @Date 15:27 2020/8/1
     * @Param
     * @return
     **/
    public List<Vedio> queryAllPeiXunClass(Integer Id){
        List<Vedio> list=trainProgressMapper.queryAllPeiXunClass(Id);
        list=list.stream().map(e->{
            e.setVedio(vedioPath+e.getVedio());
            return e;
        }).collect(Collectors.toList());
        return list;
    };

}
