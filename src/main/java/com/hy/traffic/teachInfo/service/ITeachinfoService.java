package com.hy.traffic.teachInfo.service;

import com.hy.traffic.teachInfo.entity.*;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author gwl
 * @since 2020-07-25
 */
public interface ITeachinfoService extends IService<Teachinfo> {


  public List<Teachinfo> queryAllTeachinfo();
  public List<ClassDetail> queryFirstClassDetail(Integer cid);
  public List<ClassDetail> recursionHands(List<ClassDetail> list);
  public List<ClassDetail> queryZhangByClass(Integer id);
  public List<ClassDetail> queryJieByClass(Integer id);
  public List<QuestionsDetail> queryAllQuestion();
  public Question queryOneAnswer(Integer id);
  public boolean addQuesObject(AddQuesObject add);
  public boolean addLession(AddLession add);
  public boolean addLessionsJie(AddLessions add);



}
