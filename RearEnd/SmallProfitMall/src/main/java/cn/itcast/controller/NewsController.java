package cn.itcast.controller;

import cn.itcast.domain.news.News;
import cn.itcast.response.CommonCode;
import cn.itcast.response.QueryResponseResult;
import cn.itcast.response.news.Page;
import cn.itcast.response.news.QueryResponseNews;
import cn.itcast.service.NewsService;
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
 * @author Kite
 * @date 2020/3/29
 */

@Controller
@RequestMapping("/NewsController")
@ResponseBody
public class NewsController {
    @Autowired
    NewsService newsService;

    @Autowired
    WebSocket webSocket;
    /**
     * 查询所有消息
     * @return
     */
    @RequestMapping(value = "/findNews",method = RequestMethod.GET)
    public QueryResponseNews findNews(String userId,Integer currentPage , Integer pageSize) {

        if (currentPage==0){
            currentPage =4;
        }

        //消息数据
        List<News> news = newsService.fendNews(userId,currentPage,pageSize);
        //总数量
        Integer total = newsService.fendTotal(userId);
        Page page = new Page();
        page.setNews(news);
        //总记录时
        page.setTotalCount(total);
        //总页数
        page.setTotalPage(TotalPages.totalPages(pageSize,total));
        return  new QueryResponseNews(CommonCode.SUCCESS,page);
    }

    /**
     *
     * 发送消息测试
     * @param userId
     * @param msg
     * @throws IOException
     */
    @RequestMapping(value = "/close/{userId}/{msg}",method = RequestMethod.POST)
    public QueryResponseResult close(@PathVariable("userId")String userId,@PathVariable("msg")String msg) throws IOException {
        webSocket.sendMessage(userId,msg);

        return new QueryResponseResult(CommonCode.SUCCESS,null);
    }


}