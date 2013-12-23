package com.musicbox.server.websocket;

import javax.websocket.Session;
import java.util.HashMap;

/**
 * Created by David on 17.12.13.
 */
public class MusicRoomSessionContainer {
    private HashMap<String, MusicRoomSession> sessions;

    public MusicRoomSessionContainer(){
        sessions = new HashMap<>();
    }

    public boolean putMusicRoomSession(String sessionName){
        if(sessionName == null || sessionName.isEmpty())
            return false;

        if(sessions.containsKey(sessionName))
            return false;

        MusicRoomSession session = new MusicRoomSession(sessionName);

        sessions.put(sessionName, session);
        MusicRoomSession m = sessions.get(sessionName);
        return m != null && m.equals(session);
    }

    public boolean putMusicRoomSession(String sessionName, Session founder){
        if(sessionName == null || sessionName.isEmpty() || founder == null)
            return false;

        if(sessions.containsKey(sessionName))
            return false;

        HashMap<String, Session> members = new HashMap<>();
        members.put(founder.getId(), founder);
        MusicRoomSession session = new MusicRoomSession(sessionName, members);
        sessions.put(sessionName, session);
        MusicRoomSession m = sessions.get(sessionName);
        return m != null && m.equals(session);
    }

    public MusicRoomSession getMusicRoomSession(String sessionName) {
        if(sessionName == null || sessionName.isEmpty())
            return null;

        if(!sessions.containsKey(sessionName))
            return null;

        return sessions.get(sessionName);
    }

    public HashMap<String, Session> getMusicRoomSessionMembers(String sessionName) {
        if(sessionName == null || sessionName.isEmpty())
            return null;

        MusicRoomSession musicRoomSession = sessions.get(sessionName);
        if(musicRoomSession == null)
            return null;

        return sessions.get(sessionName).getMembers();
    }

    public boolean putNewMemberInSession(String sessionName, Session member) {
        if(sessionName == null || sessionName.isEmpty() || member == null)
            return false;

        MusicRoomSession session = getMusicRoomSession(sessionName);
        if(session == null)
            return false;

        return session.putMember(member);
    }

    public boolean removeMemberFromSession(String sessionName, String memberId) {
        if(sessionName == null || sessionName.isEmpty() || memberId == null || memberId.isEmpty())
            return false;

        if(sessions.isEmpty())
            return true;

        MusicRoomSession session = getMusicRoomSession(sessionName);
        if(session == null)
            return false;

        return session.removeMember(memberId);
    }

    public boolean removeMemberFromSession(String memberId) {
        if(memberId == null || memberId.isEmpty())
            return false;

        if(sessions.isEmpty())
            return true;

        for(MusicRoomSession m : sessions.values()) {
            if(m != null && !m.getMembers().isEmpty()){
                for(Session s : m.getMembers().values()){
                    if(s != null && s.getId().equals(memberId))
                        return m.removeMember(memberId);
                }
            }
        }
        return true;
    }

    public boolean removeMusicRoomSession(String sessionName) {
        if(sessionName == null || sessionName.isEmpty())
            return false;

        sessions.remove(sessionName);
        return !sessions.containsKey(sessionName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MusicRoomSessionContainer that = (MusicRoomSessionContainer) o;

        if (sessions != null ? !sessions.equals(that.sessions) : that.sessions != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return sessions != null ? sessions.hashCode() : 0;
    }
}
