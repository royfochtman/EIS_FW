package com.musicbox.server.websocket;

import com.musicbox.util.websocket.WebsocketTextMessage;

import javax.websocket.EncodeException;
import javax.websocket.Session;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by David on 31.12.13.
 */
public class WebsocketTextMessageHandlingThread implements Runnable {
    private MusicRoomSessionContainer musicRoomSessionContainer;
    private WebsocketTextMessage websocketTextMessage;

    public WebsocketTextMessageHandlingThread(MusicRoomSessionContainer musicRoomSessionContainer, WebsocketTextMessage websocketTextMessage) {
        this.musicRoomSessionContainer = musicRoomSessionContainer;
        this.websocketTextMessage = websocketTextMessage;
    }

    @Override
    public void run() {
        try{
            switch (websocketTextMessage.getTextMessageType()) {
                case CHAT:
                    HashMap<String, Session> members = musicRoomSessionContainer.getMusicRoomSessionMembers(websocketTextMessage.getMusicRoomName());
                    if(members != null){
                        for(Session s : members.values()){
                            if(s.isOpen())
                                s.getBasicRemote().sendObject(websocketTextMessage);
                        }
                    }
                    break;
            }
        }catch(IOException | EncodeException ex) {
            ex.printStackTrace();
        }
    }
}
