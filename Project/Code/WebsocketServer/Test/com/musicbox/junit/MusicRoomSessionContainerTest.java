package com.musicbox.junit;

import com.musicbox.server.websocket.MusicRoomSessionContainer;
import org.junit.Before;
import org.junit.Test;

import javax.websocket.*;

import java.io.IOException;
import java.net.URI;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by David on 23.12.13.
 */
public class MusicRoomSessionContainerTest {
    MusicRoomSessionContainer musicRoomSessionContainer;
    Session session1;
    Session session2;
    Session session3;
    Session session4;
    @Before
    public void setUp() throws Exception {
        musicRoomSessionContainer = new MusicRoomSessionContainer();
        session1 = new Session() {
            @Override
            public WebSocketContainer getContainer() {
                return null;
            }

            @Override
            public void addMessageHandler(MessageHandler messageHandler) throws IllegalStateException {

            }

            @Override
            public Set<MessageHandler> getMessageHandlers() {
                return null;
            }

            @Override
            public void removeMessageHandler(MessageHandler messageHandler) {

            }

            @Override
            public String getProtocolVersion() {
                return null;
            }

            @Override
            public String getNegotiatedSubprotocol() {
                return null;
            }

            @Override
            public List<Extension> getNegotiatedExtensions() {
                return null;
            }

            @Override
            public boolean isSecure() {
                return false;
            }

            @Override
            public boolean isOpen() {
                return false;
            }

            @Override
            public long getMaxIdleTimeout() {
                return 0;
            }

            @Override
            public void setMaxIdleTimeout(long l) {

            }

            @Override
            public void setMaxBinaryMessageBufferSize(int i) {

            }

            @Override
            public int getMaxBinaryMessageBufferSize() {
                return 0;
            }

            @Override
            public void setMaxTextMessageBufferSize(int i) {

            }

            @Override
            public int getMaxTextMessageBufferSize() {
                return 0;
            }

            @Override
            public RemoteEndpoint.Async getAsyncRemote() {
                return null;
            }

            @Override
            public RemoteEndpoint.Basic getBasicRemote() {
                return null;
            }

            @Override
            public String getId() {
                return "Founder1";
            }

            @Override
            public void close() throws IOException {

            }

            @Override
            public void close(CloseReason closeReason) throws IOException {

            }

            @Override
            public URI getRequestURI() {
                return null;
            }

            @Override
            public Map<String, List<String>> getRequestParameterMap() {
                return null;
            }

            @Override
            public String getQueryString() {
                return null;
            }

            @Override
            public Map<String, String> getPathParameters() {
                return null;
            }

            @Override
            public Map<String, Object> getUserProperties() {
                return null;
            }

            @Override
            public Principal getUserPrincipal() {
                return null;
            }

            @Override
            public Set<Session> getOpenSessions() {
                return null;
            }
        };
        session2 = new Session() {
            @Override
            public WebSocketContainer getContainer() {
                return null;
            }

            @Override
            public void addMessageHandler(MessageHandler messageHandler) throws IllegalStateException {

            }

            @Override
            public Set<MessageHandler> getMessageHandlers() {
                return null;
            }

            @Override
            public void removeMessageHandler(MessageHandler messageHandler) {

            }

            @Override
            public String getProtocolVersion() {
                return null;
            }

            @Override
            public String getNegotiatedSubprotocol() {
                return null;
            }

            @Override
            public List<Extension> getNegotiatedExtensions() {
                return null;
            }

            @Override
            public boolean isSecure() {
                return false;
            }

            @Override
            public boolean isOpen() {
                return false;
            }

            @Override
            public long getMaxIdleTimeout() {
                return 0;
            }

            @Override
            public void setMaxIdleTimeout(long l) {

            }

            @Override
            public void setMaxBinaryMessageBufferSize(int i) {

            }

            @Override
            public int getMaxBinaryMessageBufferSize() {
                return 10;
            }

            @Override
            public void setMaxTextMessageBufferSize(int i) {

            }

            @Override
            public int getMaxTextMessageBufferSize() {
                return 0;
            }

            @Override
            public RemoteEndpoint.Async getAsyncRemote() {
                return null;
            }

            @Override
            public RemoteEndpoint.Basic getBasicRemote() {
                return null;
            }

            @Override
            public String getId() {
                return "Founder2";
            }

            @Override
            public void close() throws IOException {

            }

            @Override
            public void close(CloseReason closeReason) throws IOException {

            }

            @Override
            public URI getRequestURI() {
                return null;
            }

            @Override
            public Map<String, List<String>> getRequestParameterMap() {
                return null;
            }

            @Override
            public String getQueryString() {
                return null;
            }

            @Override
            public Map<String, String> getPathParameters() {
                return null;
            }

            @Override
            public Map<String, Object> getUserProperties() {
                return null;
            }

            @Override
            public Principal getUserPrincipal() {
                return null;
            }

            @Override
            public Set<Session> getOpenSessions() {
                return null;
            }
        };
        session3 = new Session() {
            @Override
            public WebSocketContainer getContainer() {
                return null;
            }

            @Override
            public void addMessageHandler(MessageHandler messageHandler) throws IllegalStateException {

            }

            @Override
            public Set<MessageHandler> getMessageHandlers() {
                return null;
            }

            @Override
            public void removeMessageHandler(MessageHandler messageHandler) {

            }

            @Override
            public String getProtocolVersion() {
                return null;
            }

            @Override
            public String getNegotiatedSubprotocol() {
                return null;
            }

            @Override
            public List<Extension> getNegotiatedExtensions() {
                return null;
            }

            @Override
            public boolean isSecure() {
                return false;
            }

            @Override
            public boolean isOpen() {
                return false;
            }

            @Override
            public long getMaxIdleTimeout() {
                return 0;
            }

            @Override
            public void setMaxIdleTimeout(long l) {

            }

            @Override
            public void setMaxBinaryMessageBufferSize(int i) {

            }

            @Override
            public int getMaxBinaryMessageBufferSize() {
                return 0;
            }

            @Override
            public void setMaxTextMessageBufferSize(int i) {

            }

            @Override
            public int getMaxTextMessageBufferSize() {
                return 0;
            }

            @Override
            public RemoteEndpoint.Async getAsyncRemote() {
                return null;
            }

            @Override
            public RemoteEndpoint.Basic getBasicRemote() {
                return null;
            }

            @Override
            public String getId() {
                return "Founder3";
            }

            @Override
            public void close() throws IOException {

            }

            @Override
            public void close(CloseReason closeReason) throws IOException {

            }

            @Override
            public URI getRequestURI() {
                return null;
            }

            @Override
            public Map<String, List<String>> getRequestParameterMap() {
                return null;
            }

            @Override
            public String getQueryString() {
                return null;
            }

            @Override
            public Map<String, String> getPathParameters() {
                return null;
            }

            @Override
            public Map<String, Object> getUserProperties() {
                return null;
            }

            @Override
            public Principal getUserPrincipal() {
                return null;
            }

            @Override
            public Set<Session> getOpenSessions() {
                return null;
            }
        };
        session4 = new Session() {
            @Override
            public WebSocketContainer getContainer() {
                return null;
            }

            @Override
            public void addMessageHandler(MessageHandler messageHandler) throws IllegalStateException {

            }

            @Override
            public Set<MessageHandler> getMessageHandlers() {
                return null;
            }

            @Override
            public void removeMessageHandler(MessageHandler messageHandler) {

            }

            @Override
            public String getProtocolVersion() {
                return null;
            }

            @Override
            public String getNegotiatedSubprotocol() {
                return null;
            }

            @Override
            public List<Extension> getNegotiatedExtensions() {
                return null;
            }

            @Override
            public boolean isSecure() {
                return false;
            }

            @Override
            public boolean isOpen() {
                return false;
            }

            @Override
            public long getMaxIdleTimeout() {
                return 0;
            }

            @Override
            public void setMaxIdleTimeout(long l) {

            }

            @Override
            public void setMaxBinaryMessageBufferSize(int i) {

            }

            @Override
            public int getMaxBinaryMessageBufferSize() {
                return 0;
            }

            @Override
            public void setMaxTextMessageBufferSize(int i) {

            }

            @Override
            public int getMaxTextMessageBufferSize() {
                return 0;
            }

            @Override
            public RemoteEndpoint.Async getAsyncRemote() {
                return null;
            }

            @Override
            public RemoteEndpoint.Basic getBasicRemote() {
                return null;
            }

            @Override
            public String getId() {
                return "Founder4";
            }

            @Override
            public void close() throws IOException {

            }

            @Override
            public void close(CloseReason closeReason) throws IOException {

            }

            @Override
            public URI getRequestURI() {
                return null;
            }

            @Override
            public Map<String, List<String>> getRequestParameterMap() {
                return null;
            }

            @Override
            public String getQueryString() {
                return null;
            }

            @Override
            public Map<String, String> getPathParameters() {
                return null;
            }

            @Override
            public Map<String, Object> getUserProperties() {
                return null;
            }

            @Override
            public Principal getUserPrincipal() {
                return null;
            }

            @Override
            public Set<Session> getOpenSessions() {
                return null;
            }
        };

    }

