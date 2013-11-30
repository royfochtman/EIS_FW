package main.java.websockets.server;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: David
 * Date: 16.11.13
 * Time: 15:26
 * To change this template use File | Settings | File Templates.
 */
public enum RoomStatus implements Serializable{
    NEW, EXIST, UNDEF;
}
