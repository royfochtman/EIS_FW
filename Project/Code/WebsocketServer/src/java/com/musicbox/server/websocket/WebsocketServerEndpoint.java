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
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by David on 16.12.13.
 */
@ServerEndpoint(value="/websocketEndpoint", encoders = {WebsocketMessageEncoder.class}, decoders = {WebsocketMessageDecoder.class}, 
        configurator = WebsocketServerEndpointConfigurator.class)
public class WebsocketServerEndpoint{
    private MusicRoomSessionContainer musicRoomSessionContainer = new MusicRoomSessionContainer();

    public WebsocketServerEndpoint(){
        try {
            Context env = (Context)new InitialContext().lookup("java:comp/env");
            String connectionString = (String)env.lookup("databaseConnectionString");
            DatabaseManager.setConnection(connectionString);
        } catch(NamingException e) {
            e.printStackTrace();
        }
    }

    @OnOpen
    public void onOpen(Session session, EndpointConfig endpointConfig) {
        System.out.println("Server: Client connect: " + session.getId());

    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        musicRoomSessionContainer.removeMemberFromSession(session.getId());
    }

    @OnMessage
    public void onMessage(Session session, WebsocketMessage websocketMessage) {
        MusicRoomSession musicRoomSession = null;
        HashMap<String, Session> members = null;
        switch ( websocketMessage.getMessageType()){
            case NEW_MUSIC_ROOM:
                musicRoomSession = musicRoomSessionContainer.putMusicRoomSession(websocketMessage.getMusicRoomName(), session);
                MusicRoom musicRoom = new MusicRoom(1, musicRoomSession.getMusicRoomSessionName());
                ArrayList<GlobalObject> data = new ArrayList<>();
                data.add(musicRoom);
                if(DatabaseManager.insertMusicRoom(musicRoom))
                    session.getAsyncRemote().sendObject(new WebsocketMessage(musicRoom.getName(), WebsocketMessageType.NEW_MUSIC_ROOM, data));
                break;
            case JOIN_MUSIC_ROOM:
                musicRoomSession = musicRoomSessionContainer.putNewMemberInSession(websocketMessage.getMusicRoomName(), session);
                if(musicRoomSession != null){
                   // Sende kompletten MusicRoom an Client. Alle Daten die benötigt werden
                }
                break;
            case CHAT:

                break;
        }

    }

    @OnMessage
    public void onMessage(String websocketChatMessageString, Session session) {
       WebsocketChatMessage websocketChatMessage =  WebsocketChatMessage.fromString(websocketChatMessageString);
        if(websocketChatMessage != null) {
            HashMap<String, Session> members = musicRoomSessionContainer.getMusicRoomSessionMembers(websocketChatMessage.getMusicRoomName());
            if(members != null){
                for(Session s : members.values()){
                    if(s.isOpen())
                        s.getAsyncRemote().sendObject(websocketChatMessage);
                }
            }
        }
    }

    @OnError
    public void onError(Session session, Throwable t) {
        t.printStackTrace();

    }

}
