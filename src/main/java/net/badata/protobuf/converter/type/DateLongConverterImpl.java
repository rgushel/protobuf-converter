package net.badata.protobuf.converter.type;


import java.util.Date;

/**
 * Converts domain {@link java.util.Date Date} field value to protobuf {@link java.lang.Long Long} field value.
 *
 * @author jsjem
 * @author Roman Gushel
 */
public class DateLongConverterImpl implements TypeConverter<Date, Long> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Date toDomainValue(final Object instance) {
		return new Date((Long) instance);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Long toProtobufValue(final Object instance) {
		return ((Date) instance).getTime();
	}
}
