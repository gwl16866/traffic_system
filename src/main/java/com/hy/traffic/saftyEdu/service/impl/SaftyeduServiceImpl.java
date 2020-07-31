package com.hy.traffic.saftyEdu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hy.traffic.saftyEdu.entity.*;
import com.hy.traffic.saftyEdu.mapper.SaftyeduMapper;
import com.hy.traffic.saftyEdu.service.ISaftyeduService;
import com.hy.traffic.studentInfo.entity.Studentinfo;
import com.hy.traffic.studentInfo.mapper.StudentinfoMapper;
import com.hy.traffic.studentaccmq.mapper.StudentaccmqMapper;
import com.hy.traffic.teachInfo.entity.ClassDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author gwl
 * @since 2020-07-25
 */
@Service
public class SaftyeduServiceImpl extends ServiceImpl<SaftyeduMapper, Saftyedu> implements ISaftyeduService {
    @Autowired
    private  SaftyeduMapper saftyeduMapper;

    @Autowired
    private StudentinfoMapper studentinfoMapper;

    @Autowired
    StudentaccmqMapper studentaccmqMapper;


    @Override
    public List<Saftyedu> selectSaftyEdu(Integer learnType) {
        return saftyeduMapper.selectSaftyEdu(learnType);
    }

    @Override
    public Integer selectStudentCount(Integer id){
        return saftyeduMapper.selectStudentCount(id);
    }


    @Override
    public void updateStatus(Integer id, Integer status) {
        saftyeduMapper.updateStatus(id, status);
    }
    @Override
    public List<Studentinfo> selectStudent(Integer id) {
        return saftyeduMapper.selectStudent(id);
    }
    @Override
    public void deleteStudent(Integer studentid,Integer saftyid) {

             saftyeduMapper.deleteStudent(studentid,saftyid);
    }

    @Override
    public List<Studentinfo> selectAllStudent(Integer saftyid){
      return   saftyeduMapper.selectAllStudent(saftyid);
    }

    @Override
    public void batchAddStudent(Integer saftyid,Integer studentid){
        saftyeduMapper.batchAddStudent(saftyid,studentid);
    }

    public void addLook(Integer classid,Integer studentid,Integer saftyeduid){
        saftyeduMapper.addLook(classid,studentid,saftyeduid);
    };

    public void addLession(Integer id,Integer classid){
        saftyeduMapper.addLession(id,classid);
    }

    @Override
    public List<Studentinfo> selectAllStu() {
       return saftyeduMapper.selectAllStu();
    }

    @Override
    public List<Tree> queryclassDetail(){
        return saftyeduMapper.queryclassDetail();
    };

    @Override
    public void addSaftyEdu(String theme, String startTime, String endTime, String manager, String testPeople, Integer learnType, String learnTime,Integer passscore){
         saftyeduMapper.addSaftyEdu(theme, startTime, endTime,  manager,testPeople, learnType, learnTime,passscore);
    };

    @Override
    public Integer selectMaxId(){
      return   saftyeduMapper.selectMaxId();
    }

    @Override
    public void deleteSaftyedu(Integer id){
         saftyeduMapper.deleteSaftyedu(id);
    }


    @Override
    public List<saftyclass> selectlession(Integer id){
      return   saftyeduMapper.selectlession(id);
    }
    @Override
    public List<ClassDetail> classDetailList(String id){
        return saftyeduMapper.classDetailList(id);
}



    public Integer[] year(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        Date date=new Date();
        String time=sdf.format(date);

        Integer [] in=new Integer [12];
        for (int i = 0; i < in.length; i++) {
            List<Saftyedu> list=saftyeduMapper.year(time,i+1);
            int cc=0;
            for (Saftyedu saftyedu : list) {
                String str=saftyedu.getStudent();
                String [] le=str.split(",");
                cc+=le.length;
            }
            in[i]=cc;
        }
        return in;
    }

    public Integer[] thenumber(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        Date date=new Date();
        String time=sdf.format(date);

        Integer [] in=new Integer [12];
        for (int i = 0; i < in.length; i++) {
            List<Saftyedu> list=saftyeduMapper.year(time,i+1);
            int cc=0;
            for (Saftyedu saftyedu : list) {
                String str=saftyedu.getStudent(); //培训人
                Integer sid=saftyedu.getId();
                cc+=studentaccmqMapper.thenumber(str,sid,2);

            }
            in[i]=cc;
        }

        return in;
    }

    public  List<Bchar> num(Integer learnType){
        System.out.println("测=="+learnType);
        Integer[] in=new Integer[2];
        int nb=0;
        int nb2=0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        Date date=new Date();
        String cc=sdf.format(date);
        List<Saftyedu> list=saftyeduMapper.num(cc,learnType);
        if(list.size()>0){
            System.out.println("集合的长度："+list.size());
            for (Saftyedu saftyedu : list) {
                System.out.println("大哥啊-----------------"+saftyedu.getId());
                nb+=studentaccmqMapper.thenumber(saftyedu.getStudent(),saftyedu.getId(),2);
                nb2+=studentaccmqMapper.thenumber(saftyedu.getStudent(),saftyedu.getId(),1);
            }
        }

        in[0]=nb;
        in[1]=nb2;

        Bchar bchar=new Bchar();
        bchar.setValue(in[0]);
        bchar.setName("完成人数");
        Bchar bchar1=new Bchar();
        bchar1.setValue(in[1]);
        bchar1.setName("未完成人数");
        List<Bchar> mq=new ArrayList<>();
        mq.add(bchar);
        mq.add(bchar1);
        return mq;
    }

    public List<Saftyedu> jhs(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        Date date=new Date();
        String cc=sdf.format(date);
        List<Saftyedu> list=saftyeduMapper.num(cc,null);

        return list;
    }





    //默认查询所有并且分页
    @Override
    public PageJson querySaftyedu(Integer currpage, Integer pagesize, PageJson pageJson) {
        Page page = PageHelper.startPage(currpage, pagesize, true);
        pageJson.setData(saftyeduMapper.querySaftyedu());
        pageJson.setCount(page.getTotal());

        return pageJson;
    }

    //根据日期进行条件查询并分页
    @Override
    public PageJson querySaftyeduMonth(String yuefen, Integer currpage, Integer pagesize, PageJson pageJson) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Page page = PageHelper.startPage(currpage, pagesize, true);
        pageJson.setData(saftyeduMapper.querySaftyeduMonth(dateFormat.parse(yuefen)));
        pageJson.setCount(page.getTotal());

        return pageJson;
    }


    //根据id查询并分页
    @Override
    public PageJson querySaftyduStudentinfoByid(Integer said,Integer currpage, Integer pagesize,PageJson pageJson) {
        Page page = PageHelper.startPage(currpage, pagesize, true);
        pageJson.setData(studentinfoMapper.queryStudentinfoByid(said));
        pageJson.setCount(page.getTotal());
        return pageJson;
    }
}
