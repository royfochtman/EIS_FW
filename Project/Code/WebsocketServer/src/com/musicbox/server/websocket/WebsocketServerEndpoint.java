package com.musicbox.server.websocket;

import com.musicbox.util.database.DatabaseManager;
import com.musicbox.util.database.entities.MusicRoom;
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
 * Created by David Wachs on 16.12.13.
 * Websocket-Server-Endpoint. Handles Websocket-Messages from Clients.
 */
@ServerEndpoint(value="/websocketEndpoint", encoders = {WebsocketMessageEncoder.class, WebsocketTextMessageEncoder.class},
        decoders = {WebsocketMessageDecoder.class, WebsocketTextMessageDecoder.class}, configurator = WebsocketServerEndpointConfigurator.class)
public class WebsocketServerEndpoint{
    private static MusicRoomSessionContainer musicRoomSessionContainer = new MusicRoomSessionContainer();

    protected static final String ERROR_CREATE_ROOM = "An error occurred while creating the music room. Data could not be saved into Database!";

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
        new WebsocketMessageHandlingThread(musicRoomSessionContainer, websocketMessage, session).run();
    }

    @OnMessage
    public void onMessage(WebsocketTextMessage websocketTextMessage, Session session) {
        new WebsocketTextMessageHandlingThread(musicRoomSessionContainer, websocketTextMessage).run();
    }

    @OnError
    public void onError(Session session, Throwable t) {
        t.printStackTrace();
        //logger??
    }

    /**
     * Sends WebsocketMessage-Object to all session members except the transmitter session
     * @param websocketMessage
     * @param session transmitter session
     * @throws IOException
     * @throws EncodeException
     */
    protected static void sendWebsocketMessageToOtherSessionMembers(WebsocketMessage websocketMessage, Session session) throws IOException, EncodeException {
        HashMap<String, Session> members = musicRoomSessionContainer.getMusicRoomSessionMembers(websocketMessage.getMusicRoomName());
        if(members != null){
            for(Session s : members.values()){
                if(s.isOpen() && !s.getId().equals(session.getId()))
                    s.getBasicRemote().sendObject(websocketMessage);
            }
        }
    }

}
