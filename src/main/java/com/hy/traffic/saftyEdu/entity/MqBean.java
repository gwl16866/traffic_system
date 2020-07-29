package com.hy.traffic.saftyEdu.entity;

import org.apache.ibatis.annotations.Param;

public class MqBean {

    public String year(@Param("time")String time,@Param("i")Integer i){
        StringBuffer str=new StringBuffer(" select  * from saftyedu where 1=1 ");

        if(time!=null && time!=""){
            str.append(" and date_format(startTime,'%Y')= "+time+"  ");
        }
        if(i!=null){
            str.append(" and month(startTime)= "+i+" ");
        }
        return str.toString();
    }

    public String num(@Param("time")String time,@Param("learnType")Integer learnType){
        StringBuffer str=new StringBuffer(" select  * from saftyedu where 1=1 ");
        System.out.println(time);
        System.out.println(learnType);
            if(time!=null && time!=""){
                str.append(" and date_format(startTime,'%Y')= "+time+"  ");
            }
        if(learnType!=null){
            str.append(" and learnType= '"+learnType+"' ");
        }

        return str.toString();
    }

}
