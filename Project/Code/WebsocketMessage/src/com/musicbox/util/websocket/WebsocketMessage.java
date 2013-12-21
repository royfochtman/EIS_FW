package com.musicbox.util.websocket;

import com.musicbox.util.globalobject.GlobalObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by David Wachs on 17.12.13.
 */
public class WebsocketMessage implements Serializable {
    private String musicRoomName;
    private WebsocketMessageType messageType;
    private ArrayList<GlobalObject> data;

    public WebsocketMessage(final String musicRoomName, WebsocketMessageType messageType, ArrayList<GlobalObject> data) {
        this.musicRoomName = musicRoomName;
        this.messageType = messageType;
        this.data = data;
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
                && data != null && data.equals(websocketMessage.getData());
    }

    @Override
    public int hashCode() {
        int result = musicRoomName != null ? musicRoomName.hashCode() : 0;
        result = 31 * result + (messageType != null ? messageType.hashCode() : 0);
        result = 31 * result + (data != null ? data.hashCode() : 0);
        return result;
    }
}
