package cn.itcast.controller;

import cn.itcast.domain.shoppingCar.PurchaseInformation;
import cn.itcast.domain.shoppingCar.ShoppingCart;
import cn.itcast.response.CommonCode;
import cn.itcast.response.QueryResponseResult;
import cn.itcast.response.QueryResult;
import cn.itcast.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 购物车控制层
 * @author 86185
 */
@Controller
@RequestMapping("/ShoppingCartController")
@ResponseBody
public class ShoppingCartController {

    /**购物车业务层**/
    @Autowired
    ShoppingCartService shoppingCartService;
    /**
     * 添加购物车
     * @param purchaseInformation 购物车对象
     * @return
     */
    @RequestMapping(value = "/addShoppingCart",method = RequestMethod.POST)
    public QueryResponseResult addShoppingCart(@RequestBody PurchaseInformation purchaseInformation) {
        QueryResult queryResult = new QueryResult();
        if(purchaseInformation==null|purchaseInformation.getUserId()==null){
            //传入参数为空
            return new QueryResponseResult(CommonCode.FAIL, null);
        }
        if (purchaseInformation.getQuantity()>99){
            //添加商品失败 单个商品大于99
            return new QueryResponseResult(CommonCode.INVALID_PARAM, null);
        }
        int[] redis =shoppingCartService.addShoppingCar(purchaseInformation);
        if(redis[0]==1){
            //添加成功
            queryResult.setTotal(redis[1]);
            return new QueryResponseResult(CommonCode.SUCCESS, queryResult);
        }else if (redis[1]==2){
            //添加失败 购物车数量大于99
            return new QueryResponseResult(CommonCode.FAIL, null);
        }else if (redis[0]==3){
            //添加失败 单个商品数量大于99
            return new QueryResponseResult(CommonCode.INVALID_PARAM, null);
        }
        //添加失败 添加数据失败
        return new QueryResponseResult(CommonCode.FAIL, null);
    }

    /**
     * 查询购物车
     * @param userId 用户id
     * @return
     */
    @RequestMapping(value = "/findByUserId/{userId}",method = RequestMethod.GET)
    public QueryResponseResult findByUserId(@PathVariable("userId")String userId) {
        QueryResult queryResult = new QueryResult();
        if(userId==null){
            //传入参数为空
            return new QueryResponseResult(CommonCode.FAIL, null);
        }
         List<ShoppingCart> shoppingCart =shoppingCartService.findByUserId(userId);
            queryResult.setList(shoppingCart);
            queryResult.setTotal(shoppingCart.size());
            return new QueryResponseResult(CommonCode.SUCCESS, queryResult);
    }

    /**
     * 删除购物车
     * @param cartIdList 购物车id数组
     * @return
     */
    @RequestMapping(value = "/deleteCart/{shoppingCartId}",method = RequestMethod.DELETE)
    public QueryResponseResult deleteCart(@PathVariable("shoppingCartId")Integer[] cartIdList) {
        if (cartIdList == null) {
            //查询传入参数为空
            return new QueryResponseResult(CommonCode.FAIL, null);
        }
        int redis = shoppingCartService.deleteCart(cartIdList);
        if (redis == 1) {
            //删除购物车成功
            return new QueryResponseResult(CommonCode.SUCCESS, null);
        } else {
            //删除购物车失败
            return new QueryResponseResult(CommonCode.FAIL, null);
        }
    }

    /**
     * 添加到货通知
     * @param userId 用户id
     * @param productId 商品id
     * @param distinctionId 配置id
     * @return
     */
    @RequestMapping(value = "/addArrivalNotice/{userId}/{productId}/{distinctionId}",method = RequestMethod.POST)
    public QueryResponseResult addArrivalNotice(@PathVariable("userId")String userId,@PathVariable("productId") Integer productId ,@PathVariable("distinctionId") Integer distinctionId){
        if (userId ==null && productId ==0){
            //传入参数为空
            return new QueryResponseResult(CommonCode.FAIL, null);
        }
        int redis = shoppingCartService.addArrivalNotice(userId,productId,distinctionId);
        QueryResult queryResult = new QueryResult();
        if (redis==1){
            return  new QueryResponseResult(CommonCode.SUCCESS, queryResult);
        }else {
            return new QueryResponseResult(CommonCode.FAIL,null);
        }
    }

    /**
     * 修改商品数量
     * @param quantity 修改的数量
     * @param shoppingCartId 购物车id
     * @return
     */
    @RequestMapping(value = "/updateQuantity/{quantity}/{shoppingCartId}",method = RequestMethod.PUT)
    public QueryResponseResult updateQuantity(@PathVariable("quantity")Integer quantity,@PathVariable("shoppingCartId") int shoppingCartId){
        if (quantity == 0 && shoppingCartId == 0){
            //传入参数为空
            return new QueryResponseResult(CommonCode.FAIL, null);
        }
        int redis = shoppingCartService.updateQuantity(quantity,shoppingCartId);
        QueryResult queryResult = new QueryResult();
        if (redis==1){
            return  new QueryResponseResult(CommonCode.SUCCESS, queryResult);
        }else {
            return new QueryResponseResult(CommonCode.FAIL,null);
        }
    }

    /**
     * 查询预览商品
     * @param userId 用户id
     * @param Ends 每页查询多少天
     * @return
     */
    @RequestMapping(value = "/findPreview/{userId}/{Ends}",method = RequestMethod.GET)
    public QueryResponseResult findPreview(@PathVariable("userId")String userId,@PathVariable("Ends")String Ends){
        if (userId == null){
            //传入参数为空
            return new QueryResponseResult(CommonCode.FAIL, null);
        }
        if (Ends==null){
            Ends="4";
        }
        List<ShoppingCart> redis = shoppingCartService.findPreview(userId,Integer.parseInt(Ends));
        //查询购物车中商品数
        Integer quantity = shoppingCartService.findByuId(userId);
        QueryResult queryResult = new QueryResult();
        queryResult.setList(redis);
        queryResult.setTotal(quantity);
        return  new QueryResponseResult(CommonCode.SUCCESS, queryResult);

    }

}
