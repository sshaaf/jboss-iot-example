package org.acme.socket;

import com.google.gson.Gson;
import org.acme.home.Home;

import javax.websocket.EncodeException;
import javax.websocket.EndpointConfig;
import javax.websocket.Encoder.Text;

public class HomeEncoder implements Text<String> {


    @Override
    public void init(EndpointConfig config) {

    }

    @Override
    public String encode(String s) throws EncodeException {
        return s;
    }

    @Override
    public void destroy() {

    }

}
