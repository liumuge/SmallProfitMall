package cn.itcast.service;

import cn.itcast.domain.*;

import java.util.List;

public interface HomepageService {
    //查询轮播图图片
    public List<RotationChart> findRotationChart();

    //商品分类导航栏
    public List<Navigation> findNavigation();

    //商品分类导航栏2
    public List<Navigation_2> findNavigation2();


    //查询图标
    public List<Icon> findIcon();

    public List<NavigationClassify> findById(int id);

    public Classification navigationInDetail();

    public List<NavigationClassify> findClassification();
}
