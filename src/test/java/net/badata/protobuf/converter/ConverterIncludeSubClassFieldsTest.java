package net.badata.protobuf.converter;


import net.badata.protobuf.converter.domain.Coffee;
import net.badata.protobuf.converter.proto.LiquidProto;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;


public class ConverterIncludeSubClassFieldsTest {

    @Test
    public void testDomainToProtobuf() {
        Coffee coffee = new Coffee();
        coffee.setMlVolume(100);
        coffee.setTemperature(40);
        coffee.setSwirling(true);
        coffee.setClockwise(false);

        LiquidProto.Coffee coffeeProto = Converter.create().toProtobuf(LiquidProto.Coffee.class,coffee);

        assertEquals(coffee.getMlVolume(), coffeeProto.getMlVolume());
        assertEquals(coffee.getTemperature(), coffeeProto.getTemperature());
        assertEquals(coffee.isSwirling(),coffeeProto.getSwirling());
        assertEquals(coffee.isClockwise(), coffeeProto.getClockwise());

    }
}
