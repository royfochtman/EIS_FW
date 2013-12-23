package com.musicbox.server.websocket;


import javax.websocket.server.ServerEndpointConfig;

/**
 * Created by David on 17.12.13.
 */
public class WebsocketServerEndpointConfigurator extends ServerEndpointConfig.Configurator {
    private static WebsocketServerEndpoint endpoint = new WebsocketServerEndpoint();

    @Override
    public <T> T getEndpointInstance(Class<T> endpointClass) throws InstantiationException {
        if (WebsocketServerEndpoint.class.equals(endpointClass)) {
            return (T)endpoint;
        } else {
            throw new InstantiationException();
        }
    }
}
