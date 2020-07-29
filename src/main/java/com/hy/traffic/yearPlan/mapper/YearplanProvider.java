package com.hy.traffic.yearPlan.mapper;

import com.hy.traffic.yearPlan.entity.Yearplan;
import org.apache.ibatis.annotations.Param;

public class YearplanProvider {
    /*
     * 根据大纲查询
     * */
    public String selectYearplan(Yearplan yearplan){
        StringBuffer buffer = new StringBuffer("select * from yearplan where 1=1");
        if (yearplan.getTitle() != null && yearplan.getTitle() != "") {
            buffer.append(" and title = '"+yearplan.getTitle()+"' ");
        }
        return buffer.toString();
    }


}
