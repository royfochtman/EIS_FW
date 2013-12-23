package com.musicbox.server.websocket;

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
@ServerEndpoint(value="/websocketEndpoint", encoders = {WebsocketMessageEncoder.class}, decoders = {WebsocketMessageDecoder.class}, configurator = WebsocketServerEndpointConfigurator.class)
public class WebsocketServerEndpoint{
    private MusicRoomSessionContainer musicRoomSessionContainer = new MusicRoomSessionContainer();

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
        //System.out.println("Server: Client connect: " + session.getId());
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        musicRoomSessionContainer.removeMemberFromSession(session.getId());
        String test = "";
    }

    @OnMessage
    public void onMessage(WebsocketMessage websocketMessage, Session session) {
        MusicRoomSession musicRoomSession = null;
        HashMap<String, Session> members = null;
        try{
            switch ( websocketMessage.getMessageType()){
                case NEW_MUSIC_ROOM:
                    musicRoomSessionContainer.putMusicRoomSession(websocketMessage.getMusicRoomName(), session);
                    musicRoomSession = musicRoomSessionContainer.getMusicRoomSession(websocketMessage.getMusicRoomName());
                    MusicRoom musicRoom = new MusicRoom(1, musicRoomSession.getMusicRoomSessionName());
                    ArrayList<GlobalObject> data = new ArrayList<>();
                    data.add(musicRoom);
                    if(DatabaseManager.insertMusicRoom(musicRoom))
                        session.getBasicRemote().sendObject(new WebsocketMessage(musicRoom.getName(), WebsocketMessageType.NEW_MUSIC_ROOM, data));
                    break;
                case JOIN_MUSIC_ROOM:
                    if(musicRoomSessionContainer.putNewMemberInSession(websocketMessage.getMusicRoomName(), session)) {
                        session.getBasicRemote().sendObject(new WebsocketMessage(websocketMessage.getMusicRoomName(), WebsocketMessageType.JOIN_MUSIC_ROOM, null));
                    }
                    break;
            }
        } catch(IOException | EncodeException ex) {
            ex.printStackTrace();
        }

    }

    @OnMessage
    public void onMessage(String websocketChatMessageString, Session session) {
        try{
            if(websocketChatMessageString != null && !websocketChatMessageString.isEmpty()) {
                WebsocketChatMessage websocketChatMessage = WebsocketChatMessage.fromString(websocketChatMessageString);
                HashMap<String, Session> members = musicRoomSessionContainer.getMusicRoomSessionMembers(websocketChatMessage.getMusicRoomName());
                if(members != null){
                    for(Session s : members.values()){
                        if(s.isOpen())
                            s.getBasicRemote().sendObject(websocketChatMessage);
                    }
                }
            }
        }catch(IOException | EncodeException ex) {
            ex.printStackTrace();
        }
    }

    @OnError
    public void onError(Session session, Throwable t) {
        t.printStackTrace();

    }

}
