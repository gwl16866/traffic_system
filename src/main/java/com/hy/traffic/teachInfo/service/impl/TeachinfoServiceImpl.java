package com.hy.traffic.teachInfo.service.impl;

import com.hy.traffic.teachInfo.entity.*;
import com.hy.traffic.teachInfo.mapper.TeachinfoMapper;
import com.hy.traffic.teachInfo.service.ITeachinfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author gwl
 * @since 2020-07-25
 */
@Service
public class TeachinfoServiceImpl extends ServiceImpl<TeachinfoMapper, Teachinfo> implements ITeachinfoService {
    @Autowired
    private TeachinfoMapper mapper;

    @Override
    public List<Teachinfo> queryAllTeachinfo() {
        return mapper.queryAllTeachinfo();
    }

    @Override
    public List<ClassDetail> queryFirstClassDetail(Integer cid) {
        return mapper.queryFirstClassDetail(cid);
    }
    public List<ClassDetail> secondClassDetail(Integer pid){
        return mapper.secondClassDetail(pid);
    }

    @Override
    public List<ClassDetail> recursionHands(List<ClassDetail> list) {
            if(null != list && !list.isEmpty()){
                for(ClassDetail cla:list){
                    List<ClassDetail> secondList=secondClassDetail(cla.getId());
                    cla.setList(secondList);
                    recursionHands(secondList);
                }
            }
            return list;
    }

    @Override
    public List<ClassDetail> queryZhangByClass(Integer id) {
        return mapper.queryZhangByClass(id);
    }

    @Override
    public List<ClassDetail> queryJieByClass(Integer id) {
        return mapper.queryJieByClass(id);
    }



    @Override
    public List<QuestionsDetail> queryAllQuestion() {
        return mapper.queryAllQuestion();
    }

    @Override
    public Question queryOneAnswer(Integer id) {
        return mapper.queryOneAnswer(id);
    }

    @Override
    public boolean addQuesObject(AddQuesObject add) {
        StringBuilder sb =new StringBuilder();
        sb.append("A."+add.getA());
        sb.append(",");
        sb.append("B."+add.getB());
        sb.append(",");
        sb.append("C."+add.getC());
        sb.append(",");
        sb.append("D."+add.getD());
        //插入问题
        Integer q = mapper.addQuestionsManager(add,sb.toString());
        add.setId(mapper.maxQid());
        if(add.getTypes().equals("单选题")){
            String dan = add.getDanAnswer();
            mapper.addQuestionsAnswer(add,dan);
        }else{
            StringBuilder duo =new StringBuilder();
            for (int i = 0; i < add.getDuoAnswer().size(); i++) {
                duo.append(add.getDuoAnswer().get(i));
                if(i<add.getDuoAnswer().size()-1){
                    duo.append(",");
                }
            }
            mapper.addQuestionsAnswer(add,duo.toString());
        }
        return true;
    }

    @Override
    public boolean addLession(AddLession add) {
        if(!StringUtils.isEmpty(add.getD1())){
            //已选择大纲
            //添加章
            mapper.addLession(add.getZ(),null,null,add.getD1().toString());
            Integer z = mapper.maxCid();
            //添加节
            mapper.addLession(add.getJ(),z.toString(),null,add.getD1().toString());
            Integer j = mapper.maxCid();
            //添加小节
            mapper.addLession(add.getXj(),j.toString(),add.getVe(),add.getD1().toString());
        }else{
            //全文字
            //添加纲
            mapper.addGang(add.getD2());
            Integer t =mapper.maxTid();
            //添加章
            mapper.addLession(add.getZ(),null,null,t.toString());
            //添加节
            Integer z = mapper.maxCid();
            mapper.addLession(add.getJ(),z.toString(),null,t.toString());
            Integer j = mapper.maxCid();
            //添加小节
            mapper.addLession(add.getXj(),j.toString(),add.getVe(),t.toString());
        }
        return true;
    }

    @Override
    public boolean addLessionsJie(AddLessions add) {
        //mapper.addLession(add.getZhangjie(),null,null,add.getDagang());
        //Integer z = mapper.maxCid();
        //添加节
        mapper.addLession(add.getDajie(),add.getZhangjie(),null,add.getDagang());
        Integer j = mapper.maxCid();
        //添加小节
        mapper.addLession(add.getXiaojie(),j.toString(),add.getShipin(),add.getDagang());
        return true;
    }


}
