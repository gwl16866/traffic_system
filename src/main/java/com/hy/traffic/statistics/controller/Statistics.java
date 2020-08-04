package com.hy.traffic.statistics.controller;

import com.hy.traffic.saftyEdu.entity.*;
import com.hy.traffic.saftyEdu.service.impl.SaftyeduServiceImpl;
import com.hy.traffic.studentInfo.entity.Answer;
import com.hy.traffic.studentInfo.entity.Studentxiangqing;
import com.hy.traffic.studentInfo.service.impl.StudentinfoServiceImpl;
import com.hy.traffic.studentaccmq.service.impI.StudentaccmqServiceImpI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/statistics/statisticsinfo")
@CrossOrigin
public class Statistics {
    @Autowired
    SaftyeduServiceImpl saftyeduService;

    @Autowired
    StudentaccmqServiceImpI studentaccmqServiceImpI;

    @Autowired
    StudentinfoServiceImpl studentinfoService;
    /*
    * @parm mq
    * 1到12月报名人数
    * */
    @RequestMapping("/year")
    @ResponseBody
    public Integer[] year(){
        return saftyeduService.year(MqBean.time(),2,null);
    }

    @RequestMapping("/thenumber")
    @ResponseBody
    public Integer[] thenumber(){
        return saftyeduService.year(MqBean.time(),null,null);
    }

    @ResponseBody
    @RequestMapping("/Bchar1")
    public List<Bchar> Bchar1(){
        return saftyeduService.num(1);
    }

    @ResponseBody
    @RequestMapping("/Bchar2")
    public List<Bchar> Bchar2(){
        return saftyeduService.num(2);
    }

    @ResponseBody
    @RequestMapping("/Bchar3")
    public List<Bchar> Bchar3(){
        return saftyeduService.num(3);
    }

    @ResponseBody
    @RequestMapping("/inittableData")
    public List<Bcharinfo> inittableData(){
        List<Bchar> list=saftyeduService.num(1);
        List<Bchar> list2=saftyeduService.num(2);
        List<Bchar> list3=saftyeduService.num(3);

        List<Bcharinfo> res=new ArrayList<>();
        Bcharinfo bcharinfo=new Bcharinfo();
        Bcharinfo bcharinfo2=new Bcharinfo();
        Bcharinfo bcharinfo3=new Bcharinfo();
        bcharinfo.setLearning("线上");
        if(list.size()>0){
            bcharinfo.setNum(list.get(0).getValue()+list.get(1).getValue());
            if(list.get(0).getValue()!=0){
                bcharinfo.setPercentage(list.get(0).getValue()*100/(list.get(0).getValue()+list.get(1).getValue()));
            }else{
                bcharinfo.setPercentage(0);
            }

        }

        bcharinfo2.setLearning("线下");
        if(list2.size()>0){
            bcharinfo2.setNum(list2.get(0).getValue()+list2.get(1).getValue());
            if(list2.get(0).getValue()!=0){
                bcharinfo2.setPercentage(list2.get(0).getValue()*100/(list2.get(0).getValue()+list2.get(1).getValue()));
            }else{
                bcharinfo2.setPercentage(0);
            }

        }


        bcharinfo3.setLearning("线上+线下");
        if(list3.size()>0){
            bcharinfo3.setNum(list3.get(0).getValue()+list3.get(1).getValue());
            if(list3.get(0).getValue()!=0){
                bcharinfo3.setPercentage(list3.get(0).getValue()*100/(list3.get(0).getValue()+list3.get(1).getValue()));
            }else{
                bcharinfo3.setPercentage(0);
            }
            }

        res.add(bcharinfo);
        res.add(bcharinfo2);
        res.add(bcharinfo3);
        return res;
    }


    @ResponseBody
    @RequestMapping("/inittableData2")
    public List<NData> inittableData2(){
        List<NData> res=new ArrayList<>();
        List<Saftyedu> jhs=saftyeduService.jh(MqBean.time(),null);
        int count=saftyeduService.jhs(null,null,null);
        int okcount=saftyeduService.jhs(2,null,null);


        NData nData=new NData();
        nData.setName("枣阳市光武石化运输有限公式");
        nData.setJhs(jhs.size());
        nData.setCount(count);
        nData.setOkcount(okcount);
        if(okcount!=0){
            nData.setWcl(okcount*100/count);
        }else{
            nData.setWcl(0);
        }


        res.add(nData);
       return res;
    }

