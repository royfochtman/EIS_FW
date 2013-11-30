package main.java.websockets.client;

import main.java.websockets.server.*;

import java.awt.EventQueue;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.websocket.*;

/**
 * Created with IntelliJ IDEA.
 * User: David
 * Date: 14.11.13
 * Time: 12:28
 * To change this template use File | Settings | File Templates.
 */
@ClientEndpoint(encoders = {MessageEncoder.class}, decoders = {MessageDecoder.class})
public class WebsocketClient {
    private static Session session;
    private static String room = null;

    @OnOpen
    public void onOpen(Session session)
    {
        WebsocketClientGUI.txt_connectionMessages.insert("Client: Connected..." + session.getId(), 0);
        try {
            //session.getBasicRemote().sendText("start");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason)
    {
        WebsocketClientGUI.txt_connectionMessages.append("\nClient: Session" + session.getId() + " close because of" +  closeReason);
    }

    @OnMessage
    public void onMessage(RoomMessage message, Session session)
    {
        try {
            String msgRoom = message.getRoom();

            if(msgRoom != null && !msgRoom.equals(room))
                room = msgRoom;

            WebsocketClientGUI.txt_connectionMessages.append("\nCLient: Received..." + message.getServerMessage());
            WebsocketClientGUI.txt_message.append(message.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public static void openSession()
    {
        try
        {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            session = container.connectToServer(WebsocketClient.class, new URI("ws://localhost:8080/WSServlet/websocketTest"));
        }
        catch(DeploymentException | IOException | URISyntaxException e)
        {
            session = null;
        }
    }

    public static void sendMessage(String message)
    {
        try {
            RoomMessage msg = null;
            if(room != null)
            {
                msg = new RoomMessage(message, room, "message");
                session.getBasicRemote().sendObject(msg);
            }
            else
                getRoomMessage("newRoom");
        } catch (IOException | EncodeException e) {
            WebsocketClientGUI.txt_connectionMessages.append("\nCLient: Error...Could not send message!");
            e.printStackTrace();
        }
    }

    public static Session getSession()
    {
        return session;
    }

    public static void getRoomMessage(String serverMessage)
    {
        try {
            if(WebsocketClientGUI.txt_room.getText() != null && !WebsocketClientGUI.txt_room.getText().isEmpty())
            {
                //Room room = new Room(WebsocketClientGUI.txt_room.getText());
                RoomMessage msg = new RoomMessage("", WebsocketClientGUI.txt_room.getText(), serverMessage);
                session.getBasicRemote().sendObject(msg);
            }
        }
        catch (IOException | EncodeException ex)
        {
            WebsocketClientGUI.txt_message.append(ex.getMessage());
        }
    }

    public static void closeConnection()
    {
        try {
            RoomMessage msg = new RoomMessage("", null, "quit");
            session.getBasicRemote().sendObject(msg);
        } catch (IOException | EncodeException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    WebsocketClientGUI window = new WebsocketClientGUI();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
