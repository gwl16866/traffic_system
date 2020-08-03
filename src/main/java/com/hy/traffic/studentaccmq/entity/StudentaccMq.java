package com.hy.traffic.studentaccmq.entity;

import org.apache.ibatis.annotations.Param;

public class StudentaccMq {

    public String thenumber(@Param("time")String time,@Param("i")Integer i,@Param("completion")Integer completion,@Param("learnType")Integer learnType,@Param("id")Integer id){
        StringBuffer str=new StringBuffer(" select  count(1)  from saftydustudentinfo where 1=1 ");

        str.append("and saftyid in(select  id from saftyedu where date_format(startTime,'%Y')= "+time+"  ");

        if(i!=null){
            str.append(" and month(startTime)= "+i+" ");
        }
        if(learnType!=null){
            str.append(" and learnType="+learnType+" ");
        }

        if(id!=null){
            str.append(" and id="+id+"");
        }
        str.append(" ) ");
        if(completion!=null){
            str.append(" and completion="+completion+"");
        }
        return str.toString();
    }
}
