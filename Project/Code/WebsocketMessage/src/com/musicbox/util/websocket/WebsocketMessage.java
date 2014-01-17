package com.musicbox.util.websocket;

import com.musicbox.util.globalobject.GlobalObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Messages with GlobalObject-Data to send informations about e.g. new Tracks or new MusicSegments from
 * client to server.
 * <br> The websocket-endpoint-server handle this messages and forwards it to other logged in clients.
 *
 * @author David Wachs
 */
public class WebsocketMessage implements Serializable {
    /**
     * The music-room-name of the user which sending the message. So that the server knows to which users
     * this message has to be forwarded.
     */
    private String musicRoomName;
    /**
     * The message-type is used by the server and the clients, so that they can decide what they have to do with a message
     */
    private WebsocketMessageType messageType;
    /**
     * the message-data
     */
    private ArrayList<GlobalObject> data;

    private String username;

    /**
     * Constructor
     * @param musicRoomName name of users music-room
     * @param messageType type of the message, e.g. WebsocketMessageType.JOIN_MUSIC_ROOM to join music room with the given name in parameter musicRoomName
     * @param data data to send of type GlobalObject. Could be MusicRoom-, MusicSegment-, Track-, Variation-, VariationTrack- or MusicRoomDataContainer-instances
     */
    public WebsocketMessage(final String musicRoomName, WebsocketMessageType messageType, ArrayList<GlobalObject> data, String username) {
        this.musicRoomName = musicRoomName;
        this.messageType = messageType;
        this.data = data;
        this.username = username;
    }

    public WebsocketMessage(final String musicRoomName, WebsocketMessageType messageType, ArrayList<GlobalObject> data) {
        this.musicRoomName = musicRoomName;
        this.messageType = messageType;
        this.data = data;
        this.username = "";
    }

    public String getMusicRoomName() {
        return musicRoomName;
    }

    public void setMusicRoomName(final String musicRoomName) {
        this.musicRoomName = musicRoomName;
    }

    public WebsocketMessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(WebsocketMessageType messageType) {
        this.messageType = messageType;
    }

    public ArrayList<GlobalObject> getData() {
        return data;
    }

    public void setData(ArrayList<GlobalObject> data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WebsocketMessage websocketMessage = (WebsocketMessage) o;

        return musicRoomName != null && musicRoomName.equals(websocketMessage.getMusicRoomName())
                && messageType != null && messageType.equals(websocketMessage.getMessageType())
                && data != null && data.equals(websocketMessage.getData())
                && username != null && username.equals(websocketMessage.getData());
    }

    @Override
    public int hashCode() {
        int result = musicRoomName != null ? musicRoomName.hashCode() : 0;
        result = 31 * result + (messageType != null ? messageType.hashCode() : 0);
        result = 31 * result + (data != null ? data.hashCode() : 0);
        result = 31 * result + (username != null ? username.hashCode() : 0);
        return result;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        if(username == null)
            this.username = "";
        else
            this.username = username;
    }
}
