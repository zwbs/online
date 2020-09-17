package com.atguigu.online_edu_user.edu.controller;

import com.atguigu.common.RetVal;
import com.atguigu.online_edu_user.edu.entity.MemberCenter;
import com.atguigu.online_edu_user.edu.service.MemberCenterService;
import com.atguigu.online_edu_user.edu.utlis.HttpClientUtils;
import com.atguigu.online_edu_user.edu.utlis.JwtUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.Gson;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;


@Controller
@CrossOrigin
@ConfigurationProperties(prefix = "wx.open")
@Data
public class WeCharController {


    private String appId;

    private String appSecret;

    private String redirectUrl;

    @Autowired
    private MemberCenterService memberCenterService;

    @GetMapping("/weChat/login")

    public String login() throws UnsupportedEncodingException {
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";
        String encode = URLEncoder.encode(redirectUrl, "utf-8");
        String url = String.format(baseUrl, appId, encode, "atguigu");
        return "redirect:"+url;
    }

    @GetMapping("/api/ucenter/wx/callback")
    public String getCode(String code,String state) throws Exception {
        String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                "?appid=%s" +
                "&secret=%s" +
                "&code=%s" +
                "&grant_type=authorization_code";
        String format = String.format(baseAccessTokenUrl, appId,appSecret,code);
        String value = HttpClientUtils.get(format);
        Gson gson = new Gson();
        HashMap hashMap = gson.fromJson(value, HashMap.class);
        String accessToken = (String)hashMap.get("access_token");
        String openId = (String)hashMap.get("openid");
        String userInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                "?access_token=%s" +
                "&openid=%s";
        String format1 = String.format(userInfoUrl, accessToken, openId);

        String s = HttpClientUtils.get(format1);
        HashMap userInfo = gson.fromJson(s, HashMap.class);
        String nickName = (String)userInfo.get("nickname");
        String headImgUrl = (String)userInfo.get("headimgurl");
        QueryWrapper<MemberCenter> wrapper = new QueryWrapper<>();
        wrapper.eq("openid",openId);
        MemberCenter existMemberCenter = memberCenterService.getOne(wrapper);
        if(existMemberCenter==null) {
            //把个人信息保存在自己的应用服务器里面
            MemberCenter memberCenter = new MemberCenter();
            memberCenter.setNickname(nickName);
            memberCenter.setAvatar(headImgUrl);
            memberCenter.setOpenid(openId);
            memberCenterService.save(memberCenter);
        }
        //返回一个token信息
        String token = JwtUtils.geneJsonWebToken(existMemberCenter);
        return "redirect:http://127.0.0.1:3000?token="+token;
    }



}
