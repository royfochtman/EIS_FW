package com.musicbox.util.websocket;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

/**
 * Encoder is used by the websocket-server-endpoint to encode an instance of
 * WebsocketMessage-Class to BinaryStream, so that the message can be sended over websocket-protocol
 * to websocket-clients
 *
 * @author David Wachs
 */
public class WebsocketMessageEncoder implements Encoder.BinaryStream<WebsocketMessage> {
        @Override
        public void encode(WebsocketMessage websocketMessage, OutputStream outputStream) throws EncodeException, IOException {
            ObjectOutputStream out = new ObjectOutputStream(outputStream);
            out.writeObject(websocketMessage);
            out.close();
            outputStream.close();
        }

        @Override
        public void init(EndpointConfig endpointConfig) {

        }

        @Override
        public void destroy() {

        }
}
