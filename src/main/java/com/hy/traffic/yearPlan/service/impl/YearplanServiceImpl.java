package com.hy.traffic.yearPlan.service.impl;

import com.hy.traffic.studentInfo.entity.Studentinfo;
import com.hy.traffic.yearPlan.entity.Yearplan;
import com.hy.traffic.yearPlan.mapper.YearplanMapper;
import com.hy.traffic.yearPlan.service.IYearplanService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author gwl
 * @since 2020-07-25
 */
@Service
public class YearplanServiceImpl extends ServiceImpl<YearplanMapper, Yearplan> implements IYearplanService {

    @Resource
    private YearplanMapper yearplanMapper;

    @Override
    public List<Yearplan> selectYearplan(Yearplan yearplan) {
        return yearplanMapper.selectYearplan(yearplan);
    }

    @Override
    public List<Yearplan> selectOn(String id) {
        return yearplanMapper.selectOn(id);
    }

    @Override
    public void upd(Yearplan yearplan) {
        yearplanMapper.updateYearplan(yearplan);
    }

    @Override
    public void del(String id) {
        yearplanMapper.del(id);
    }

    @Override
    public void tianjiaYearplan(String title,String bodys) {
         yearplanMapper.tianjiaYearplan(title,bodys);
    }

    @Override
    public Workbook exportFile(){
        Workbook workbook = new HSSFWorkbook();
        Sheet workbookSheet = workbook.createSheet("年计划");
        Row row0 = workbookSheet.createRow(0);
        row0.createCell(0).setCellValue("大纲");
        row0.createCell(1).setCellValue("内容");
        return workbook;
    }
    @Override
    public List<Yearplan> importFile(InputStream inputStream){
        List<Yearplan> mapList = new ArrayList<>();
        try {
            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheetAt(0);
            Row titleRow = sheet.getRow(0);
            int num = sheet.getLastRowNum();
            for (int i = 0; i <num ; i++) {
                Yearplan studentinfo = new Yearplan();
                Row row = sheet.getRow(i+1);
                for (int j = 0; j <row.getLastCellNum(); j++) {
                    Cell cell = row.getCell(j);
                    cell.setCellType(CellType.STRING);
                    String key = titleRow.getCell(j).getStringCellValue();
                    String value = cell.getStringCellValue();
                    System.out.print(key+value);
                    if(key.equals("大纲")){
                        studentinfo.setTitle(value);
                    }else if(key.equals("内容")){
                        studentinfo.setBodys(value);
                    }

                }
                mapList.add(studentinfo);
            }
        } catch (IOException | InvalidFormatException e) {
            e.printStackTrace();
        }
        return mapList;
    }
}
