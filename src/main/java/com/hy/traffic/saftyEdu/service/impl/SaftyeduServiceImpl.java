package com.hy.traffic.saftyEdu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hy.traffic.managerMan.entity.Manager;
import com.hy.traffic.managerMan.service.impl.ManagerServiceImpl;
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
import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    private ManagerServiceImpl managerService;

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

    @Override
    public void addLook(Integer classid,Integer studentid,Integer saftyeduid){
        saftyeduMapper.addLook(classid,studentid,saftyeduid);
    };

    @Override
    public void addLession(Integer id,Integer classid){
        saftyeduMapper.addLession(id,classid);
    }

    @Override
    public List<Studentinfo> selectAllStu() {

       List<Studentinfo> list = saftyeduMapper.selectAllStu();
        List<Studentinfo> list1=list.stream().filter(e->e.getType().equals("0")).collect(Collectors.toList());
        list1.addAll(list.stream().filter(e->e.getType().equals("1")).collect(Collectors.toList()));
//        List<Manager> mans = managerService.selectList();
//        for (int i = 0; i < mans.size(); i++) {
//            Studentinfo s=new Studentinfo();
//            s.setId(mans.get(i).getId());
//            s.setHeadImg(null);
//            s.setRealName(mans.get(i).getName());
//            s.setCardId(mans.get(i).getCardId());
//            s.setJobName(mans.get(i).getJob());
//            list.add(s);
//        }
        return list1;
    }

    @Override
    public List<Tree> queryclassDetail(){
        List<Tree> list = saftyeduMapper.queryclassDetail();
        for (int i = 0; i < list.size(); i++) {
           Integer a = saftyeduMapper.querQuestionsCount(list.get(i).getId());
           list.get(i).setCount(a);
        }
        return list;
    };

    @Override
    public void addSaftyEdu(String theme, String startTime, String endTime, String manager, String testPeople, Integer learnType, String learnTime,Integer passscore){
        saftyeduMapper.addSaftyEdu(theme, startTime, endTime,  manager,testPeople, learnType, learnTime,passscore);
    };

    @Override
    public void addSaftyEducopy(String theme, String startTime, String endTime, String manager, String testPeople, Integer learnType, String learnTime,Integer passscore,String address,String image){
        saftyeduMapper.addSaftyEducopy(theme, startTime, endTime,  manager,testPeople, learnType, learnTime,passscore,address,image);
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



    public Integer[] year(String time,Integer completion,Integer learnType){

        Integer [] in=new Integer [12];
        for (int i = 0; i < in.length; i++) {
           Integer count=saftyeduMapper.year(time,i+1,completion,learnType);
           in[i]=count;
        }
        return in;
    }



    public  List<Bchar> num(Integer learnType){
        System.out.println("测=="+learnType);

        Integer count=saftyeduMapper.year(MqBean.time(),null,2,learnType);
        Integer count2=saftyeduMapper.year(MqBean.time(),null,1,learnType);

        Bchar bchar=new Bchar();
        bchar.setValue(count);
        bchar.setName("完成人数");
        Bchar bchar1=new Bchar();
        bchar1.setValue(count2);
        bchar1.setName("未完成人数");
        List<Bchar> mq=new ArrayList<>();
        mq.add(bchar);
        mq.add(bchar1);
        return mq;
    }

    public Integer jhs(Integer completion,Integer i,Integer learnType){
        Integer in=saftyeduMapper.year(MqBean.time(),i,completion,learnType);
        return in;
    }

    public List<Saftyedu> jh(String time,Integer learnType){
        return saftyeduMapper.num(time,learnType);
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
