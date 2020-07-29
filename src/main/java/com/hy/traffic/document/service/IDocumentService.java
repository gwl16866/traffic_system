package com.hy.traffic.document.service;

import com.hy.traffic.document.entity.Document;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hy.traffic.saftyEdu.entity.PageJson;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author gwl
 * @since 2020-07-25
 */
public interface IDocumentService extends IService<Document> {

    PageJson querydocument(Integer currpage, Integer pagesize, PageJson pageJson);

    Document queryById(Integer id);

}
