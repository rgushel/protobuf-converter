package net.badata.protobuf.converter.domain;

import net.badata.protobuf.converter.annotation.ProtoClass;
import net.badata.protobuf.converter.annotation.ProtoField;
import net.badata.protobuf.converter.proto.LiquidProto;

@ProtoClass(LiquidProto.Coffee.class)
public class Coffee extends Liquid {
    @ProtoField
    private boolean swirling;
    @ProtoField
    private boolean clockwise;

    public boolean isSwirling() {
        return swirling;
    }

    public void setSwirling(boolean swirling) {
        this.swirling = swirling;
    }

    public boolean isClockwise() {
        return clockwise;
    }

    public void setClockwise(boolean clockwise) {
        this.clockwise = clockwise;
    }
}
