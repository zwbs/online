package com.atguigu.online_edu_user.edu.controller;

import com.atguigu.common.RetVal;
import com.atguigu.online_edu_user.edu.service.SendSmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/sms")
public class SendSmsController {

    @Autowired
    private SendSmsService sendSmsService;

    @GetMapping("{phone}")
    public RetVal SendSms(@PathVariable String phone){
        return sendSmsService.SendSms(phone);
    }

    @GetMapping("{phone}/{code}")
    public RetVal validation(@PathVariable String phone,
                             @PathVariable String code){
        return sendSmsService.validation(phone,code);
    }
}
