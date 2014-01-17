package com.musicbox.server.websocket;

import com.musicbox.util.MusicRoomDataContainer;
import com.musicbox.util.database.DatabaseManager;
import com.musicbox.util.database.entities.MusicRoom;
import com.musicbox.util.globalobject.GlobalObject;
import com.musicbox.util.websocket.WebsocketMessage;
import com.musicbox.util.websocket.WebsocketMessageType;
import com.musicbox.util.websocket.WebsocketTextMessage;
import com.musicbox.util.websocket.WebsocketTextMessageType;

import javax.websocket.EncodeException;
import javax.websocket.Session;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by David on 31.12.13.
 */
public class WebsocketMessageHandlingThread implements Runnable {
    private MusicRoomSessionContainer musicRoomSessionContainer;
    private WebsocketMessage websocketMessage;
    private Session session;

    public WebsocketMessageHandlingThread(MusicRoomSessionContainer musicRoomSessionContainer, WebsocketMessage websocketMessage, Session session) {
        this.musicRoomSessionContainer = musicRoomSessionContainer;
        this.websocketMessage = websocketMessage;
        this.session = session;
    }

    @Override
    public void run() {
        ArrayList<GlobalObject> data = websocketMessage.getData();
        try{
            switch ( websocketMessage.getMessageType()){
                case NEW_MUSIC_ROOM:
                    MusicRoom musicRoom = (MusicRoom)data.get(0);
                    if(data != null && !data.isEmpty() && DatabaseManager.insertMusicRoom((MusicRoom)data.get(0))) {
                        musicRoomSessionContainer.putMusicRoomSession(musicRoom.getName(), session);
                        session.getBasicRemote().sendObject(new WebsocketMessage(musicRoom.getName(), WebsocketMessageType.NEW_MUSIC_ROOM, data));
                    }
                    else
                        session.getBasicRemote().sendText(new WebsocketTextMessage(musicRoom.getName(), WebsocketTextMessageType.ERROR, WebsocketServerEndpoint.ERROR_CREATE_ROOM).toString());
                    break;
                case JOIN_MUSIC_ROOM:
                    if(musicRoomSessionContainer.putNewMemberInSession(websocketMessage.getMusicRoomName(), session)) {
                        MusicRoomDataContainer dataContainer = DatabaseManager.getCompleteMusicRoomDataByMusicRoomName(websocketMessage.getMusicRoomName());
                        data = new ArrayList<>();
                        data.add(dataContainer);
                        websocketMessage.setData(data);
                        session.getBasicRemote().sendObject(websocketMessage);
                    }
                    break;
                case CREATED_ELEMENT:
                    if(data != null && !data.isEmpty()){
                        GlobalObject globalObject = data.get(0);
                        if(DatabaseManager.insertGlobalObject(globalObject))
                            WebsocketServerEndpoint.sendWebsocketMessageToOtherSessionMembers(websocketMessage, session);
                    }
                    break;
                case UPDATED_ELEMENT:
                    if(data != null && !data.isEmpty()){
                        GlobalObject globalObject = data.get(0);
                        if(DatabaseManager.updateGlobalObject(globalObject))
                            WebsocketServerEndpoint.sendWebsocketMessageToOtherSessionMembers(websocketMessage, session);
                    }
                    break;
                case DELETED_ELEMENT:
                    if(data != null && !data.isEmpty()){
                        GlobalObject globalObject = data.get(0);
                        if(DatabaseManager.deleteGlobalObject(globalObject))
                            WebsocketServerEndpoint.sendWebsocketMessageToOtherSessionMembers(websocketMessage, session);
                    }
                    break;
            }
        } catch(IOException | EncodeException ex) {
            ex.printStackTrace();
        }
    }
}
