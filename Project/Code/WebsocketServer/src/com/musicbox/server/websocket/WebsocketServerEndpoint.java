package com.musicbox.server.websocket;

import com.musicbox.util.MusicRoomDataContainer;
import com.musicbox.util.database.DatabaseManager;
import com.musicbox.util.database.entities.MusicRoom;
import com.musicbox.util.globalobject.GlobalObject;
import com.musicbox.util.websocket.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by David on 16.12.13.
 */
@ServerEndpoint(value="/websocketEndpoint", encoders = {WebsocketMessageEncoder.class, WebsocketTextMessageEncoder.class},
        decoders = {WebsocketMessageDecoder.class, WebsocketTextMessageDecoder.class}, configurator = WebsocketServerEndpointConfigurator.class)
public class WebsocketServerEndpoint{
    private MusicRoomSessionContainer musicRoomSessionContainer = new MusicRoomSessionContainer();

    private final String ERROR_CREATE_ROOM = "An error occurred while creating the music room. Data could not be saved into Database!";

    public WebsocketServerEndpoint(){
        try {
            Context env = (Context)new InitialContext().lookup("java:comp/env");
            String connectionString = (String)env.lookup("databaseConnectionString");
            System.out.println(connectionString);
            if(DatabaseManager.setConnection(connectionString)) {
                ArrayList<MusicRoom> musicRooms = DatabaseManager.getMusicRooms();
                if(musicRooms != null && !musicRooms.isEmpty()) {
                    for(MusicRoom m : musicRooms) {
                        musicRoomSessionContainer.putMusicRoomSession(m.getName());
                    }
                }
            }
        } catch(NamingException e) {
            e.printStackTrace();
        }
    }

    @OnOpen
    public void onOpen(Session session, EndpointConfig endpointConfig) {
        //logger???
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        musicRoomSessionContainer.removeMemberFromSession(session.getId());
        //logger???
    }

    @OnMessage
    public void onMessage(WebsocketMessage websocketMessage, Session session){
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
                        session.getBasicRemote().sendObject(new WebsocketTextMessage(musicRoom.getName(), WebsocketMessageType.ERROR, ERROR_CREATE_ROOM));
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
                            sendWebsocketMessageToOtherSessionMembers(websocketMessage, session);
                    }
                    break;
                case UPDATED_ELEMENT:
                    if(data != null && !data.isEmpty()){
                        GlobalObject globalObject = data.get(0);
                        if(DatabaseManager.updateGlobalObject(globalObject))
                            sendWebsocketMessageToOtherSessionMembers(websocketMessage, session);
                    }
                    break;
                case DELETED_ELEMENT:
                    if(data != null && !data.isEmpty()){
                        GlobalObject globalObject = data.get(0);
                        if(DatabaseManager.deleteGlobalObject(globalObject))
                            sendWebsocketMessageToOtherSessionMembers(websocketMessage, session);
                    }
                    break;
            }
        } catch(IOException | EncodeException ex) {
            ex.printStackTrace();
        }

    }

    @OnMessage
    public void onMessage(WebsocketTextMessage websocketTextMessage, Session session) {
        try{
            switch (websocketTextMessage.getMessageType()) {
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

    @OnError
    public void onError(Session session, Throwable t) {
        t.printStackTrace();
        //logger??
    }

    /**
     * Sends a WebsocketMessage-Object to all session members except the transmitter session
     * @param websocketMessage
     * @param session transmitter session
     * @throws IOException
     * @throws EncodeException
     */
    private void sendWebsocketMessageToOtherSessionMembers(WebsocketMessage websocketMessage, Session session) throws IOException, EncodeException {
        HashMap<String, Session> members = musicRoomSessionContainer.getMusicRoomSessionMembers(websocketMessage.getMusicRoomName());
        if(members != null){
            for(Session s : members.values()){
                if(s.isOpen() && !s.getId().equals(session.getId()))
                    s.getBasicRemote().sendObject(websocketMessage);
            }
        }
    }

}
