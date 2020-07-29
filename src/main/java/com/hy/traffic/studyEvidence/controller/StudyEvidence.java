package com.hy.traffic.studyEvidence.controller;

import com.hy.traffic.saftyEdu.entity.PageJson;
import com.hy.traffic.studyEvidence.service.IStudyEvidenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author gwl
 * @since 2020-07-25
 */
@RestController
@CrossOrigin
@RequestMapping("/studyEvidence/saftyedu")
public class StudyEvidence {

    @Autowired
    private IStudyEvidenceService iStudyEvidenceService;

    @RequestMapping("/queryStudentinfo")
        public PageJson queryStudentinfo(Integer currpage, Integer pagesize,PageJson pageJson) {

        return iStudyEvidenceService.queryStudentinfo(currpage,pagesize,pageJson);
    }


}
