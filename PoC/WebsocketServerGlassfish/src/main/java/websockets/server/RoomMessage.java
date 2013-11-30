package main.java.websockets.server;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: David
 * Date: 16.11.13
 * Time: 15:42
 * To change this template use File | Settings | File Templates.
 */
public class RoomMessage implements Serializable {
    private String message;
    private String room;
    private String serverMessage;

    public RoomMessage(String message, String room, String serverMessage)
    {
        this.message = message;
        this.room = room;
        this.serverMessage = serverMessage;
    }

    public String getMessage() { return this.message; }

    public String getRoom() { return this.room; }

    public String getServerMessage() { return this.serverMessage; }

    public void setMessage(String message) { this.message = message; }

    public void setRoom(String room) { this.room = room; }

    public void setServerMessage(String msg) { this.serverMessage = msg; }
}
