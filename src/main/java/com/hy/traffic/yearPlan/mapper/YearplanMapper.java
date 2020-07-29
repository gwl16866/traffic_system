package com.hy.traffic.yearPlan.mapper;

import com.hy.traffic.yearPlan.entity.Yearplan;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
public interface YearplanMapper extends BaseMapper<Yearplan> {


    @SelectProvider(type = YearplanProvider.class, method = "selectYearplan")
    public List<Yearplan> selectYearplan(Yearplan yearplan);

    @Update("update yearplan set title =#{title},bodys=#{bodys} where id=#{id}")
    public void updateYearplan(Yearplan yearplan);

    @Insert("insert into yearplan(title,bodys) values (#{title},#{bodys})")
    public void tianjiaYearplan(String title,String bodys);

    @Select("select title,bodys from yearplan where id=#{value}")
    public List<Yearplan> selectOn(String id);

    @Delete("delete from yearplan where id=#{value}")
    public void del(String id);

}
