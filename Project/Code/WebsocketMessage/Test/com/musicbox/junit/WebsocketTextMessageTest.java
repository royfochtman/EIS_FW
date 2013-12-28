package com.musicbox.junit;

/**
 * @author David Wachs
 */
import com.musicbox.util.websocket.WebsocketMessageType;
import com.musicbox.util.websocket.WebsocketTextMessage;
import com.musicbox.util.websocket.WebsocketTextMessageType;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
public class WebsocketTextMessageTest {
    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testFromString() throws Exception {
        WebsocketTextMessage websocketTextMessage = new WebsocketTextMessage("Test", WebsocketTextMessageType.CHAT, "dfvwefe");
        String string = websocketTextMessage.toString();
        WebsocketTextMessage message = WebsocketTextMessage.fromString(string);
        assertTrue(message.equals(websocketTextMessage));
    }
}
