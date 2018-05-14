package net.badata.protobuf.converter.type;

import java.lang.reflect.ParameterizedType;

/**
 * Converts domain {@link java.lang.Enum Enum} field value to protobuf {@link java.lang.String String} field value.
 *
 * @author jsjem
 * @author Roman Gushel
 */
public class EnumStringConverter<T extends Enum<T>> implements TypeConverter<T, String> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	@SuppressWarnings("unchecked")
	public T toDomainValue(Object instance) {
		if (((String) instance).isEmpty()) {
			return null;
		}
		Class<T> enumType = (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
		return Enum.valueOf(enumType, (String) instance);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toProtobufValue(Object instance) {
		return instance.toString();
	}
}
