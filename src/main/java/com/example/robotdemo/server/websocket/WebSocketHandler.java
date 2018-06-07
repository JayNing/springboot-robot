package com.example.robotdemo.server.websocket;

import com.example.robotdemo.server.WebsocketMessageHandler;
import com.example.robotdemo.server.contants.ServerContant;
import com.example.robotdemo.server.session.ServerSessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;


/**
 * @author ning
 */
@ServerEndpoint(value = "/websocket/{robotId}")
@Component
public class WebSocketHandler {

    private static Logger logger = LoggerFactory.getLogger(WebSocketHandler.class);

    private static ApplicationContext applicationContext;

    private static WebsocketMessageHandler websocketMessageHandler;

    private static ServerSessionManager sessionManager;

    private WebsocketSession session;

    public static void setApplicationContext(ApplicationContext context) {
        applicationContext = context;
        sessionManager = applicationContext.getBean(ServerSessionManager.class);
        websocketMessageHandler = applicationContext.getBean(WebsocketMessageHandler.class);
    }

    private ScheduledThreadPoolExecutor pingPool = new ScheduledThreadPoolExecutor(1, new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r);
            t.setName("websocket-ping-check");
            return t;
        }
    });

    @OnOpen
    public void onOpen(@PathParam(value = "robotId") String robotId, Session wsSession, EndpointConfig ec) {
        logger.info("有新连接加入！------" + robotId);
        this.session = new WebsocketSession(wsSession);
//        ServerSessionManager sessionManager = applicationContext.getBean(ServerSessionManager.class);
        sessionManager.add(robotId, session);
        logger.info("有新连接加入！" + robotId);
        logger.info("sessionManager size : " + sessionManager.buildSessions(robotId).size());
        addPingTask();
    }

    private void addPingTask() {
        pingPool.schedule(new Runnable() {
            @Override
            public void run() {
                if (session.isOpen()) {
                    session.write(ServerContant.PING_MESSAGE);
                    addPingTask();
                }
            }
        }, ServerContant.PING_INTERVAL_SECONDS, TimeUnit.SECONDS);
    }

    @OnClose
    public void onClose() {
        logger.info("有一连接关闭！" + session.getId());
        //将session清除
        synchronized(session){
//            ServerSessionManager sessionManager = applicationContext.getBean(ServerSessionManager.class);
            sessionManager.removeSession(session);
        }
    }

    @OnMessage
    public void onMessage(String message) throws Exception {
        logger.info("来自客户端的消息:" + message);
//        WebsocketMessageHandler websocketMessageHandler = applicationContext.getBean(WebsocketMessageHandler.class);
        websocketMessageHandler.handleMsg(session, message);
    }

    @OnError
    public void onError(Throwable error) {
        logger.info("onError 发生错误" + session.getId());
        logger.info("error ： " + error);
        //将session清除
//        ServerSessionManager sessionManager = applicationContext.getBean(ServerSessionManager.class);
        sessionManager.removeSession(this.session);

    }

}
