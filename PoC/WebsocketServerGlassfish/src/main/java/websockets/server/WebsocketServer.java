package main.java.websockets.server;


import javax.servlet.annotation.WebServlet;
import javax.websocket.*;
import javax.websocket.CloseReason;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;
/**
 * Created with IntelliJ IDEA.
 * User: David
 * Date: 16.11.13
 * Time: 13:38
 * To change this template use File | Settings | File Templates.
 */
@WebServlet("/WSServlet")
@ServerEndpoint(value = "/websocketTest", encoders = {MessageEncoder.class}, decoders = {MessageDecoder.class})
public class WebsocketServer {
    private Logger logger = Logger.getLogger(this.getClass().getName());
    private static Set<Session> sessions = Collections.synchronizedSet(new HashSet<Session>());
    private static HashMap<String, Room> rooms = new HashMap<>();

    @OnOpen
    public void onOpen(Session session)
    {
        logger.info("Connected ..." + session.getId());
        sessions.add(session);
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason)
    {
        logger.info(String.format("Session %s closed because of %s", session.getId(), closeReason));
        sessions.remove(session);
    }

    @OnMessage
    public void onMessage(RoomMessage message, Session client)
    {
        try
        {
            switch(message.getServerMessage())
            {
                case "quit":
                    client.close(new CloseReason(CloseReason.CloseCodes.NORMAL_CLOSURE, "Test ended"));
                    break;
                case "newRoom":
                    HashMap<String, Session> members = new HashMap<>();
                    members.put(client.getId(), client);
                    Room room = new Room(message.getRoom(), members);
                    rooms.put(message.getRoom(), room);
                    client.getBasicRemote().sendObject(new RoomMessage("", room.getName(), "requiredRoom"));
                    break;
                case "joinRoom":
                    if(!rooms.isEmpty())
                    {
                        Room r = rooms.get(message.getRoom());
                        if(r != null)
                        {
                            r.putMember(client);
                            client.getBasicRemote().sendObject(new RoomMessage("", r.getName(), "requiredRoom"));
                        }
                    }
                    else
                        client.getBasicRemote().sendObject(new RoomMessage("", null, "roomNotFound"));

                case "message":
                    Room ro = rooms.get(message.getRoom());
                    if(ro != null)
                    {
                        HashMap<String, Session> mem = ro.getMembers();
                        for(Session s : mem.values())
                        {
                            if(s.isOpen() && !s.getId().equals(client.getId()))
                                s.getBasicRemote().sendObject(message);
                        }
                    }
            }
        }
        catch(IOException | EncodeException ex)
        {
            throw new RuntimeException(ex);
        }
    }
}

