package cn.itcast.dao;

import cn.itcast.domain.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ProductDao {
    //查询秒杀商品表
    public List<Seckill> findSeckill (@Param("StartingValue")int StartingValue ,@Param("Quantity") int Quantity);

    //查询低价商品
    public List<ProductLowPrice> findProductLowPrice(@Param("StartingValue")int StartingValue ,@Param("Quantity") int Quantity);

    //广告
    public List<Ad> findAd();

    //查询所有为你推荐
    public List<Recommend> findRecommend();
}
