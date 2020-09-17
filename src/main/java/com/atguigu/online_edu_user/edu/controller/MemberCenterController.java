package com.atguigu.online_edu_user.edu.controller;


import com.atguigu.common.RetVal;
import com.atguigu.online_edu_user.edu.service.MemberCenterService;
import com.atguigu.online_edu_user.edu.utlis.JwtUtils;
import com.atguigu.vo.MemberCenterVo;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author zwb
 * @since 2020-07-01
 */
@RestController
@RequestMapping("/user")
@CrossOrigin
public class MemberCenterController {

    @Autowired
    private MemberCenterService memberCenterService;

    @GetMapping("{day}")
    public RetVal queryRegisterNum(@PathVariable("day") String day){
        return memberCenterService.queryRegisterNum(day);
    }
    //2.通过token获取用户信息
    @GetMapping("getUserInfoByToken/{token}")
    public RetVal getUserInfoByToken(@PathVariable String token)
    {
        Claims claims = JwtUtils.checkJWT(token);
        String id = (String)claims.get("id");
        String nickname = (String)claims.get("nickname");
        String avatar = (String)claims.get("avatar");
        MemberCenterVo memberCenterVo = new MemberCenterVo();
        memberCenterVo.setId(id);
        memberCenterVo.setNickName(nickname);
        memberCenterVo.setAvatar(avatar);
        return RetVal.success().data("memberCenterVo",memberCenterVo);
    }
}

