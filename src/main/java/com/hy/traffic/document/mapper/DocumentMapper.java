package com.hy.traffic.document.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hy.traffic.document.entity.Document;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author gwl
 * @since 2020-07-25
 */
@Mapper
public interface DocumentMapper extends BaseMapper<Document> {

    @Select("select * from document")
    List<Document> queryDocument();

    @Select("select * from document where id=#{value}")
    Document queryById(Integer id);

    @Insert("insert into document(id,num,title,texts,createTime,updateTime,status) values(null,#{num},#{title},#{texts},#{createTime},#{updateTime},#{status})")
    void insertdocument(Document document);

    @Update("update document set num=#{num} ,title=#{title},texts=#{texts},updateTime=#{updateTime} where id=#{id}")
    void upocumentdate(Document document);

    @Delete("delete from document where id=#{id}")
    Integer deleteDoc(Integer id);
}
