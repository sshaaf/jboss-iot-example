package org.acme.device;

import org.acme.device.features.Feature;

import java.util.List;

public interface Device {

    public String getDeviceName();

    public List<Feature> getFeatures();

}
