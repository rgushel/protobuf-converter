package net.badata.protobuf.converter.writer;

import net.badata.protobuf.converter.exception.WriteException;
import net.badata.protobuf.converter.inspection.DefaultValue;
import net.badata.protobuf.converter.inspection.NullValueInspector;
import net.badata.protobuf.converter.resolver.FieldResolver;
import net.badata.protobuf.converter.type.TypeConverter;
import net.badata.protobuf.converter.utils.FieldUtils;

import java.lang.reflect.InvocationTargetException;

/**
 * Writes data to the domain instance.
 *
 * @author jsjem
 * @author Roman Gushel
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
	protected void write(final Object destination, final FieldResolver fieldResolver, final Object value)
			throws WriteException {
		NullValueInspector nullInspector = fieldResolver.getNullValueInspector();
		DefaultValue defaultValueCreator = fieldResolver.getDefaultValue();
		TypeConverter<?, ?> typeConverter = fieldResolver.getTypeConverter();
		if (nullInspector.isNull(value)) {
			writeValue(destination, fieldResolver, defaultValueCreator.generateValue(fieldResolver.getDomainType()));
		} else {
			writeValue(destination, fieldResolver, typeConverter.toDomainValue(value));
		}
	}

	private void writeValue(final Object destination,  final FieldResolver fieldResolver, final Object value) throws WriteException {
		String setterName = FieldUtils.createDomainSetterName(fieldResolver);
		try {
			destinationClass.getMethod(setterName, fieldResolver.getDomainType()).invoke(destination, value);
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
