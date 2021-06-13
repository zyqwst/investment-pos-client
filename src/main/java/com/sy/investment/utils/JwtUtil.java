package com.sy.investment.utils;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;

import com.sy.investment.exceptions.UnAuthorizedException;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

/** 
* @ClassName: JwtUtil 
* @Description: 
* @author albert
* @date 2018年2月3日 上午11:10:06 
*  
*/
public class JwtUtil {
	private final static Base64.Encoder m_encoder = Base64.getEncoder();
	/**
	 * @param id	  随机字符
	 * @param subject 用户信息json字符串
	 * @param ttlMillis 过时秒数，负数表示不过时
	 * @param secretKey 秘钥
	 * @return
	 */
	public static String createJWT(String id, String subject, long ttlMillis,String secretKey) {

	    SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
	    long nowMillis = System.currentTimeMillis();
	    
	    byte[] apiKeySecretBytes = m_encoder.encode(secretKey.getBytes());
	    Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
	    JwtBuilder builder = Jwts.builder()
	    							.setId(id)
	                                .setSubject(subject)
	                                .signWith(signatureAlgorithm, signingKey);
	    if (ttlMillis >= 0) {
	    long expMillis = nowMillis + ttlMillis*1000;
	        Date exp = new Date(expMillis);
	        builder.setExpiration(exp);
	    }
	    return builder.compact();
	}
	/**
	 * 
	 * @param jwt
	 * @param secretKey
	 */
	public static void parseJWT(String jwt,String secretKey) throws IllegalArgumentException{
	    try {
			Jwts.parser()         
			   .setSigningKey(m_encoder.encode(secretKey.getBytes()))
			   .parseClaimsJws(jwt).getBody();
		} catch (ExpiredJwtException e) {
			throw new UnAuthorizedException("鉴权过期");
		} catch (UnsupportedJwtException e) {
			throw new UnAuthorizedException("鉴权错误");
		} catch (MalformedJwtException e) {
			throw e;
		} catch (SignatureException e) {
			throw new UnAuthorizedException("鉴权不通过");
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			throw e;
		}
	}
}
