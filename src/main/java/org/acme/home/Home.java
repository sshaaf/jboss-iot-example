package org.acme.home;

import org.acme.device.Device;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Home {
    private UUID uuid = null;
    private String home = null;
    List<Device> devices = new ArrayList<>();



    private Home(){
        uuid = UUID.randomUUID();
    }

    public Home(String home){
        this();
        if(home != null){
            home = home;
        }
        else
            throw new IllegalArgumentException("Expecting name for this home but not NULL");
    }

    public Home(List<Device> devices, String home){
        this(home);
        if(devices != null) {
            this.devices = devices;
        }
        else
            throw new IllegalArgumentException("No devices for this home"+home);
    }

    public List<Device> getDevices(){
        return devices;
    }

    public void addDevice(Device device){
        if(device != null){
            devices.add(device);
            if(device.getFeatures().isEmpty())
                throw new IllegalArgumentException("Device cannot be added when no features exist");
        }
        else
            throw new IllegalArgumentException("Device cannot be null");
    }

    public void setDevices(List<Device> devices) {
        this.devices = devices;
    }

    public String getHome() {
        return home;
    }

}
