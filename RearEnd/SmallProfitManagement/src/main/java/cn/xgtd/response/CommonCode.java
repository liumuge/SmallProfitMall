package cn.xgtd.response;

import cn.xgtd.response.ResultCode;
import lombok.ToString;

/**
 * @Author: mrt.
 * @Description:
 * @Date:Created in 2018/1/24 18:33.
 * @Modified By:
 */

@ToString
public enum CommonCode implements ResultCode {

    INVALID_PARAM(false,10003,"参数非法"),
    SUCCESS(true,10000,"操作成功！"),
    FILE_COMPOSITE(true,10100,"文件以合成"),
    NO_FILE_COMPOSITE(true,10101,"文件未合成"),
    PORTION_SUCCESS(true,10002,"部分成功"),
    FAIL(false,11111,"操作失败！"),
    FILE_INEXISTENCE(false,20001,"文件不存在"),
    FILE_ERROR(false,12221,"文件出错"),
    FALL_USER_REGISTER(false,11211,"用户已存在!"),
    UNAUTHENTICATED(false,10001,"此操作需要登陆系统！"),
    UNAUTHORISE(false,10002,"权限不足，无权操作！"),
    SERVER_ERROR(false,99999,"抱歉，系统繁忙，请稍后重试！");

    //操作是否成功
    boolean success;
    //操作代码
    int code;
    //提示信息
    String message;

    private CommonCode(boolean success,int code, String message){
        this.success = success;
        this.code = code;
        this.message = message;
    }

    @Override
    public boolean success() {
        return success;
    }
    @Override
    public int code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }


}
