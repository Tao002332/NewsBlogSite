package com.newsblogsite.friend.interceptor;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * *@author 83614
 * *@date 2020/2/25
 **/

@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("经过拦截");
        String header = request.getHeader("Authorization");
        if(header!=null && !"".equals(header)) {
            if(header.startsWith("Bearer ")) {
                String token = header.substring(7);
                try{
                    Claims claims = jwtUtil.parseJWT(token);
                    String roles = (String)claims.get("roles");
                    request.setAttribute("loginId",claims.getId());
                    if(roles!=null && "admin".equals(roles)) {
                        throw  new RuntimeException("管理员无法操作");
                    }
                    if(roles!=null && "user".equals(roles)) {
                        request.setAttribute("userClaims",token);
                    }
                } catch (Exception e) {
                    throw  new RuntimeException("token不正确");
                }
            }
        }
        return true;
    }
}
