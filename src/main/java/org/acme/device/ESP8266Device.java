package org.acme.device;

import org.acme.device.features.Feature;
import org.acme.device.features.Humidity;
import org.acme.device.features.Temperature;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ESP8266Device implements Device {
    private final String deviceName = "ESP8266-01";
    private UUID uuid = null;

    private List<Feature> features = new ArrayList<>();

    public ESP8266Device(){
        uuid = UUID.randomUUID();
        Humidity humidity = new Humidity();
        features.add(humidity);
        Temperature temp = new Temperature();
        features.add(temp);
    }

    public String getDeviceName() {
        return deviceName;
    }

    @Override
    public List<Feature> getFeatures() {
        return features;
    }
}
