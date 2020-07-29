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
        StringBuffer stringBuffer = new StringBuffer("SELECT id,headImg,companyName,realname,cardId,linkNum,busNum,jobName,createTime,STATUS,headImgStatus FROM studentinfo WHERE 1=1 ");
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


}
