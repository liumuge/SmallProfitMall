package cn.itcast.controller;

import cn.itcast.domain.Login;
import cn.itcast.domain.Password;
import cn.itcast.domain.Register;
import cn.itcast.domain.User;
import cn.itcast.response.CommonCode;
import cn.itcast.response.QueryResponseResult;
import cn.itcast.response.QueryResult;
import cn.itcast.service.UserService;
import cn.itcast.util.GetFourRandom;
import cn.itcast.util.SmsUtils;
import com.aliyuncs.exceptions.ClientException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 帐户web
 */
@Controller
@RequestMapping("/user")
@ResponseBody
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private SmsUtils sm;

    @Autowired
    private Login login;

    @Autowired
    private GetFourRandom getFourRandom;
    /**
     * 查询所有方法
     * @return
     */
    @RequestMapping("/findAll")
    public QueryResponseResult findAll(){
        System.out.println("表现层：查询所有账户...");
        // 调用service的方法
        List<User> list = userService.findAll();
        QueryResult<User> result = new QueryResult<>();
        result.setList(list);
        return new QueryResponseResult(CommonCode.SUCCESS,result);
    }

    /**
     * 用户通过账号，密码登录方法
     * @return
     */
    @RequestMapping("/accountLogin")
    public QueryResponseResult accountLogin(@RequestBody User user){
        System.out.println("通过账号密码登录方法登录---");
        User name = userService.findByName(user.getName()); //根据用户名查询
        User phone = userService.findByPhone(user.getName()); //根据手机号查询
        if(user !=null && user.getPassword()!=null){ //判断用户输入是否完整
            if (name == null && phone == null) {  //判断用户是否存在
                return new QueryResponseResult(CommonCode.FAIL, null); //用户不存在
            }
                if(user.getName().length()!=11){  //判断用户是使用用户名登录
                    if(name.getPassword().equals(user.getPassword())){
                        login.setName(name.getName());
                        login.setUid(name.getUid());
                        login.setToken(name.getToken());
                        List<Login> logins= Arrays.asList(login);
                        QueryResult<Login> result = new QueryResult<>();
                        result.setList(logins);
                        return new QueryResponseResult(CommonCode.SUCCESS,result);   //登录成功
                    }else {
                        return new QueryResponseResult(CommonCode.FAIL,null);//密码不正确
                    }
                }else if (user.getName().length()==11){ //判断用户是使用手机号码登录
                    if(phone.getPassword().equals(user.getPassword())){
                        login.setName(phone.getName());
                        login.setUid(phone.getUid());
                        login.setToken(phone.getToken());
                        List<Login> logins= Arrays.asList(login);
                        QueryResult<Login> result = new QueryResult<>();
                        result.setList(logins);
                        return new QueryResponseResult(CommonCode.SUCCESS,result);   //登录成功
                    }else {
                        return new QueryResponseResult(CommonCode.FAIL,null); //密码不正确
                    }
                }
        }else {
            return new QueryResponseResult(CommonCode.FAIL,null); //用户输入信息不完整
        }
        return null;
    }

    /**
     * 注册手机验证接口
     * @param register
     * @return
     * @throws ClientException
     */
    @RequestMapping("/registerVerify")
    public QueryResponseResult registerVerify(@RequestBody Register register , HttpSession session) throws ClientException {
        if (register.getPhone().length()==11){      //判断手机号是否正确
            User phone = userService.findByPhone(register.getPhone()); //根据手机号查询
            if (phone==null){       //手机尚未注册
                String FR = getFourRandom.getFourRandom();
                System.out.println("验证码为 "+FR);
                session.setAttribute("Verify",FR);//设置验证码session
                session.setAttribute("phone",register.getPhone());//设置手机号session
                boolean flag= sm.sendRegistSms(register.getPhone(),FR);
                if(flag){
                    removeAttrbute(session,"Verify");//存入session
                    return new QueryResponseResult(CommonCode.SUCCESS,null);
                }else {
                    return new QueryResponseResult(CommonCode.SERVER_ERROR,null);
                }
            }else {
                return new QueryResponseResult(CommonCode.FALL_USER_REGISTER,null);
            }
        }else{
            return new QueryResponseResult(CommonCode.INVALID_PARAM,null);
        }
    }
    /**
     * 注册
     * @return
     */
    @RequestMapping("/register")
    public QueryResponseResult register(@RequestBody User user,HttpSession session) {
        String Verify =(String) session.getAttribute("Verify");
        String phone =(String) session.getAttribute("phone");
        if(user.getVerify().equals(Verify)&&user.getPhone().equals(phone)){
            userService.saveAccount(user);  //存入对象
            return new QueryResponseResult(CommonCode.SUCCESS,null);//注册成功
        }else{
            return new QueryResponseResult(CommonCode.FAIL,null);//注册失败
        }
    }

    /**
     * 短信验证接口(修改登录密码，支付密码）
     * @param user
     * @param session
     * @return
     * @throws ClientException
     */
    @RequestMapping("/SmVerify")
    public  QueryResponseResult SmVerify(@RequestBody User user , HttpSession session) throws ClientException {
        User user1 =userService.findByPhone(user.getPhone());
        if (user1!=null){
            String FR = getFourRandom.getFourRandom();
            System.out.println("修改验证码为 "+FR);
            boolean flag = sm.sendRegistSms(user1.getPhone(),FR);
            if(flag){
                session.setAttribute("passwordVerify",FR);//存入验证码session
                session.setAttribute("upPasswordPhone",user.getPhone());//手机号存入session
                return new QueryResponseResult(CommonCode.SUCCESS,null);
            }else {
                return new QueryResponseResult(CommonCode.SERVER_ERROR,null);
            }
        }else {
            return new QueryResponseResult(CommonCode.FAIL,null);
        }
    }

    /**
     * 根据手机号码修改接口
     * @param user
     * @param session
     * @return
     */
    @RequestMapping("/updatePasswordPhone")
    public QueryResponseResult updatePasswordPhone(@RequestBody User user,HttpSession session){
        String passwordVerify =(String) session.getAttribute("passwordVerify");
        String phone =(String) session.getAttribute("upPasswordPhone");
        String password = user.getPassword();
        System.out.println(passwordVerify);
        System.out.println(phone);
        if(user.getPhone().equals(phone)){
            if (user.getVerify().equals(passwordVerify)){
                System.out.println(password);
                System.out.println();
                userService.updatePasswordPhone(phone,password);
                System.out.println("执行完成");
                return new QueryResponseResult(CommonCode.SUCCESS,null);
            }else {
                return new QueryResponseResult(CommonCode.FAIL,null);
            }
        }else {
            return new QueryResponseResult(CommonCode.INVALID_PARAM,null);
        }
    }

    /**
     * 根据旧密码修改密码
     * @param
     * @return
     */
    @RequestMapping("/updatePasswordUid")
    public QueryResponseResult updatePasswordUid(@RequestBody Password password){
        User user = userService.findByUid(password.getUid());
        String SqlPassword = user.getPassword();//数据库密码
        String ExpiredPassword =password.getExpiredPassword();//旧密码
        String LatestPassword = password.getLatestPassword();//新密码
        if(SqlPassword.equals(ExpiredPassword)){
            userService.updatePasswordUid(password.getUid(),LatestPassword);
            return new QueryResponseResult(CommonCode.SUCCESS,null);
        }else{
            return new QueryResponseResult(CommonCode.FAIL,null);
        }
    }




    //删除session
    public void removeAttrbute(HttpSession session, String codeName) {
        System.out.println("开始倒计时五分钟");
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                // 删除session中存的验证码
                session.removeAttribute(codeName);
                System.out.println("删除成功");
            }
            //设置时间为五分钟
        }, 10*1000*3000);
    }



}
