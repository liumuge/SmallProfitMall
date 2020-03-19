package cn.itcast.controller;

import cn.itcast.domain.address.Address;
import cn.itcast.domain.user.User;
import cn.itcast.response.CommonCode;
import cn.itcast.response.QueryResponseResult;
import cn.itcast.response.QueryResult;
import cn.itcast.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/AddressController")
@ResponseBody
public class AddressController {

    @Autowired
    AddressService addressService;


    @Autowired
    QueryResult result;
    /**
     * 新增地址
     *
     * @return
     */
    @RequestMapping(value = "/addAddress",method = RequestMethod.POST)
    public QueryResponseResult addAddress(@RequestBody Address address) {
         int redis=addressService.addAddress(address);
       if (redis==1) {
            return new QueryResponseResult(CommonCode.SUCCESS, null);//注册成功
            } else {
            return new QueryResponseResult(CommonCode.FAIL, null);//注册失败
       }
    }

    //根据id查询用户信息
    @RequestMapping("/findById")
    public QueryResponseResult findById(String userId) {
        List<Address> address =addressService.findById(userId);
        List<Address>[] redis= new List[]{address};
        result.setAddressList(redis);
        return new QueryResponseResult(CommonCode.SUCCESS, result);//注册成功
    }
}