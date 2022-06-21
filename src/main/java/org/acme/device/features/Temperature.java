package org.acme.device.features;

public class Temperature extends Feature{

    private String name = "Temperature";

    private String value = "0.0";

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getValue() {
        int min = 50;
        int max = 100;
        return String.valueOf((int) (Math.random() * (max - min)) + min);
    }

    public void setValue(){
        int min = 10;
        int max = 50;
        this.value = String.valueOf((int) (Math.random() * (max - min)) + min);
    }
}
