package com.hy.traffic.studentaccmq.service.impI;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hy.traffic.studentaccmq.entity.Studentacc;
import com.hy.traffic.studentaccmq.mapper.StudentaccmqMapper;
import com.hy.traffic.studentaccmq.service.IStudentaccmqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentaccmqServiceImpI extends ServiceImpl<StudentaccmqMapper, Studentacc> implements IStudentaccmqService {

    @Autowired
    StudentaccmqMapper studentaccmqMapper;
    public List<Studentacc> queryall(){
        return studentaccmqMapper.queryall();
    }

    public int thenumber(String studentid,Integer saftyid,Integer completion){
        return studentaccmqMapper.thenumber(studentid,saftyid,completion);
    }


}
