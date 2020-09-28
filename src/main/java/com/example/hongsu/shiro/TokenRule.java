package com.example.hongsu.shiro;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.hongsu.system.entity.UserBean;
import org.springframework.stereotype.Service;
/**
 *  @author:WJ
 *  @date: 2020-04-07 9:20
 *  @describe:
 * */
@Service
public class TokenRule {

    public String getToken(UserBean user) {
        String token="";
        token= JWT.create().withAudience(String.valueOf(user.getId()))
                .sign(Algorithm.HMAC256(user.getPassword()));
        return token;
    }
}
