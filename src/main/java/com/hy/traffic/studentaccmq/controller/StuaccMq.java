package com.hy.traffic.studentaccmq.controller;

import com.hy.traffic.studentaccmq.entity.Studentacc;
import com.hy.traffic.studentaccmq.service.impI.StudentaccmqServiceImpI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/abc/mq")
public class StuaccMq {
    @Autowired
    StudentaccmqServiceImpI studentaccmqServiceImpI;

    @ResponseBody
    @RequestMapping("/aa")
    public List<Studentacc> queryall(){
        return studentaccmqServiceImpI.queryall();
    }

}
