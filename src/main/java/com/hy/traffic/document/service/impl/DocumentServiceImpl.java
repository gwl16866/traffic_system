package com.hy.traffic.document.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hy.traffic.document.entity.Document;
import com.hy.traffic.document.mapper.DocumentMapper;
import com.hy.traffic.document.service.IDocumentService;
import com.hy.traffic.saftyEdu.entity.PageJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author gwl
 * @since 2020-07-25
 */
@Service
public class DocumentServiceImpl extends ServiceImpl<DocumentMapper, Document> implements IDocumentService {

    @Autowired
    private DocumentMapper documentMapper;

    @Override
    public PageJson querydocument(Integer currpage, Integer pagesize,PageJson pageJson) {
        Page page = PageHelper.startPage(currpage, pagesize, true);
        pageJson.setData(documentMapper.queryDocument());
        pageJson.setCount(page.getTotal());


        return pageJson;
    }


    @Override
    public Document queryById(Integer id){

        return documentMapper.queryById(id);
    }
}
