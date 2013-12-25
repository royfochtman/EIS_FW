package com.musicbox.util.websocket;

import java.util.ArrayList;

/**
 * Created by David on 17.12.13.
 */
public enum WebsocketMessageType {
    CHAT ("Chat"),
    NEW_MUSIC_ROOM("New MusicRoom"),
    JOIN_MUSIC_ROOM("Join MusicRoom"),
    CREATED_ELEMENT("Created Element"),
    UPDATED_ELEMENT("Updated Element"),
    ERROR("Error");

    private final String messageTypeString;

    private WebsocketMessageType(final String messageTypeString) {
        this.messageTypeString = messageTypeString;
    }

    public static ArrayList<String> getTypes() {
        ArrayList<String> list = new ArrayList<>();
        for(WebsocketMessageType messageType : WebsocketMessageType.values())
            list.add(messageType.toString());

        return list;
    }

    @Override
    public String toString() {
        return messageTypeString;
    }

    public static WebsocketMessageType fromString(String messageTypeString) {
        if(messageTypeString == null || messageTypeString.isEmpty())
            return null;

        for(WebsocketMessageType type : WebsocketMessageType.values()){
            if(messageTypeString.equals(type.toString()))
                return type;
        }

        return null;
    }

}
