package com.musicbox.util.websocket;

import java.io.Serializable;

/**
 * Created by David on 19.12.13.
 */
public class WebsocketChatMessage {
    private String musicRoomName;
    private WebsocketMessageType messageType;
    private String chatMessage;

    public WebsocketChatMessage() {
        musicRoomName = "";
        messageType = WebsocketMessageType.CHAT;
        chatMessage = "";
    }

    public WebsocketChatMessage(String musicRoomName, String chatMessage) {
        messageType = WebsocketMessageType.CHAT;
        setMusicRoomName(musicRoomName);
        setChatMessage(chatMessage);
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

    public WebsocketMessageType getMessageType() {
        return messageType;
    }

    /*public void setMessageType(WebsocketMessageType messageType) {
        this.messageType = messageType;
    }*/

    public String getChatMessage() {
        return chatMessage;
    }

    public void setChatMessage(String chatMessage) {
        if(chatMessage == null)
            this.chatMessage = "";
        else
            this.chatMessage = chatMessage;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        WebsocketChatMessage websocketChatMessage = (WebsocketChatMessage) obj;

        return musicRoomName != null && musicRoomName.equals(websocketChatMessage.getMusicRoomName())
                && chatMessage != null && chatMessage.equals(websocketChatMessage.getChatMessage());
    }

    @Override
    public int hashCode() {
        int result = musicRoomName != null ? musicRoomName.hashCode() : 0;
        result = 31 * result + (messageType != null ? messageType.hashCode() : 0);
        result = 31 * result + (chatMessage != null ? chatMessage.hashCode() : 0);
        return result;
    }

    @Override
    public String toString(){
        return musicRoomName + "," + messageType.toString() + "," + chatMessage;
    }

    public static WebsocketChatMessage fromString(String websocketChatMessageString){
        if(websocketChatMessageString == null || websocketChatMessageString.isEmpty())
            return null;

        String[] data = websocketChatMessageString.split(",");
        WebsocketChatMessage message = new WebsocketChatMessage(data[0], data[2]);
        return message;
    }
}
