package net.badata.protobuf.converter.domain;


import net.badata.protobuf.converter.annotation.ProtoField;

public class Liquid {
    @ProtoField
    private int mlVolume;
    @ProtoField
    private float temperature;

    public int getMlVolume() {
        return mlVolume;
    }

    public void setMlVolume(int mlVolume) {
        this.mlVolume = mlVolume;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }
}