    @Test
    public void testPutMusicRoomSession() throws Exception {
        assertTrue(musicRoomSessionContainer.putMusicRoomSession("Session1"));
        assertFalse(musicRoomSessionContainer.putMusicRoomSession(""));
        assertFalse(musicRoomSessionContainer.putMusicRoomSession(null));
    }

    @Test
    public void testPutMusicRoomSession1() throws Exception {
        assertTrue(musicRoomSessionContainer.putMusicRoomSession("Session1", session1));
        assertTrue(musicRoomSessionContainer.putMusicRoomSession("Session2", session2));
        assertFalse(musicRoomSessionContainer.putMusicRoomSession("Session1", session2));
        assertFalse(musicRoomSessionContainer.putMusicRoomSession("", null));
        assertFalse(musicRoomSessionContainer.putMusicRoomSession("Session3", null));
    }

    @Test
    public void testGetMusicRoomSession() throws Exception {
        musicRoomSessionContainer.putMusicRoomSession("Session1");
        musicRoomSessionContainer.putMusicRoomSession("Session2", session1);
        assertNotNull(musicRoomSessionContainer.getMusicRoomSession("Session1"));
        assertNotNull(musicRoomSessionContainer.getMusicRoomSession("Session2"));
        assertNull(musicRoomSessionContainer.getMusicRoomSession("Session3"));
        assertNull(musicRoomSessionContainer.getMusicRoomSession(""));
        assertNull(musicRoomSessionContainer.getMusicRoomSession(null));
    }

