package com.atguigu.online_edu_user.edu.utlis;

import com.atguigu.online_edu_user.edu.entity.MemberCenter;
import org.springframework.util.StringUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtUtils {
	public static final String SUBJECT = "guli";
	//秘钥
	public static final String APPSECRET = "guli";
	public static final long EXPIRE = 1000 * 60 * 30;  //过期时间，毫秒，30分钟


	/**
	 * 根据对象生成jwt的token字符串
	 */
	public static String geneJsonWebToken(MemberCenter member) {

		if (member == null || StringUtils.isEmpty(member.getId())
				|| StringUtils.isEmpty(member.getNickname())
				|| StringUtils.isEmpty(member.getAvatar())) {
			return null;
		}
		String token = Jwts.builder().setSubject(SUBJECT)
				.claim("id", member.getId())
				.claim("nickname", member.getNickname())
				.claim("avatar", member.getAvatar())
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
				.signWith(SignatureAlgorithm.HS256, APPSECRET).compact();
		return token;
	}

	/**
	 * 校验jwt token
	 */
	public static Claims checkJWT(String token) {
		Claims claims = Jwts.parser().setSigningKey(APPSECRET).parseClaimsJws(token).getBody();
		return claims;
	}


}
