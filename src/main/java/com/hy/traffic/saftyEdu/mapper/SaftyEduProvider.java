package com.hy.traffic.saftyEdu.mapper;

public class SaftyEduProvider {

    public String selectSaftyEdu(Integer learnType){
        StringBuffer sf=new StringBuffer();
        if(learnType == 9){
            sf.append("select * from saftyedu where status !=3 order by id desc");
        }else{
            sf.append("select * from saftyedu where status !=3 and learnType="+learnType+" order by id desc");
        }
        return  sf.toString();
    }
}
