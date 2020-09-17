package com.atguigu.online_edu_user.edu.service;

import com.atguigu.common.RetVal;
import com.atguigu.online_edu_user.edu.entity.MemberCenter;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author zwb
 * @since 2020-07-01
 */
public interface MemberCenterService extends IService<MemberCenter> {

    RetVal queryRegisterNum(String day);
}
