package com.hy.traffic.saftyEdu.entity;

import org.apache.ibatis.annotations.Param;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MqBean {

    public String year(@Param("time")String time,@Param("i")Integer i,@Param("completion")Integer completion,@Param("learnType")Integer learnType){
        StringBuffer str=new StringBuffer(" select  count(1)  from saftydustudentinfo where 1=1 ");

            str.append("and saftyid in(select  id from saftyedu where date_format(startTime,'%Y')= "+time+"  ");

            if(i!=null){
                str.append(" and month(startTime)= "+i+" ");
            }
            if(learnType!=null){
                str.append(" and learnType="+learnType+" ");
            }
            str.append(" ) ");
            if(completion!=null){
                str.append(" and completion="+completion+"");
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


    public static String time(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        Date date=new Date();
        String time=sdf.format(date);

        return time;
    }



}
