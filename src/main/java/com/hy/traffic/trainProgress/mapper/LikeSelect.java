package com.hy.traffic.trainProgress.mapper;

public class LikeSelect {


    public String queryThemTable(String YearMonth){
        StringBuilder sql=new StringBuilder("SELECT zhu.id,zhu.theme,zhu.project,zhu.allProper, IFNULL(ok.okProper,0)okProper ,zhu.startTime,zhu.endTime,zhu.learnType FROM \n" +
                "(SELECT  a.`id`,a.`theme`,a.`project`, COUNT(1) allProper ,a.`startTime`,a.`endTime`,a.learnType FROM \n" +
                "`saftyedu` a LEFT JOIN `saftydustudentinfo` b ON a.`id`=b.`saftyId` WHERE a.`startTime` LIKE '"+YearMonth+"%' GROUP BY a.id\n" +
                ")zhu LEFT JOIN \n" +
                "(SELECT c.id,COUNT(1) okProper \n" +
                "FROM `saftyedu` c LEFT JOIN `saftydustudentinfo` d ON c.`id`=d.`saftyId` \n" +
                "GROUP BY d.saftyId,d.completion HAVING d.completion=2\n" +
                ")ok \n" +
                "ON zhu.id=ok.id");
        return sql.toString();
    }
    public String queryAllProperAndOkProper(String YearMonth){
        StringBuilder sql=new StringBuilder("SELECT alls.allProper,ok.okProper FROM \n" +
                "(SELECT a.id, IFNULL(COUNT(1),0) allProper FROM `saftyedu` a LEFT JOIN `saftydustudentinfo` b ON a.`id`=b.`saftyId` WHERE a.`startTime` LIKE '"+YearMonth+"%')alls,\n" +
                "(SELECT a.id, IFNULL(COUNT(1),0) okProper FROM `saftyedu` a LEFT JOIN `saftydustudentinfo` b ON a.`id`=b.`saftyId` WHERE b.`completion`=2 AND a.startTime LIKE '"+YearMonth+"%')ok WHERE alls.id=ok.id OR alls.id IS NULL");
        return sql.toString();
    }
    public String queryAllPeiXunClass(Integer Id){
        StringBuilder sql=new StringBuilder("SELECT pro.project,c.oneTitle,c.vedio,c.vedioTime FROM classdetails c,saftyclass s,saftyedu pro WHERE c.id=s.classId AND pro.`id`=s.`saftyId` AND s.`saftyId`="+Id+"");
        return sql.toString();
    }


}
