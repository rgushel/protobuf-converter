package net.badata.protobuf.converter.writer;

import com.google.protobuf.Message;
import net.badata.protobuf.converter.exception.WriteException;
import net.badata.protobuf.converter.resolver.FieldResolver;
import net.badata.protobuf.converter.type.TypeConverter;
import net.badata.protobuf.converter.utils.FieldUtils;
import net.badata.protobuf.converter.utils.Primitives;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

/**
 * Writes data to the protobuf dto.
 *
 * @author jsjem
 * @author Roman Gushel
 */
public class ProtobufWriter extends AbstractWriter {

	private final Class<? extends Message.Builder> destinationClass;

	/**
	 * Constructor.
	 *
	 * @param destination Protobuf dto builder instance.
	 */
	public ProtobufWriter(final Message.Builder destination) {
		super(destination);
		destinationClass = destination.getClass();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void write(final Object destination, final FieldResolver fieldResolver, final Object value) throws
			WriteException {
		if (value != null) {
			TypeConverter<?, ?> typeConverter = fieldResolver.getTypeConverter();
			writeValue(destination, fieldResolver, typeConverter.toProtobufValue(value));
		}
	}


	private void writeValue(final Object destination, final FieldResolver fieldResolver, final Object value) throws WriteException {
		Class<?> valueClass = extractValueClass(value);
		while (valueClass != null) {
			String setterName = FieldUtils.createProtobufSetterName(fieldResolver);
			try {
				destinationClass.getMethod(setterName, valueClass).invoke(destination, value);
				break;
			} catch (IllegalAccessException e) {
				throw new WriteException(
						String.format("Access denied. '%s.%s(%s)'", destinationClass.getName(), setterName, valueClass));
			} catch (InvocationTargetException e) {
				throw new WriteException(
						String.format("Can't set field value through '%s.%s(%s)'", destinationClass.getName(), setterName, valueClass));
			} catch (NoSuchMethodException e) {
				if (valueClass.getSuperclass() != null) {
					valueClass = valueClass.getSuperclass();
				} else {
					throw new WriteException(
							String.format("Setter not found: '%s.%s(%s)'", destinationClass.getName(), setterName, valueClass));
				}
			}
		}
	}

	private Class<?> extractValueClass(final Object value) {
		Class<?> valueClass = value.getClass();
		if (Primitives.isWrapperType(valueClass)) {
			return Primitives.unwrap(valueClass);
		}
		if (Collection.class.isAssignableFrom(valueClass)) {
			return Iterable.class;
		}
		return valueClass;
	}
}
