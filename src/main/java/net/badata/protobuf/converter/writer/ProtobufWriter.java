package net.badata.protobuf.converter.writer;

import com.google.protobuf.Message;
import net.badata.protobuf.converter.annotation.ProtoField;
import net.badata.protobuf.converter.exception.WriteException;
import net.badata.protobuf.converter.type.TypeConverter;
import net.badata.protobuf.converter.utils.AnnotationUtils;
import net.badata.protobuf.converter.utils.FieldUtils;
import net.badata.protobuf.converter.utils.Primitives;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

/**
 * Writes data to the protobuf dto.
 *
 * @author jsjem
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
	protected void write(final Object destination, final Field field, final Object value) throws WriteException {
		if (value != null) {
			TypeConverter<?, ?> typeConverter = AnnotationUtils
					.createTypeConverter(field.getAnnotation(ProtoField.class));
			writeValue(destination, field, typeConverter.toProtobufValue(value));
		}
	}


	private void writeValue(final Object destination, final Field field, final Object value) throws WriteException {
		String setterName = FieldUtils.createProtobufSetterName(field);
		try {
			destinationClass.getMethod(setterName, extractValueClass(value)).invoke(destination, value);
		} catch (IllegalAccessException e) {
			throw new WriteException(
					String.format("Access denied. '%s.%s()'", destinationClass.getName(), setterName));
		} catch (InvocationTargetException e) {
			throw new WriteException(
					String.format("Can't set field value through '%s.%s()'", destinationClass.getName(), setterName));
		} catch (NoSuchMethodException e) {
			throw new WriteException(
					String.format("Setter not found: '%s.%s()'", destinationClass.getName(), setterName));
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
