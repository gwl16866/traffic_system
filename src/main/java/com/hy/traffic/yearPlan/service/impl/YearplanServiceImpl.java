package com.hy.traffic.yearPlan.service.impl;

import com.hy.traffic.yearPlan.entity.Yearplan;
import com.hy.traffic.yearPlan.mapper.YearplanMapper;
import com.hy.traffic.yearPlan.service.IYearplanService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
public class YearplanServiceImpl extends ServiceImpl<YearplanMapper, Yearplan> implements IYearplanService {

    @Resource
    private YearplanMapper yearplanMapper;

    @Override
    public List<Yearplan> selectYearplan(Yearplan yearplan) {
        return yearplanMapper.selectYearplan(yearplan);
    }

    @Override
    public List<Yearplan> selectOn(String id) {
        return yearplanMapper.selectOn(id);
    }

    @Override
    public void upd(Yearplan yearplan) {
        yearplanMapper.updateYearplan(yearplan);
    }

    @Override
    public void del(String id) {
        yearplanMapper.del(id);
    }

    @Override
    public void tianjiaYearplan(String title,String bodys) {
         yearplanMapper.tianjiaYearplan(title,bodys);
    }
}
