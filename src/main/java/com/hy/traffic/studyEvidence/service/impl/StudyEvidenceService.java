package com.hy.traffic.studyEvidence.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hy.traffic.saftyEdu.entity.PageJson;
import com.hy.traffic.studyEvidence.mapper.IStudyEvidenceMapper;
import com.hy.traffic.studyEvidence.service.IStudyEvidenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudyEvidenceService implements IStudyEvidenceService {

    @Autowired
    private IStudyEvidenceMapper iStudyEvidenceMapper;

    @Override
    public PageJson queryStudentinfo(Integer currpage, Integer pagesize, PageJson pageJson) {
        Page page = PageHelper.startPage(currpage, pagesize, true);
        pageJson.setData(iStudyEvidenceMapper.queryStudentinfo());
        pageJson.setCount(page.getTotal());

        return pageJson;
    }
}
