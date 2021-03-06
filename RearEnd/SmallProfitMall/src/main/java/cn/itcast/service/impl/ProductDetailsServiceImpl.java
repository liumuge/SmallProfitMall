package cn.itcast.service.impl;

import cn.itcast.dao.ProductDetailsDao;
import cn.itcast.domain.ProductDatails.*;
import cn.itcast.service.ProductDetailsService;
import cn.itcast.util.redis.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//商品详细
@Service("productDetailsServiceImpl")
public class ProductDetailsServiceImpl implements ProductDetailsService {

    @Autowired
    private ProductDetailsDao productDetailsDao;

    /**缓存工具**/
    @Autowired
    private RedisUtil redisUtil;

    /**
     * 商品详细分类
     * @param productId 商品id
     * @return 商品详细对象
     */
    @Override
    public ProductDetailsResult findByPid(Integer productId) {
        //设置缓存id
        String transition = String.valueOf(productId);
        String productIds ="productId_"+transition;
        //从缓存中查询是否存在
        ProductDetailsResult  redis = (ProductDetailsResult)redisUtil.get(productIds);
        if(redis==null){
            //设置商品配置信息
           ProductDetailsResult productDetailsResult = setProductConfiguration(productId);

            productDetailsResult.setProductContexts(null);

            //查询不同配置集合 库存 价格
            List<ProductDistinction> productDistinctions = productDetailsDao.findProductDistinction(productId);
            for (int i = 0; i <productDistinctions.size() ; i++) {
                String productInventory = findInventory( productDistinctions.get(i).getProductInventory());
                productDistinctions.get(i).setProductInventorys(productInventory);
            }
            productDetailsResult.setProductDistinctions(productDistinctions);
            //库存价格(转换)
            String inventory =findInventory(productDetailsResult.getProductInventory());
            //商品销量(转换)
            String sale =findSale(productDetailsResult.getProductSales());
            if (productDetailsResult.getProductSales()==0){
                //如果销量为空 则设置为零
                productDetailsResult.setSales("0");
            }
            //库存为空 则设置为零
            if (productDetailsResult.getProductInventory()==0) {
                productDetailsResult.setInventory("0");
            }
            //设置转换后的库存
            productDetailsResult.setInventory(inventory);
            //设置转换后的销量
            productDetailsResult.setSales(sale);
            redisUtil.set(productIds,productDetailsResult);
            return productDetailsResult;
        }else {
            ProductDetailsResult productDetailsResult1 = productDetailsDao.findSalesInventory(productId);
            //从缓存中取 跟新商品的销量跟库存
            //查询当前库存
           double intInventory = productDetailsResult1.getProductInventory();
            //格式转换
            String productInventorys = findInventory(intInventory);
            //设置数字库存
            redis.setProductInventory(intInventory);
            //设置转换后的字符串库存
            redis.setInventory(productInventorys);
            if (intInventory==0) {
                redis.setInventory("0");
            }
            //查询当前销量
            Double intProductSales = productDetailsResult1.getProductSales();
            //设置数组销量
            redis.setProductSales(intProductSales);
            //格式转换
            String productSale = findSale(intProductSales);
            //设置转换格式后的销量
            redis.setSales(productSale);
            if (intProductSales==0){
                //如果销量为空 则设置为零
                redis.setSales("0");
            }
            List<ProductDistinction> productDistinctions = productDetailsDao.findProductDistinction(productId);
            for (int i = 0; i <productDistinctions.size() ; i++) {
              String productInventory = findInventory( productDistinctions.get(i).getProductInventory());
                productDistinctions.get(i).setProductInventorys(productInventory);
            }
            redis.setProductDistinctions(productDistinctions);

            return redis;
        }

    }


    /**
     * 商品介绍 商品保障 商品参数
     * @param productId
     * @return
     */
    @Override
    public ProductDetailsResult findProductDesciption(Integer productId){
        //查询患者中是否有
        ProductDetailsResult productDetailsResult = (ProductDetailsResult) redisUtil.get("ProductDetailsResult_"+productId);
        if (productDetailsResult == null){
            //数据库中取商品介绍数据
            ProductDetailsResult productDetailsResults = productDetailsDao.findProductDesciption(productId);
            if (productDetailsResults != null){
                redisUtil.set("ProductDetailsResult_"+productId,productDetailsResults);
            }
            return productDetailsResults;
        }
        //缓存中取商品介绍数据
         return productDetailsResult;
    }


