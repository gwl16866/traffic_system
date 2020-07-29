package com.hy.traffic.studyEvidence.service;

import com.hy.traffic.saftyEdu.entity.PageJson;

public interface IStudyEvidenceService {

    PageJson queryStudentinfo(Integer currpage, Integer pagesize, PageJson pageJson);

}
