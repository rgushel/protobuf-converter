package net.badata.protobuf.converter.type;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Converts domain {@link java.util.Set Set} field value to protobuf {@link java.util.List List} field value.
 *
 * @author jsjem
 * @author Roman Gushel
 */
public class SetListConverterImpl implements TypeConverter<Set<?>, List<?>> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<?> toDomainValue(final Object instance) {
		return new HashSet<Object>((List<?>) instance);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<?> toProtobufValue(final Object instance) {
		return new ArrayList<Object>((Set<?>) instance);
	}
}
