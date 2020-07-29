package com.hy.traffic.studentInfo.provider;

public class StudentinfoProvider {

    public String queryByid(Integer said, String select , String neirong){
        StringBuffer sql=new StringBuffer("SELECT * FROM studentinfo WHERE 1=1 ");

        if(select=="realName"){
            sql.append(" And realName like '%"+neirong+"%'");
        }else if(select=="cardId"){
            sql.append(" And cardId = "+neirong+"");
        }else if(select=="busName"){
            sql.append(" And busName = "+neirong+"");
        }

        sql.append(" AND id IN(SELECT stuid FROM saftydustudentinfo WHERE said="+said+") ");


        return sql.toString();
    }

}
