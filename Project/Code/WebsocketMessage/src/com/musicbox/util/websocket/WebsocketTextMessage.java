package com.musicbox.util.websocket;

/**
 * Created by David Wachs on 19.12.13.
 *
 * Container for MusicBox-Chat-Messages or Error-Messages from Server
 */
public class WebsocketTextMessage {
    private String musicRoomName;
    private WebsocketMessageType messageType;
    private String textMessage;

    public WebsocketTextMessage() {
        musicRoomName = "";
        messageType = WebsocketMessageType.CHAT;
        textMessage = "";
    }

    /**
     * Constructor
     * @param musicRoomName
     * @param type has to be CHAT for Chat-Message or ERROR for Error-Message
     * @param textMessage
     */
    public WebsocketTextMessage(String musicRoomName, WebsocketMessageType type, String textMessage) {
        messageType = type;
        setMusicRoomName(musicRoomName);
        setTextMessage(textMessage);
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

    public void setMessageType(WebsocketMessageType messageType) {
        this.messageType = messageType;
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
                && textMessage != null && textMessage.equals(websocketTextMessage.getTextMessage());
    }

    @Override
    public int hashCode() {
        int result = musicRoomName != null ? musicRoomName.hashCode() : 0;
        result = 31 * result + (messageType != null ? messageType.hashCode() : 0);
        result = 31 * result + (textMessage != null ? textMessage.hashCode() : 0);
        return result;
    }

    @Override
    public String toString(){
        return musicRoomName + "," + messageType.toString() + "," + textMessage;
    }

    public static WebsocketTextMessage fromString(String textMessageString){
        if(textMessageString == null || textMessageString.isEmpty())
            return null;

        String[] data = textMessageString.split(",");
        WebsocketTextMessage message = null;
        if(data.length == 3)
              message = new WebsocketTextMessage(data[0], WebsocketMessageType.fromString(data[1]), data[2]);
        return message;
    }
}
