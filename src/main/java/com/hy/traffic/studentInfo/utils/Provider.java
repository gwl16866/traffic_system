package com.hy.traffic.studentInfo.utils;

import com.hy.traffic.studentInfo.entity.Studentinfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.util.StringUtils;

/**
 * @ClassName Provider
 * @Description TODO
 * @Author zhangduo
 * @Date 2020/7/25 15:25
 * @Version 1.0
 */
public class Provider {
    public String queryAllStudentInFo(@Param("studentinfo") Studentinfo studentinfo){
        StringBuffer stringBuffer = new StringBuffer("SELECT id,headImg,companyName,realname,cardId,linkNum,busNum,jobName,createTime,STATUS,headImgStatus FROM studentinfo WHERE 1=1 and type='0'");
        if (!StringUtils.isEmpty(studentinfo)){
            if(!StringUtils.isEmpty(studentinfo.getRealName())){
                stringBuffer.append(" and realname like '%"+studentinfo.getRealName()+"%'");
            }
            if(!StringUtils.isEmpty(studentinfo.getStatus()) && studentinfo.getStatus()!=-1){
                stringBuffer.append(" and status="+studentinfo.getStatus()+"");
            }
            if(!StringUtils.isEmpty(studentinfo.getCreateTime()) && !StringUtils.isEmpty(studentinfo.getEndTime())){
                stringBuffer.append(" and createTime BETWEEN '"+studentinfo.getCreateTime()+"' and '"+studentinfo.getEndTime()+"'");
            }
            if(!StringUtils.isEmpty(studentinfo.getHeadImgStatus()) && studentinfo.getHeadImgStatus()!=-1 ){
                stringBuffer.append(" and headImgStatus="+studentinfo.getHeadImgStatus()+"");
            }
        }
        return stringBuffer.toString();
    }
    public String updateOneStudent(Integer id,Integer status){
        StringBuffer stringBuffer = new StringBuffer();
        if (!StringUtils.isEmpty(id)){
            stringBuffer.append("update studentinfo set status="+status+" where id="+id+" and 1=1");
        }
        return stringBuffer.toString();
    }


    public String studentxiangqing(Integer id){
        StringBuffer str=new StringBuffer(" select s.*,a.id as aid , a.status as astatus,a.score from studentinfo s left join  answerrecord  a on s.id=a.saftyid where a.saftyid="+id+" ");

        return  str.toString();
    }

    public String studentxiangqing2(@Param("id")Integer id,@Param("aaa") String aaa,@Param("realName") String realName){
        StringBuffer sss=new StringBuffer(" SELECT s.*,a.id as aid , a.status as astatus,a.startTime,a.score FROM  studentinfo s LEFT JOIN answerrecord a ON a.stuid=s.id WHERE s.id IN(SELECT stuid FROM saftydustudentinfo WHERE saftyId="+id+") AND a.saftyId="+id+" ");

       if(aaa!=null&&!"".equals(aaa) && aaa.trim().length()>0){
           if(aaa.equals("按姓名")){
            sss.append(" and s.realName like '%"+realName+"%' ");
           }
           if(aaa.equals("按身份证")){
               sss.append(" and s.cardId="+realName+"");
           }

           if(aaa.equals("按联系电话")){
               sss.append(" and s.linkNum= "+realName+"");
           }
           if(aaa.equals("按车牌号")){
               sss.append(" and s.busNum ="+realName+"");
           }

       }

        return sss.toString();
    }

    public String stuentmq(@Param("id")Integer id,@Param("aaa") String aaa,@Param("realName") String realName){
        StringBuffer sss=new StringBuffer(" SELECT * FROM studentinfo s WHERE s.id="+id+" ");


        if(aaa!=null&&!"".equals(aaa) && aaa.trim().length()>0){
            if(aaa.equals("按姓名")){
                sss.append(" and s.realName like  '%"+realName+"%' ");
            }
            if(aaa.equals("按身份证")){
                sss.append(" and s.cardId ="+realName+"");
            }

            if(aaa.equals("按联系电话")){
                sss.append(" and s.linkNum= "+realName+"");
            }
            if(aaa.equals("按车牌号")){
                sss.append(" and s.busNum ="+realName+"");
            }

        }
        return sss.toString();
    }













}
