package com.musicbox.util.websocket;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

/**
 * Created by David on 19.12.13.
 */
public class WebsocketChatMessageDecoder implements Decoder.Text<WebsocketChatMessage> {
    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }

    @Override
    public WebsocketChatMessage decode(String s) throws DecodeException {
        return WebsocketChatMessage.fromString(s);
    }

    @Override
    public boolean willDecode(String s) {
        return false;
    }
}
