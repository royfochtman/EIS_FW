package com.musicbox.util.websocket;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.nio.ByteBuffer;

/**
 * Created by David on 19.12.13.
 */
public class WebsocketChatMessageEncoder implements Encoder.Text<WebsocketChatMessage> {
    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }


    @Override
    public String encode(WebsocketChatMessage websocketChatMessage) throws EncodeException {
        return websocketChatMessage.toString();
    }
}
