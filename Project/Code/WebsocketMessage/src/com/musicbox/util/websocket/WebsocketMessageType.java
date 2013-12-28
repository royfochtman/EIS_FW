package com.musicbox.util.websocket;

import java.util.ArrayList;

/**
 * @author David Wachs
 */
public enum WebsocketMessageType {
    /**
     * new music-room created by a client
     */
    NEW_MUSIC_ROOM("New MusicRoom"),
    /**
     * a client wants to join a specific music-room
     */
    JOIN_MUSIC_ROOM("Join MusicRoom"),
    /**
     * a client created an element inside of a music-room
     */
    CREATED_ELEMENT("Created Element"),
    /**
     * a client updated data of an element inside of a music-room
     */
    UPDATED_ELEMENT("Updated Element"),
    /**
     * a client deleted an element inside of a music-room
     */
    DELETED_ELEMENT("Deleted Element");

    private final String messageTypeString;

    private WebsocketMessageType(final String messageTypeString) {
        this.messageTypeString = messageTypeString;
    }

    /**
     * Returns the String-values of all WebsocketMessageType-Enum-Constants
     * @return ArrayList of String-Values
     */
    public static ArrayList<String> getStringValues() {
        ArrayList<String> list = new ArrayList<>();
        for(WebsocketMessageType messageType : WebsocketMessageType.values())
            list.add(messageType.toString());

        return list;
    }

    @Override
    public String toString() {
        return messageTypeString;
    }

    /**
     * Returns the enum constant of this type with the specific String-value.
     * @param messageTypeStringValue the string-value of the enum constant to be returned
     * @return the enum constant with the specific String-value. Returns null if no constant-string-value matches with parameter
     */
    public static WebsocketMessageType fromString(String messageTypeStringValue) {
        if(messageTypeStringValue == null || messageTypeStringValue.isEmpty())
            return null;

        for(WebsocketMessageType type : WebsocketMessageType.values()){
            if(messageTypeStringValue.equals(type.toString()))
                return type;
        }

        return null;
    }

}
