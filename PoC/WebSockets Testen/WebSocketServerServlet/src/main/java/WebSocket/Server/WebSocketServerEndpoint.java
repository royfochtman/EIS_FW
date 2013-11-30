package main.java.WebSocket.Server;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.annotation.WebServlet;
import javax.websocket.CloseReason;
import javax.websocket.CloseReason.CloseCodes;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@WebServlet("/WebSocketServerServlet")
@ServerEndpoint(value = "/websocketTest")
public class WebSocketServerEndpoint{

	private Logger logger = Logger.getLogger(this.getClass().getName());
	
	@OnOpen
	public void onOpen(Session session)
	{
		logger.info("Connected ..." + session.getId());
	}
	
	@OnClose
	public void onClose(Session session, CloseReason closeReason)
	{
		logger.info(String.format("Session %s closed because of %s", session.getId(), closeReason));
	}
	
	@OnMessage
	public void onMessage(String message, Session session)
	{
		try
		{
			if(message.equals("quit"))
				 session.close(new CloseReason(CloseCodes.NORMAL_CLOSURE, "Test ended"));
			
			for(Session s : session.getOpenSessions())
			{
				if(s.isOpen() && s.getId() != session.getId())
					s.getBasicRemote().sendText(message);
			}
		}
		catch(IOException ex)
		{
			throw new RuntimeException(ex);
		}
	}
	
}
