package com.atguigu.online_edu_user.edu.service;

import com.atguigu.common.RetVal;

public interface SendSmsService {
    RetVal SendSms(String phone);

    RetVal validation(String phone, String code);
}
