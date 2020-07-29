package com.hy.traffic.document.controller;


import com.hy.traffic.document.entity.Document;
import com.hy.traffic.document.mapper.DocumentMapper;
import com.hy.traffic.document.service.IDocumentService;
import com.hy.traffic.saftyEdu.entity.PageJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author gwl
 * @since 2020-07-25
 */
@CrossOrigin
@RestController
@RequestMapping("/document/document")
public class DocumentController {

    @Autowired
    private IDocumentService iDocumentService;

    @Autowired
    private DocumentMapper documentMapper;

    @RequestMapping("/queryDocument")
    public PageJson queryDocument(Integer currpage, Integer pagesize,PageJson pageJson){

        return iDocumentService.querydocument(currpage,pagesize,pageJson);
    }

    @RequestMapping("/insertdocument")
    public PageJson insertdocument(Document document,PageJson pageJson){
        if(document.getId()==null){
            document.setStatus(1);
        }
        document.setUpdateTime(new Date());
         pageJson.setCode(200);
        try {
            iDocumentService.saveOrUpdate(document);
        } catch (Exception e) {
            e.printStackTrace();
             pageJson.setCode(500);

        }
        return pageJson;
    }

    @RequestMapping("/queryByid")
    public Document queryByid(Integer id){

        return iDocumentService.queryById(id);
    }

}
