package cn.itcast.controller;

import org.springframework.web.bind.annotation.PathVariable;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@ServerEndpoint("/notification/{userId}")
public class WebSocket {



    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的
    public static int onlineCount = 0;

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    public Session session;

    /**
     * 连接建立成功调用的方法
     * @param session  可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    @OnOpen
    public void onOpen(@PathVariable("userId") String userId, Session session){
        this.session = session;
        System.out.println(userId);
        addOnlineCount();           //在线数加1

        System.out.println("有新连接加入！当前在线人数为" + getOnlineCount());

    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(){

        subOnlineCount();           //在线数减1

        try {
            if(session != null){
                session.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 收到客户端消息后调用的方法
     * @param message 客户端发送过来的消息
     * @param session 可选的参数
     */
    @OnMessage
    public String onMessage(String message, Session session) {
        System.out.println("来自客户端的消息:" + message);

        if (message.equals("&")){
            return "&";
        }else{

            return "Got your message ("+ message +")";
        }
    }

    /**
     * 发生错误时调用
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error){
        System.out.println("发生错误");

        error.printStackTrace();
    }

    //单发消息
    public void sendMessage(String message) throws IOException {
        //阻塞式（同步）
        //this.session.getBasicRemote().sendText(message);
        //非阻塞式（异步）
        this.session.getAsyncRemote().sendText(message);
    }

    //群发消息
    public void sendMessageAll(String message) throws IOException{

    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocket.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocket.onlineCount--;
    }


}