    @ResponseBody
    @RequestMapping("/xiangqing")
    public List<Saftyedu> xaingqing(){
        List<Saftyedu> list=saftyeduService.jh(MqBean.time(),null);

        for (Saftyedu saftyedu : list) {
            int a=studentaccmqServiceImpI.thenumber(MqBean.time(),null,2,null,saftyedu.getId());
            int b=studentaccmqServiceImpI.thenumber(MqBean.time(),null,1,null,saftyedu.getId());
            if(a>0){
                saftyedu.setJd(a*100/(a+b));
                saftyedu.setA(a);
                saftyedu.setB(a+b);
            }else{
                saftyedu.setJd(0);
                saftyedu.setA(a);
                saftyedu.setB(a+b);
            }
        }

       return list;
}

  @ResponseBody
  @RequestMapping("/xiangqing2")
  public List<Studentxiangqing> xiangqing(Integer id,String aaa,String realName,Integer radio){
        System.out.println("牛"+radio);
        System.out.println(id);
        System.out.println("按什么查询"+aaa);
        System.out.println("查询条件"+realName);

        List<Studentxiangqing>  list=studentinfoService.studentxiangqing2(id,aaa,realName);
        if(radio==1){
            Integer [] in=studentinfoService.stuid(id);
            Integer [] in2= new Integer[list.size()];


            if(in.length>0){
                for (int i = 0; i < list.size(); i++) {
                    in2[i]=list.get(i).getId();
                }
            }

            List<Integer>  list1 = new ArrayList();
            List<Integer>  list2 = new ArrayList();

            //调用Arrays.asList将数组转换成列表
            List<Integer> aList = Arrays.asList(in);
            List<Integer> bList = Arrays.asList(in2);
//接下去是重点。将上面两个List转换成ArrayList
            List<Integer> acList = new ArrayList<Integer>(aList);
            List<Integer> bcList = new ArrayList<Integer>(bList);
//遍历去除重复
            for(Integer i : bcList){
                if(acList.contains(i)){
                    acList.remove(i);
                }
            }

            for (Integer integer : acList) {
                System.out.println("怎么回事："+integer);
            }

            System.out.println("成功与否"+acList.size());

            for (Integer integer : acList) {
                Studentxiangqing studentxiangqing=studentinfoService.stuentmq(integer,aaa,realName);
                if(studentxiangqing!=null){
                    list.add(studentxiangqing);
                }
            }

        }else if(radio==2){

            for (int i = 0; i < list.size(); i++) {
                if(list.get(i).getAstatus()!=2){
                    list.remove(i);
                }
            }
        }else if(radio==3){

            Integer [] in=studentinfoService.stuid(id);
            Integer [] in2= new Integer[list.size()];


            if(in.length>0){
                for (int i = 0; i < list.size(); i++) {
                    in2[i]=list.get(i).getId();
                }
            }

            List<Integer>  list1 = new ArrayList();
            List<Integer>  list2 = new ArrayList();

            //调用Arrays.asList将数组转换成列表
            List<Integer> aList = Arrays.asList(in);
            List<Integer> bList = Arrays.asList(in2);
//接下去是重点。将上面两个List转换成ArrayList
            List<Integer> acList = new ArrayList<Integer>(aList);
            List<Integer> bcList = new ArrayList<Integer>(bList);
//遍历去除重复
            for(Integer i : bcList){
                if(acList.contains(i)){
                    acList.remove(i);
                }
            }

            for (Integer integer : acList) {
                System.out.println("怎么回事："+integer);
            }

            System.out.println("成功与否"+acList.size());

            list.clear();
            for (Integer integer : acList) {
                Studentxiangqing studentxiangqing=studentinfoService.stuentmq(integer,aaa,realName);
                if(studentxiangqing!=null){
                    list.add(studentxiangqing);
                }
            }



      }else if(radio==4){

            for (int i = 0; i < list.size(); i++) {
                if(list.get(i).getAstatus()!=1){
                    list.remove(i);
                }
            }
        }

        return list;
  }


  @ResponseBody
  @RequestMapping("/corexiangqing")
  public List<Answer> queryallAnswer(Integer id){
        return studentinfoService.queryallAnswer(id);
  }

}
