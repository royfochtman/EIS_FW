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

    public MusicRoomSession putMusicRoomSession(String sessionName){
        if(sessionName == null || sessionName.isEmpty())
            return null;

        if(sessions.containsKey(sessionName))
            return null;

        return sessions.put(sessionName, new MusicRoomSession(sessionName, new HashMap<String, Session>()));
    }

    public MusicRoomSession putMusicRoomSession(String sessionName, Session founder){
        if(sessionName == null || sessionName.isEmpty() || founder == null)
            return null;

        if(sessions.containsKey(sessionName))
            return null;

        HashMap<String, Session> members = new HashMap<>();
        members.put(founder.getId(), founder);
        return sessions.put(sessionName, new MusicRoomSession(sessionName, members));
    }

    public MusicRoomSession getMusicRoomSession(String sessionName) {
        if(sessionName == null || sessionName.isEmpty())
            return null;

        if(!sessions.containsKey(sessionName))
            return null;

        return sessions.get(sessionName);
    }

    public HashMap<String, Session> getMusicRoomSessionMembers(String roomSessionName) {
        if(roomSessionName == null || roomSessionName.isEmpty())
            return null;

        if(sessions.containsKey(roomSessionName))
            return sessions.get(roomSessionName).getMembers();

        return null;
    }

    public MusicRoomSession putNewMemberInSession(String sessionName, Session member) {
        if(sessionName == null || sessionName.isEmpty() || member == null)
            return null;

        MusicRoomSession session = getMusicRoomSession(sessionName);
        if(session != null)
            return null;

        if(session.putMember(member) != null)
            return session;
        else
            return null;
    }

    public Session removeMemberFromSession(String sessionName, String memberId) {
        if(sessionName == null || sessionName.isEmpty() || memberId == null || memberId.isEmpty())
            return null;

        MusicRoomSession session = getMusicRoomSession(sessionName);
        if(session == null)
            return null;

        return session.removeMember(memberId);
    }

    public Session removeMemberFromSession(String memberId) {
        if(memberId == null || memberId.isEmpty())
            return null;

        if(sessions.isEmpty())
            return null;

        for(MusicRoomSession m : sessions.values()) {
            if(m != null && !m.getMembers().isEmpty()){
                for(Session s: m.getMembers().values()){
                    if(s != null && s.getId().equals(memberId))
                        return m.removeMember(memberId);
                }
            }
        }

        return null;
    }

    public MusicRoomSession removeMusicRoomSession(String sessionName) {
        if(sessionName == null || sessionName.isEmpty())
            return null;

        if(!sessions.containsKey(sessionName))
            return null;

        return sessions.remove(sessionName);
    }
}
