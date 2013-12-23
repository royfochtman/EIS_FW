package com.musicbox.junit;

/**
 * Created by David on 23.12.13.
 */
import com.musicbox.util.websocket.WebsocketChatMessage;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
public class WebsocketChatMessageTest {
    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testFromString() throws Exception {
        WebsocketChatMessage websocketChatMessage = new WebsocketChatMessage("Test", "jrngjwnerfnwr");
        String string = websocketChatMessage.toString();
        WebsocketChatMessage message = WebsocketChatMessage.fromString(string);
        assertTrue(message.equals(websocketChatMessage));
    }
}
