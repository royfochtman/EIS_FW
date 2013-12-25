package com.musicbox.junit;

/**
 * Created by David on 23.12.13.
 */
import com.musicbox.util.websocket.WebsocketMessageType;
import com.musicbox.util.websocket.WebsocketTextMessage;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
public class WebsocketTextMessageTest {
    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testFromString() throws Exception {
        WebsocketTextMessage websocketTextMessage = new WebsocketTextMessage("Test", WebsocketMessageType.CHAT, "dfvwefe");
        String string = websocketTextMessage.toString();
        WebsocketTextMessage message = WebsocketTextMessage.fromString(string);
        assertTrue(message.equals(websocketTextMessage));
    }
}
