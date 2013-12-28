package com.musicbox.util.websocket;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

/**
 * Decoder is used by the websocket-endpoint-server to decode incoming String-messages to
 * an instance of WebsocketTextMessage-Class
 *
 * @David Wachs
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
