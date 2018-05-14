package net.badata.protobuf.example.client.domain;

import net.badata.protobuf.converter.type.TypeConverter;
import net.badata.protobuf.example.proto.BookState;

/**
 * @author jsjem
 * @author Roman Gushel
 */
public class BooleanEnumConverter implements TypeConverter<Boolean, BookState> {


	@Override
	public Boolean toDomainValue(final Object object) {
		return BookState.AVAILABLE == object;
	}

	@Override
	public BookState toProtobufValue(final Object object) {
		return ((Boolean) object).booleanValue() ? BookState.AVAILABLE : BookState.UNAVAILABLE;
	}
}
