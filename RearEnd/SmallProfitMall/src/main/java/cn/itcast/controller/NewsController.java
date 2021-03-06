package cn.itcast.controller;

import cn.itcast.domain.news.News;
import cn.itcast.response.CommonCode;
import cn.itcast.response.QueryResponseResult;
import cn.itcast.response.news.Page;
import cn.itcast.response.news.QueryResponseNews;
import cn.itcast.service.NewsService;
import cn.itcast.util.logic.ConversionJson;
import cn.itcast.util.logic.TotalPages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;

/**
 * 消息控制层
 * @author Kite
 * @date 2020/3/29
 */

@Controller
@RequestMapping("/NewsController")
@ResponseBody
public class NewsController {
    @Autowired
    NewsService newsService;

    /**
     * 用于消息推送
     */
    @Autowired
    private WebSocket webSocket;

    /**
     * 查询历史消息
     * @param userId 用户id
     * @param state 查询的消息状态
     * @param currentPage  查询的当前页
     * @param pageSize 每页的长度
     * @return
     */
    @RequestMapping(value = "/findNews",method = RequestMethod.GET)
    public QueryResponseNews findNews(String userId,Integer state,Integer currentPage , Integer pageSize) {

        if (currentPage == 0){
            currentPage = 1;
        }
        //消息数据
        List<News> news = newsService.fendNews(userId,state,currentPage,pageSize);
        //总数量
        Integer total = newsService.fendTotal(userId);
        Page page = new Page();
        page.setNews(news);
        //总记录时
        page.setTotalCount(total);
        //总页数
        page.setTotalPage(TotalPages.totalPages(pageSize,total));
        //未读信息数量
        page.setUnreadQuantity(newsService.unreadQuantity(userId));
        return  new QueryResponseNews(CommonCode.SUCCESS,page);
    }


    /**
     * 修改全部已读
     * @param userId 用户id
     * @param contentId 消息id
     * @return 成功或者失败
     */
    @RequestMapping(value = "/updateNewsStatus/{userId}/{contentId}",method = RequestMethod.PUT)
    public QueryResponseResult updateNewsStatus(@PathVariable("userId")String userId,@PathVariable("contentId")Integer contentId){
        Integer redis = newsService.updateNewsStatus(userId,contentId);
        if (redis!=0){
            return new  QueryResponseResult(CommonCode.SUCCESS,null);
        }else {
            return new  QueryResponseResult(CommonCode.FAIL,null);
        }
    }




}
