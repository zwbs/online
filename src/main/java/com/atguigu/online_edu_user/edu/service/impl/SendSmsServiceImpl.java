package com.atguigu.online_edu_user.edu.service.impl;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.atguigu.common.RetVal;
import com.atguigu.online_edu_user.edu.service.SendSmsService;
import lombok.Data;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@ConfigurationProperties(prefix = "sms")
@Data
public class SendSmsServiceImpl implements SendSmsService {

    private String accessKeyId;

    private String accessSecret;

    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;

    @Override
    public RetVal SendSms(String phone) {
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessSecret);
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", "小胖电子商城");
        request.putQueryParameter("TemplateCode", "SMS_193510956");
        int code = (int) (Math.random() * 10000);
        Map<String,Integer> map = new HashMap<>();
        map.put("code", code);
        request.putQueryParameter("TemplateParam", JSON.toJSONString(map));
        try {
            CommonResponse response = client.getCommonResponse(request);
            redisTemplate.opsForValue().set(phone, code, 5, TimeUnit.MINUTES);
            return RetVal.success();
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return RetVal.error();
    }

    @Override
    public RetVal validation(String phone, String code) {
        String.valueOf(code);
        if(StringUtils.isNotEmpty(code)){
            Integer c = (Integer) redisTemplate.opsForValue().get(phone);
            String value = String.valueOf(c);
            if(value.equals(code)){
                redisTemplate.delete(phone);
                return RetVal.success();
            }
        }
        return RetVal.error();
    }
}
