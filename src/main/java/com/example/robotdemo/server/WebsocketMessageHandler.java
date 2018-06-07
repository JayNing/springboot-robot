package com.example.robotdemo.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.robotdemo.entity.ReturnMsg;
import com.example.robotdemo.server.contants.ServerContant;
import com.example.robotdemo.server.session.ISession;
import com.example.robotdemo.server.session.ServerSessionManager;
import com.example.robotdemo.server.session.SessionContext;
import com.example.robotdemo.util.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 接收消息处理
 *
 * @author ning
 */
@Component
public class WebsocketMessageHandler {

    private static Logger logger = LoggerFactory.getLogger(WebsocketMessageHandler.class);

    @Autowired
    private ServerSessionManager sessionManager;

    private static final String REQUEST_METHOD_NAME = "requestMethod";

    public void handleMsg(ISession session, String body) throws IOException {
        JSONObject jsonObject = JSON.parseObject(body);
        Object requestMethodObj = jsonObject.get(REQUEST_METHOD_NAME);
        if (requestMethodObj != null) {
            String method = jsonObject.get(REQUEST_METHOD_NAME).toString();
            if (ServerContant.PONG_METHOD.equals(method)) {
                sessionManager.keepAlive(session);
            } else {
                ReturnMsg respMsg = new ReturnMsg();
                respMsg.setErrorCode(0);
                respMsg.setData("我是websocket，我被调用了，时间 ： " + LocalDateTime.now());
                session.write(ProtocolResponseBuilder.responseMsg(respMsg, method));
            }
        } else {
            session.close();
        }
    }

    public void broadcastInfo(String robotId, Object msg) {
        List<SessionContext> buildSessions = sessionManager.buildSessions(robotId);
        if (!CommonUtil.isListEmpty(buildSessions)) {
            for (SessionContext sessionContext : buildSessions) {
                ReturnMsg respMsg = new ReturnMsg();
                respMsg.setData(msg);
                respMsg.setErrorCode(0);
                sessionContext.write(ProtocolResponseBuilder.responseMsg(respMsg, "broadcastInfo"));
            }
        }
    }
}
