package com.hy.traffic.studentaccmq.entity;

import org.apache.ibatis.annotations.Param;

public class StudentaccMq {

    public String thenumber(@Param("studentid")String studentid,@Param("saftyid")Integer saftyid,@Param("completion")Integer completion){
        StringBuffer str=new StringBuffer(" select count(1) from studentacc where 1=1 " );

        if(studentid!=null && studentid!=""){
            str.append(" and studentid in ( "+studentid+" ) ");
        }

        if(saftyid!=null){
            str.append(" and saftyid= "+saftyid+" ");
        }
        if(completion!=null){
            str.append(" and completion= "+completion+" ");
        }
        return str.toString();
    }
}
