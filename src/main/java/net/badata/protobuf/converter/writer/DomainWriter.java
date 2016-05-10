package net.badata.protobuf.converter.writer;

import net.badata.protobuf.converter.inspection.DefaultValue;
import net.badata.protobuf.converter.annotation.ProtoField;
import net.badata.protobuf.converter.exception.WriteException;
import net.badata.protobuf.converter.inspection.NullValueInspector;
import net.badata.protobuf.converter.type.TypeConverter;
import net.badata.protobuf.converter.utils.AnnotationUtils;
import net.badata.protobuf.converter.utils.FieldUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * Writes data to the domain instance.
 *
 * @author jsjem
 */
public class DomainWriter extends AbstractWriter {

	private final Class<?> destinationClass;

	/**
	 * Constructor.
	 *
	 * @param destination Domain object.
	 */
	public DomainWriter(final Object destination) {
		super(destination);
		destinationClass = destination.getClass();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void write(final Object destination, final Field field, final Object value) throws WriteException {
		ProtoField annotation = field.getAnnotation(ProtoField.class);
		NullValueInspector nullInspector = AnnotationUtils.createNullValueInspector(annotation);
		DefaultValue defaultValueCreator = AnnotationUtils.createDefaultValue(annotation);
		TypeConverter<?, ?> typeConverter = AnnotationUtils.createTypeConverter(annotation);
		if (nullInspector.isNull(value)) {
			writeValue(destination, field, defaultValueCreator.generateValue(field.getType()));
		} else {
			writeValue(destination, field, typeConverter.toDomainValue(value));
		}
	}

	private void writeValue(final Object destination, final Field field, final Object value) throws WriteException {
		String setterName = FieldUtils.createDomainSetterName(field);
		try {
			destinationClass.getMethod(setterName, field.getType()).invoke(destination, value);
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
}
