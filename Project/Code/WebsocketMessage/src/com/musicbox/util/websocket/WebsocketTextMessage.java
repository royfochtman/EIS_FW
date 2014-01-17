package com.musicbox.util.websocket;

import java.io.Serializable;

/**
 * Messages with Text-Data to send chat-messages or error-messages.
 * <br>The websocket-endpoint-server handle chat messages and forwards it to other logged in clients.
 * <br>The websocket-endpoint-server sends error messages to clients if an erro occurred.
 *
 * @author David Wachs
 */
public class WebsocketTextMessage implements Serializable {
    /**
     * The music-room-name of the user which sending the message. So that the server knows to which users
     * this message has to be forwarded.
     */
    private String musicRoomName;
    /**
     * The message-type is used by the server and the clients, so that they can decide what they have to do with a message
     * Can be type of CHAT or ERROR
     */
    private WebsocketTextMessageType textMessageType;
    /**
     * the chat- or error-message
     */
    private String textMessage;

    private String username;

    public WebsocketTextMessage() {
        musicRoomName = "";
        textMessageType = WebsocketTextMessageType.CHAT;
        textMessage = "";
        username = "";
    }

    public WebsocketTextMessage(String musicRoomName, WebsocketTextMessageType type, String textMessage, String username) {
        textMessageType = type;
        setMusicRoomName(musicRoomName);
        setTextMessage(textMessage);
        setUsername(username);
    }

    public WebsocketTextMessage(String musicRoomName, WebsocketTextMessageType type, String textMessage) {
        textMessageType = type;
        setMusicRoomName(musicRoomName);
        setTextMessage(textMessage);
        setUsername("");
    }

    public String getMusicRoomName() {
        return musicRoomName;
    }

    public void setMusicRoomName(String musicRoomName) {
        if(musicRoomName == null)
            this.musicRoomName = "";
        else
            this.musicRoomName = musicRoomName;

    }

    public WebsocketTextMessageType getTextMessageType() {
        return textMessageType;
    }

    public void setTextMessageType(WebsocketTextMessageType textMessageType) {
        this.textMessageType = textMessageType;
    }

    public String getTextMessage() {
        return textMessage;
    }

    public void setTextMessage(String textMessage) {
        if(textMessage == null)
            this.textMessage = "";
        else
            this.textMessage = textMessage;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        WebsocketTextMessage websocketTextMessage = (WebsocketTextMessage) obj;

        return musicRoomName != null && musicRoomName.equals(websocketTextMessage.getMusicRoomName())
                && textMessage != null && textMessage.equals(websocketTextMessage.getTextMessage())
                && username != null && username.equals(websocketTextMessage.getUsername());
    }

    @Override
    public int hashCode() {
        int result = musicRoomName != null ? musicRoomName.hashCode() : 0;
        result = 31 * result + (textMessageType != null ? textMessageType.hashCode() : 0);
        result = 31 * result + (textMessage != null ? textMessage.hashCode() : 0);
        result = 31 * result + (username != null ? username.hashCode() : 0);
        return result;
    }

    @Override
    public String toString(){
        return musicRoomName + "," + textMessageType.toString() + "," + textMessage + "," + username;
    }

    /**
     * Converts a String to a WebsocketTextMessage-Object.
     * @param textMessageString String which has to be converted. The String has to be the following format: musicRoomName,textMessageType,textMessage
     * @return WebsocketTextMessage-Object of the given string. Returns null if the String has a wrong format.
     */
    public static WebsocketTextMessage fromString(String textMessageString){
        if(textMessageString == null || textMessageString.isEmpty())
            return null;

        String[] data = textMessageString.split(",");
        WebsocketTextMessage message = null;
        if(data.length == 4)
              message = new WebsocketTextMessage(data[0], WebsocketTextMessageType.fromString(data[1]), data[2], data[3]);
        return message;
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
