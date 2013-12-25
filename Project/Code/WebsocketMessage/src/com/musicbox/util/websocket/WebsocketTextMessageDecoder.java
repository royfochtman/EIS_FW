package com.musicbox.util.websocket;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

/**
 * Created by David on 25.12.13.
 */
public class WebsocketTextMessageDecoder implements Decoder.Text<WebsocketTextMessage> {

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }

    @Override
    public WebsocketTextMessage decode(String s) throws DecodeException {
        return WebsocketTextMessage.fromString(s);
    }

    @Override
    public boolean willDecode(String s) {
        if(s != null && !s.isEmpty())
            return true;
        else
            return false;
    }
}
