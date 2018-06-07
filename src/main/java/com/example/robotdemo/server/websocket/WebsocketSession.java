package com.example.robotdemo.server.websocket;

import com.example.robotdemo.server.TransportMessage;
import com.example.robotdemo.server.session.ISession;
import com.example.robotdemo.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.Session;
import java.io.IOException;

/**
 * @author ning
 * 创建于 2018年1月16日下午3:52:44
 * //TODO websocket session存储
 */
public class WebsocketSession implements ISession {
    private static Logger logger = LoggerFactory.getLogger(WebsocketSession.class);
    private Session session;
    
    public WebsocketSession(Session session) {
        this.session = session;
    }
    
    @Override
    public void write(TransportMessage msg) {
        try {
            String text = JsonUtil.toJsonFromObj(msg);
            if(session != null && session.isOpen()){
                synchronized(session){
                    session.getBasicRemote().sendText(text);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getId() {
        return session.getId();
    }

    @Override
    public void close() throws IOException {
        synchronized(this.session){
            if(this.session.isOpen()){
                this.session.close();
            }
        }
    }

    @Override
    public boolean isOpen() {
        return this.session.isOpen();
    }

}
