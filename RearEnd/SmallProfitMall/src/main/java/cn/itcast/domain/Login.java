package cn.itcast.domain;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
//登录
@Component("login")
public class Login {
    private String name;
    private String tokens;
    private String uid;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return tokens;
    }

    public void setToken(String token) {
        this.tokens = token;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
