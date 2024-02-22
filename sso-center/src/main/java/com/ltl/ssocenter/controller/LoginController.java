package com.ltl.ssocenter.controller;

import cn.hutool.json.JSONObject;
import com.ltl.ssocenter.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/sso")
@Slf4j
public class LoginController {

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @PostMapping("/login")
    @ResponseBody
    public String login(@RequestBody LoginRequest loginRequest,
                      @RequestParam("redirect") String redirectUrl,
                      HttpServletResponse response) throws Exception {
        // 在这里执行你的登录逻辑
        if (! ("admin".equals(loginRequest.getUsername()) && "admin".equals(loginRequest.getPassword()))){
            return "login failure...";
        }

        // url拼接token
        String token = JwtUtil.encode("{'name': 'mike'}", "secretKey", 3600);
        redirectUrl = redirectUrl + "?token=" + token;
        return redirectUrl;
    }

    @GetMapping("/checkJwt")
    @ResponseBody
    public JSONObject checkJwt(@RequestParam("token") String token){
        try {
            return JwtUtil.decode(token, "secretKey");
        }catch (Exception e){
            log.error(String.valueOf(e));
            return null;
        }
    }


    // 内部类用于接收登录请求的数据
    static class LoginRequest {
        private String username;
        private String password;

        // getters and setters
        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
