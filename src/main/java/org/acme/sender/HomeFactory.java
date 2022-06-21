package org.acme.sender;

import org.acme.device.ESP8266Device;
import org.acme.home.Home;

import java.io.Serializable;
import java.util.*;

public class HomeFactory {

    private static Map<Integer, Home> homes = new LinkedHashMap<>();

    public HomeFactory() {
        Home h = new Home("My Home");
        h.addDevice(new ESP8266Device());
        homes.put(h.getId(), h);
    }

    public String getNextRandomHome(){
       return homes.get((int) (Math.random() * (homes.size()))).getJson();
    }

    public void setNewHome(Home h){
        if(h != null){
            homes.put(h.getId(), h);
        }
        else throw new IllegalArgumentException("Home cannot be null in this factory");
    }

}
