package org.acme.socket;

import com.google.gson.Gson;
import org.acme.home.Home;

import javax.websocket.EncodeException;
import javax.websocket.EndpointConfig;
import javax.websocket.Encoder.Text;

public class HomeEncoder implements Text<Home> {


    @Override
    public void init(EndpointConfig config) {

    }

    @Override
    public String encode(Home home) throws EncodeException {
        System.out.println("Encoding hom:"+home.getHome());
        return new Gson().toJson(home);
    }

    @Override
    public void destroy() {

    }

    private static String JBuilder(Home home) throws Exception{
        return new Gson().toJson(home);
    }

}
