package com.hy.traffic.trainProgress.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.util.StringUtils;

public class LikeSelect {
//     根据年份及月份查询条件内的数据
    public String selectByTime(String YearMonth){
        StringBuilder sql=new StringBuilder("SELECT * FROM saftyedu where startTime like '"+YearMonth+"%'");
        return sql.toString();
    };

    public String selectok(@Param("student") String student,@Param("completion") Integer completion,@Param("saftyid") Integer saftyid){
        StringBuilder sql=new StringBuilder(" SELECT count(1) FROM studentacc WHERE 1=1 ");
        if(student!=null&& student!=""){
            sql.append( " and studentid in ( "+ student +" ) " );
        }

        if(saftyid!=null){
            sql.append(" and saftyid = "+ saftyid+"");
        }
        if(completion!=null){
            sql.append( " and completion="+completion+" " );
        }
        return sql.toString();
    };



    //    查询某一年月里产与培训的总人数
    public String allNum(String YearMonth){
        StringBuilder sql=new StringBuilder("SELECT COUNT(studentid) FROM saftyedu s,studentAcc sa WHERE s.id=sa.saftyid AND s.startTime LIKE '"+YearMonth+"%'");
        return sql.toString();
    };

    //    查询某一年月里完成培训的人数
    public String successNum(String YearMonth){
        StringBuilder sql=new StringBuilder("SELECT COUNT(studentid) FROM saftyedu s,studentAcc sa WHERE s.id=sa.saftyid AND sa.completion=2 AND s.startTime LIKE '"+YearMonth+"%'");
        return sql.toString();
    };



    //     查询某一年月中某一培训主题的总人数
    public String themeAllNum(String YearMonth,String theme){
        StringBuilder sql=new StringBuilder("SELECT COUNT(studentid) FROM saftyedu s,studentAcc sa WHERE s.id=sa.saftyid AND s.startTime LIKE '"+YearMonth+"%' AND sa.saftyid=(SELECT id FROM saftyedu WHERE theme="+theme+") ");
        return sql.toString();
    };
//     查询某一年月中某一培训主题的完成培训的人数
    public String successThemeNum(String YearMonth,String theme){
        StringBuilder sql=new StringBuilder("SELECT COUNT(studentid) FROM saftyedu s,studentAcc sa WHERE s.id=sa.saftyid AND sa.completion=2 AND s.startTime LIKE '"+YearMonth+"%' AND sa.saftyid=(SELECT id FROM saftyedu WHERE theme="+theme+") ");
        return sql.toString();
    };


//    参训学员
//    查询查询学员的信息
    public String SelectCXInfo(Integer saftyid){
        StringBuffer sql=new StringBuffer("SELECT s.id,s.headImg,s.realName,s.cardId,s.jobName,s.busNum,s.linkNum,c.completion,s.createTime FROM studentinfo s, studentacc c WHERE s.id=c.studentid AND c.saftyid="+saftyid+" and 1=1");
        return sql.toString();
    }

    //    模糊查询学员的信息
    public String LikeCXInfo(String selects,Integer saftyid,String inputs,Integer oneStatus){
        StringBuffer sql=new StringBuffer("SELECT s.id,s.headImg,s.realName,s.cardId,s.jobName,s.busNum,s.linkNum,c.completion,s.createTime FROM studentinfo s, studentacc c WHERE s.id=c.studentid AND c.saftyid="+saftyid+" and 1=1");
        if(selects.equals("realName") && !StringUtils.isEmpty(inputs)){
            sql.append(" and s.realName like '%"+inputs+"%'");
        }else if(selects.equals("linkNum") && !StringUtils.isEmpty(inputs)){
            sql.append(" and s.linkNum like '%"+inputs+"%'");
        }else if(selects.equals("cardId") && !StringUtils.isEmpty(inputs)){
            sql.append(" and s.cardId like '%"+inputs+"%'");
        }else if(selects.equals("busNum") && !StringUtils.isEmpty(inputs)){
            sql.append(" and s.busNum like '%"+inputs+"%'");
        }
        if(oneStatus==1){
            sql.append(" and c.completion='1'");
        }else if(oneStatus==2){
            sql.append(" and c.completion='2'");
        }
        return sql.toString();
    }

//导出Excel查询
    public String selectExcel(String startTime){
        String sql="SELECT sa.ltype learnType,sa.theme,sa.times,sa.project,sa.learnTime,sa.allproper,st.ok ,ROUND((st.ok/sa.allproper)*100,2)AS baifenbi FROM\n" +
                "(SELECT st.*,sa.`learnType` ltype ,sa.`theme` theme,CONCAT(sa.`startTime`,\"~\",sa.`endTime`)times,sa.`project` project,sa.`learnTime` learnTime, COUNT(st.saftyid) allproper\n" +
                "FROM saftyedu sa ,studentacc st WHERE sa.`id`=st.`saftyid` AND sa.startTime LIKE '"+startTime+"%' GROUP BY st.saftyid)sa,\n" +
                "(SELECT id,COUNT(saftyid)AS ok FROM `studentacc` WHERE `saftyid`IN(SELECT saftyid FROM saftyedu sa ,studentacc st WHERE sa.`id`=st.`saftyid` AND sa.startTime LIKE '"+startTime+"%' GROUP BY st.saftyid) AND `completion`= 1 GROUP BY `saftyid`)st \n" +
                "WHERE sa.id=st.id";
        return sql;
    }

}
