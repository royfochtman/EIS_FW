package com.musicbox.server.websocket;


import javax.websocket.Session;
import java.util.HashMap;

/**
 * Created by David on 17.12.13.
 */
public class MusicRoomSession {
    private String musicRoomSessionName;
    private HashMap<String, Session> members;

    public MusicRoomSession(String musicRoomSessionName, HashMap<String, Session> members){
        setMusicRoomSessionName(musicRoomSessionName);
        setMembers(members);
    }

    public MusicRoomSession(String musicRoomSessionName){
        setMusicRoomSessionName(musicRoomSessionName);
        setMembers(null);
    }

    public MusicRoomSession(){};

    public String getMusicRoomSessionName() {
        return musicRoomSessionName;
    }

    public void setMusicRoomSessionName(String musicRoomSessionName) {
        if(musicRoomSessionName == null)
            this.musicRoomSessionName = "";
        else
            this.musicRoomSessionName = musicRoomSessionName;
    }

    public HashMap<String, Session> getMembers() {
        return members;
    }

    public Session getMember(String key) {
        if(!members.containsKey(key))
            return null;

        return members.get(key);
    }

    public void setMembers(HashMap<String, Session> members) {
        if(members == null)
            this.members = new HashMap<>();
        else
            this.members = members;
    }

    public boolean putMember(Session member) {
        if(member == null)
            return false;

        if(members.containsKey(member.getId()))
            return false;

        members.put(member.getId(), member);

        Session s = members.get(member.getId());
        return s != null && s.equals(member);
    }

    public boolean removeMember(String memberId) {
        if(memberId == null || memberId.isEmpty())
            return false;

        members.remove(memberId);
        return !members.containsKey(memberId);
    }

    public boolean updateMember(Session member) {
        if(member == null)
            return false;

        if(!members.containsKey(member.getId()))
            return false;

        members.put(member.getId(), member);
        Session s = members.get(member.getId());
        return s != null && s.equals(member);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MusicRoomSession musicSession = (MusicRoomSession) o;

        return musicRoomSessionName != null && musicSession.equals(musicSession.getMusicRoomSessionName())
                && members != null && members.equals(musicSession.members);
    }

    @Override
    public int hashCode() {
        int result = musicRoomSessionName != null ? musicRoomSessionName.hashCode() : 0;
        result = 31 * result + (members != null ? members.hashCode() : 0);
        return result;
    }
}
