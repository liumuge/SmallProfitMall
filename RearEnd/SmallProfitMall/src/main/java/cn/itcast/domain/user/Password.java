package cn.itcast.domain.user;

import org.apache.ibatis.annotations.Select;
//修改密码
public class Password {
    private String uid;
    private String expiredPassword;
    private String latestPassword;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getExpiredPassword() {
        return expiredPassword;
    }

    public void setExpiredPassword(String expiredPassword) {
        this.expiredPassword = expiredPassword;
    }

    public String getLatestPassword() {
        return latestPassword;
    }

    public void setLatestPassword(String latestPassword) {
        this.latestPassword = latestPassword;
    }
}
