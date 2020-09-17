package com.atguigu.online_edu_user.edu.service.impl;

import com.atguigu.common.RetVal;
import com.atguigu.online_edu_user.edu.entity.MemberCenter;
import com.atguigu.online_edu_user.edu.mapper.MemberCenterMapper;
import com.atguigu.online_edu_user.edu.service.MemberCenterService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author zwb
 * @since 2020-07-01
 */
@Service
public class MemberCenterServiceImpl extends ServiceImpl<MemberCenterMapper, MemberCenter> implements MemberCenterService {

    @Override
    public RetVal queryRegisterNum(String day) {
        QueryWrapper<MemberCenter> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("DATE(gmt_create)",day);
        Integer count = baseMapper.selectCount(queryWrapper);
        return RetVal.success().data("count",count);
    }
}