    /**
     *查询商品的推荐
     * @param productId 商品id
     * @return
     */
    @Override
    public List<Recommend> findRecommend(Integer productId){
        //查询页起始点
        Integer start = 0;
        //每页查询数量
        Integer pageSize = 3;
        List<Recommend> recommends = new ArrayList<>();
        List<Recommend> recommends1 = productDetailsDao.findFinalRecommend(productId,start,pageSize,1);
        for(Recommend recommend : recommends1){
            recommends.add(recommend);
        }

        for (int i = 2; i <5 ; i++) {
            if (recommends.size()<3){
                pageSize=3-recommends.size();
                List<Recommend> recommends2 = productDetailsDao.findFinalRecommend(productId,start,pageSize,i);
                for(Recommend recommend : recommends2){
                    recommends.add(recommend);
                }
            }else {
                break;
            }

        }
        return recommends;
    }

    /**
     * 设置商品配置
     * @param productId 商品id
     * @return 商品对象
     */
    public ProductDetailsResult setProductConfiguration(Integer productId){
        ProductDetailsResult productDetailsResult = productDetailsDao.fendProduct(productId);
        List<ProductContext> productContexts = productDetailsResult.getProductContexts();

        if (productContexts!=null){
            //颜色集合
            List<ProductContext> colourList = new  ArrayList();
            //版本集合
            List<ProductContext> versionList = new ArrayList<>();
            //规格集合
            List<ProductContext> specificationList = new ArrayList<>();
            //种类
            List<ProductContext> kindList = new ArrayList<>();
            //尺码
            List<ProductContext> sizeList = new ArrayList<>();
            //口味
            List<ProductContext> tasteList = new ArrayList<>();
            //套餐
            List<ProductContext> comboList = new ArrayList<>();
            //设置为空
            productDetailsResult.setColour(colourList);
            productDetailsResult.setVersion(versionList);
            productDetailsResult.setSpecification(specificationList);
            productDetailsResult.setKind(kindList);
            productDetailsResult.setSize(sizeList);
            productDetailsResult.setTaste(tasteList);
            productDetailsResult.setCombo(comboList);

            for (int i = 0; i <productContexts.size() ; i++) {
                //该商品属性类型
                Integer type = productContexts.get(i).getTitleId();
                //当前属性
                ProductContext productContext = productContexts.get(i);
                productContext.setAttributeType(null);

                switch(type){
                    case 11 :
                        colourList.add(productContext);
                        break;
                    case 16 :
                        versionList.add(productContext);
                        break;
                    case 13 :
                        specificationList.add(productContext);
                        ;break;
                    case 10 :
                        sizeList.add(productContext);
                        break;
                    case 15 :
                        kindList.add(productContext);
                        ;break;
                    case 14 :
                        tasteList.add(productContext);
                        ;break;
                    case 12 :
                        comboList.add(productContext);
                        ;break;
                }

            }
        }
        return productDetailsResult;
    }

    /**
     * 商品库存转换
     * @param productInventory
     * @return
     */
    public String findInventory(Double productInventory){

        //库存价格(转换)
        String inventory = "";
        //库存转换 大于10000转换微1w
        if (productInventory > 10000) {
            //取小数点后一位
            java.text.DecimalFormat   df=new   java.text.DecimalFormat("#.#");
            Double inventorys = productInventory;
            Double a = inventorys/10000;
            inventory = df.format(a);
            //库存过万转换
            inventory = inventory + "W+";
        }else {
            //转为字符串类型
            inventory=""+productInventory;
            //取小数点前数组
            inventory =inventory.split("\\.")[0];
        }

        return inventory;
    }

    /**
     * 商品销量转换
     * @param productSales
     * @return
     */
    public String findSale(Double productSales){
        //商品销量(转换)
        String sale = "";
        if (productSales>10000){
            java.text.DecimalFormat   df=new   java.text.DecimalFormat("#.#");
            Double sales = productSales;
            Double a = sales/10000;
            sale = df.format(a);
            //销售过完转换
            sale=sale+"W+";
        }else {
            sale=""+productSales;
            sale =sale.split("\\.")[0];
        }

        return sale;
    }

}
