package org.acme.home;

import org.acme.device.ESP8266Device;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class HomeFactory {

    private static Set<Home> homes = Collections.synchronizedSet(new HashSet<>());



    public static synchronized Set<Home> getLatestData() {
        Home h = new Home("My Home");
        h.addDevice(new ESP8266Device());
        homes.add(h);
        return homes;
    }


    public static synchronized void setNewHome(Home h){
        if(h != null){
            homes.add(h);
        }
        else throw new IllegalArgumentException("Home cannot be null in this factory");
    }

}
