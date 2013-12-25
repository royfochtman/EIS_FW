package com.musicbox.util.websocket;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

/**
 * Created by David on 25.12.13.
 */
public class WebsocketTextMessageEncoder implements Encoder.Text<WebsocketTextMessage> {

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }

    @Override
    public String encode(WebsocketTextMessage websocketTextMessage) throws EncodeException {
        return websocketTextMessage.toString();
    }
}
