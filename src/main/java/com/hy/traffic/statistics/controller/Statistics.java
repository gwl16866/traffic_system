package com.hy.traffic.statistics.controller;

import com.hy.traffic.saftyEdu.entity.Bchar;
import com.hy.traffic.saftyEdu.entity.Bcharinfo;
import com.hy.traffic.saftyEdu.entity.NData;
import com.hy.traffic.saftyEdu.entity.Saftyedu;
import com.hy.traffic.saftyEdu.service.impl.SaftyeduServiceImpl;
import com.hy.traffic.studentaccmq.service.impI.StudentaccmqServiceImpI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/statistics/statisticsinfo")
@CrossOrigin
public class Statistics {
    @Autowired
    SaftyeduServiceImpl saftyeduService;

    @Autowired
    StudentaccmqServiceImpI studentaccmqServiceImpI;

    /*
    * @parm mq
    * 1到12月报名人数
    * */
    @RequestMapping("/year")
    @ResponseBody
    public Integer[] year(){
        return saftyeduService.year();
    }

    @RequestMapping("/thenumber")
    @ResponseBody
    public Integer[] thenumber(){
        return saftyeduService.thenumber();
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
        List<Saftyedu> list=saftyeduService.jhs();

        int count=0;
        int okcount=0;
        for (Saftyedu saftyedu : list) {
            String [] str=saftyedu.getStudent().split(",");
            count+=str.length;
        }
        for (Saftyedu saftyedu : list) {
            okcount+=studentaccmqServiceImpI.thenumber(saftyedu.getStudent(),saftyedu.getId(),2);
        }

        NData nData=new NData();
        nData.setName("枣阳市光武石化运输有限公式");
        nData.setJhs(list.size());
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
        List<Saftyedu> list=saftyeduService.jhs();

        for (Saftyedu saftyedu : list) {
            int a=studentaccmqServiceImpI.thenumber(saftyedu.getStudent(),saftyedu.getId(),2);
            int b=studentaccmqServiceImpI.thenumber(saftyedu.getStudent(),saftyedu.getId(),1);
            if(a>0){
                saftyedu.setJd(a*100/(a+b));
                saftyedu.setA(a);
                saftyedu.setB(a+b);
            }else{
                saftyedu.setJd(0);
                saftyedu.setA(0);
                saftyedu.setB(0);
            }
        }

        return list;
}



}
