package com.musicbox.util.websocket;

import java.util.ArrayList;

/**
 * @author David Wachs
 */
public enum WebsocketTextMessageType {
    /**
     * chat-message of a client
     */
    CHAT ("Chat"),
    /**
     * erro-message from the websocket-endpoint-server to a client
     */
    ERROR("Error");

    private final String textMessageTypeString;

    private WebsocketTextMessageType(final String WebsocketTextMessageType) {
        this.textMessageTypeString = WebsocketTextMessageType;
    }

    /**
     * Returns the String-Values if all WebsocketTextMessageType-Enum-Constants
     * @return ArrayList of String-Values
     */
    public static ArrayList<String> getStringValues() {
        ArrayList<String> list = new ArrayList<>();
        for(WebsocketTextMessageType textMessageType : WebsocketTextMessageType.values())
            list.add(textMessageType.toString());

        return list;
    }

    @Override
    public String toString() {
        return textMessageTypeString;
    }

    /**
     * Returns the enum constant of this type with the specific String-value.
     * @param textMessageTypeString the string-value of the enum constant to be returned
     * @return the enum constant with the specific String-value. Returns null if no constant-string-value matches with parameter
     */
    public static WebsocketTextMessageType fromString(String textMessageTypeString) {
        if(textMessageTypeString == null || textMessageTypeString.isEmpty())
            return null;

        for(WebsocketTextMessageType type : WebsocketTextMessageType.values()){
            if(textMessageTypeString.equals(type.toString()))
                return type;
        }

        return null;
    }
}
