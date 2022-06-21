package org.acme.home;

import com.google.gson.Gson;
import org.acme.device.Device;
import org.acme.device.features.Feature;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class Home implements Serializable {
    private static final long serialVersionUID = 1L;
    private final int id;
    private String home = null;
    List<Device> devices = new ArrayList<>();

    private static final AtomicInteger counter = new AtomicInteger();

    private Home(){
        id = counter.getAndIncrement();
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

    public int getId(){
        return id;
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

    public String getJson() {
        for(Device d: this.getDevices()){
            for(Feature f: d.getFeatures()){
                f.setValue();
            }
        }
        return new Gson().toJson(this);
    }
}
