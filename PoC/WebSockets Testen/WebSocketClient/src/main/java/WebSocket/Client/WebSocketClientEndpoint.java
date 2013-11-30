package main.java.WebSocket.Client;
import java.awt.EventQueue;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Logger;

import javax.websocket.*;

@ClientEndpoint
public class WebSocketClientEndpoint {
	private static Session session;
	private static CountDownLatch latch;
	private Logger logger = Logger.getLogger(this.getClass().getName());
	
	@OnOpen
	public void onOpen(Session session)
	{
		//logger.info("Client: Connected ... " + session.getId());
		WebSocketClientSwing.txt_connectionMessages.insert("Client: Connected..." + session.getId(), 0);
        try {
            session.getBasicRemote().sendText("start");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
	}
	
	@OnClose
	public void onClose(Session session, CloseReason closeReason)
	{
		//logger.info(String.format("Client: Session %s close because of %s", session.getId(), closeReason));
		WebSocketClientSwing.txt_connectionMessages.append("\nClient: Session" + session.getId() + " close because of" +  closeReason);
	}
	
	@OnMessage
	public void onMessage(String message, Session session)
	{
		//BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        try {
	            //logger.info("Client: Received ...." + message);
	            WebSocketClientSwing.txt_connectionMessages.append("\nCLient: Received..." + message);
	            WebSocketClientSwing.txt_message.append(message);
	            //String userInput = bufferRead.readLine();
	            //String userInput = WebSocketClientSwing.txt_message.getText();
	           //return userInput;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
	}
	
	
	public static void openSession()
	{
		//latch = new CountDownLatch(1);
		try
		{
			WebSocketContainer container = ContainerProvider.getWebSocketContainer();
			session = container.connectToServer(WebSocketClientEndpoint.class, new URI("ws://localhost:8080/WebSocketServerServlet/websocketTest"));
			//latch.await();
		}
		catch(DeploymentException | IOException | URISyntaxException e)
		{
			session = null;
		}
	}
	
	public static void sendMessage(String message)
	{
		try {
			session.getBasicRemote().sendText(message);
		} catch (IOException e) {
			WebSocketClientSwing.txt_connectionMessages.append("\nCLient: Error...Could not send message!");
			e.printStackTrace();
		}
	}
	
	public static void closeConnection()
	{
		try {
			session.getBasicRemote().sendText("quit");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WebSocketClientSwing window = new WebSocketClientSwing();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});		
	}
}