    @Test
    public void testGetMusicRoomSessionMembers() throws Exception {
        musicRoomSessionContainer.putMusicRoomSession("Session1", session1);
        musicRoomSessionContainer.putMusicRoomSession("Session2", session2);
        assertNotNull(musicRoomSessionContainer.getMusicRoomSessionMembers("Session1"));
        assertNotNull(musicRoomSessionContainer.getMusicRoomSessionMembers("Session2"));
        assertNull(musicRoomSessionContainer.getMusicRoomSessionMembers("Session4"));
        assertNull(musicRoomSessionContainer.getMusicRoomSessionMembers(""));
        assertNull(musicRoomSessionContainer.getMusicRoomSessionMembers(null));
    }

    @Test
    public void testPutNewMemberInSession() throws Exception {
        musicRoomSessionContainer.putMusicRoomSession("Session1");
        musicRoomSessionContainer.putMusicRoomSession("Session2");
        assertTrue(musicRoomSessionContainer.putNewMemberInSession("Session1", session3));
        assertTrue(musicRoomSessionContainer.putNewMemberInSession("Session2", session4));
        assertFalse(musicRoomSessionContainer.putNewMemberInSession("Session10", session3));
        assertFalse(musicRoomSessionContainer.putNewMemberInSession("Session1", null));
        assertFalse(musicRoomSessionContainer.putNewMemberInSession("", session2));
        assertFalse(musicRoomSessionContainer.putNewMemberInSession(null, session2));
    }

    @Test
    public void testRemoveMemberFromSession() throws Exception {
        musicRoomSessionContainer.putMusicRoomSession("Session1", session3);
        assertTrue(musicRoomSessionContainer.removeMemberFromSession("Session1", "Founder3"));
        assertTrue(musicRoomSessionContainer.removeMemberFromSession("Session1", "Founder3"));
        assertTrue(musicRoomSessionContainer.removeMemberFromSession("Session1", "Founder30"));
        assertFalse(musicRoomSessionContainer.removeMemberFromSession("Session2", "Founder2"));
        assertFalse(musicRoomSessionContainer.removeMemberFromSession("", "Founder2"));
        assertFalse(musicRoomSessionContainer.removeMemberFromSession("Session2", ""));
        assertFalse(musicRoomSessionContainer.removeMemberFromSession(null, "Founder2"));
        assertFalse(musicRoomSessionContainer.removeMemberFromSession("Session2", null));
    }

    @Test
    public void testRemoveMemberFromSession1() throws Exception {
        musicRoomSessionContainer.putMusicRoomSession("Session1", session1);
        musicRoomSessionContainer.putMusicRoomSession("Session2", session4);
        assertTrue(musicRoomSessionContainer.removeMemberFromSession("Founder4"));
        assertTrue(musicRoomSessionContainer.removeMemberFromSession("Founder1"));
        assertTrue(musicRoomSessionContainer.removeMemberFromSession("Founder10"));
        assertFalse(musicRoomSessionContainer.removeMemberFromSession(""));
        assertFalse(musicRoomSessionContainer.removeMemberFromSession(null));
    }

    @Test
    public void testRemoveMusicRoomSession() throws Exception {
        musicRoomSessionContainer.putMusicRoomSession("Session1");
        musicRoomSessionContainer.putMusicRoomSession("Session2");
        assertTrue(musicRoomSessionContainer.removeMusicRoomSession("Session1"));
        assertTrue(musicRoomSessionContainer.removeMusicRoomSession("Session1"));
        assertTrue(musicRoomSessionContainer.removeMusicRoomSession("Session2"));
        assertTrue(musicRoomSessionContainer.removeMusicRoomSession("Session10"));
        assertFalse(musicRoomSessionContainer.removeMusicRoomSession(""));
        assertFalse(musicRoomSessionContainer.removeMusicRoomSession(null));
    }

    @Test
    public void testEquals() throws Exception {

    }

    @Test
    public void testHashCode() throws Exception {

    }
}
