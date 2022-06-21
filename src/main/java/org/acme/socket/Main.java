package org.acme.socket;

import org.acme.device.Device;
import org.acme.device.ESP8266Device;
import org.acme.device.features.Feature;
import org.acme.home.Home;

import com.google.gson.Gson;
public class Main {

    public static void main(String[] args) throws Exception {
        Home h = new Home("My Home");
        h.addDevice(new ESP8266Device());

        for(Device d: h.getDevices()){
            System.out.println(d.getDeviceName());
            for(Feature f: d.getFeatures()){
                System.out.println(f.getName()+" , "+f.getValue());
                f.setValue();
            }
        }
        System.out.println(JBuilder(h));
    }


    public static String JBuilder(Home home) throws Exception{
        return new Gson().toJson(home);
    }

}
