package main.java.websockets.server;

import javax.websocket.Session;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created with IntelliJ IDEA.
 * User: David
 * Date: 16.11.13
 * Time: 15:18
 * To change this template use File | Settings | File Templates.
 */

public class Room implements Serializable{
    private String name;
    //private RoomStatus status;
    //private HashSet<Session> members;
    private HashMap<String, Session> members;

    //Constructor
    public Room(String name, HashMap<String, Session> members)
    {
        this.name = name;
        //this.status = status;
        this.members = members;
    }

    public Room(String name)
    {
        this.name = name;
        //this.status = RoomStatus.UNDEF;
        this.members = null;
    }

    public String getName() { return this.name; }

    /*public RoomStatus getStatus() { return this.status; }  */

    public HashMap<String, Session> getMembers() { return this.members; }

    public Boolean putMember(Session member)
    {
        if(member == null)
            return false;

        this.members.put(member.getId(), member);
        return true;
    }

    public Boolean removeMember(Session member)
    {
        if(member == null)
            return false;

        if(this.members.containsValue(member))
            this.members.remove(member);
        return true;
    }

    @Override
    public boolean equals(Object room)
    {
        if(room == null)
            return false;

        if(room instanceof Room)
        {
            Room r = (Room)room;
            if(this.name.equals(r.getName()) && this.members.equals(r.getMembers()))
                return true;
        }
        return false;
    }
}
