package cn.xgtd.controller;

import cn.xgtd.domain.product.BasicProduct;
import cn.xgtd.domain.product.ProductCategory;
import cn.xgtd.domain.product.ProductDetails;
import cn.xgtd.domain.product.ProductDistinction;
import cn.xgtd.response.CommonCode;
import cn.xgtd.response.objectReturn.ObjectReturn;
import cn.xgtd.response.objectReturn.ObjectReturnResponse;
import cn.xgtd.response.pagination.Pagination;
import cn.xgtd.response.pagination.ResponsePagination;
import cn.xgtd.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 商品控制层
 * @author Kite
 * @date 2020/6/6
 */
@Controller
@RequestMapping("/ProductController")
@ResponseBody
public class ProductController {

    /**商品业务层**/
    @Autowired
    ProductService productService;

    /**
     * 查询商品
     * @param currentPage 当前页
     * @param pageSize 每页查询数量
     * @return
     */
    @RequestMapping(value = "/findProductList",method = RequestMethod.GET)
    public ResponsePagination findProductList(Integer currentPage,Integer pageSize){
        if (currentPage==null || currentPage==0){
            currentPage=1;
        }
        //判断传入每页显示数量 为空则默认为8条
        if (pageSize==null || pageSize == 0){
            pageSize=8;
        }
        Pagination pagination = new Pagination();
        List<BasicProduct> basicProducts = productService.fendBasicProduct(currentPage,pageSize);
        //查询总数量跟总页数 数组0为总数量 1 为总页数
        Integer[] totalPage=productService.fendTotalPage(pageSize);
        pagination.setTotalCount(totalPage[0].longValue());
        pagination.setTotalPage((int) totalPage[1].longValue());
        pagination.setList(basicProducts);
        return new ResponsePagination(CommonCode.SUCCESS,pagination);
    }

    @RequestMapping(value = "/findDetails/{productId}",method = RequestMethod.GET)
    public ObjectReturnResponse findDetails(@PathVariable("productId") Integer productId){
        ObjectReturn objectReturn = new ObjectReturn();
        ProductDetails productDistinction = productService.findDetails(productId);
        objectReturn.setObject(productDistinction);
        return new ObjectReturnResponse(CommonCode.SUCCESS,objectReturn);
    }

    @RequestMapping(value = "/findCategory",method = RequestMethod.GET)
    public ObjectReturnResponse findCategory() {
        ObjectReturn objectReturn = new ObjectReturn();
        List<ProductCategory> productDistinction = productService.findCategory();
        objectReturn.setObject(productDistinction);
        return new ObjectReturnResponse(CommonCode.SUCCESS,objectReturn);
    }
}
