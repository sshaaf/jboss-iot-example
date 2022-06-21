package org.acme.device.features;

public class Humidity extends Feature{

    private String name = "Humidity";

    private String value = "0.0";

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getValue() {
        return value;
    }

    public void setValue(){
        int min = 10;
        int max = 50;
        this.value = String.valueOf((int) (Math.random() * (max - min)) + min);
    }


}
