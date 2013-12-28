package com.musicbox.util.websocket;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

/**
 * Encoder is used by the websocket-server-endpoint to encode an instance of
 * WebsocketTextMessage-Class to String, so that the message can be sended over websocket-protocol
 * to websocket-clients
 *
 * @author David Wachs
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
