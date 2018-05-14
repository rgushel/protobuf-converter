package net.badata.protobuf.converter.resolver;

import net.badata.protobuf.converter.annotation.ProtoField;
import net.badata.protobuf.converter.exception.ConverterException;
import net.badata.protobuf.converter.exception.WriteException;
import net.badata.protobuf.converter.utils.AnnotationUtils;
import net.badata.protobuf.converter.utils.FieldUtils;

import java.lang.reflect.Field;

/**
 * Implementation of {@link net.badata.protobuf.converter.resolver.FieldResolverFactory FieldResolverFactory} that
 * creates FieldResolver according to data stored in the {@link net.badata.protobuf.converter.annotation.ProtoField
 * ProtoField} annotation.
 *
 * @author jsjem
 * @author Roman Gushel
 */
public class AnnotatedFieldResolverFactoryImpl implements FieldResolverFactory {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public FieldResolver createResolver(final Field field) {
		DefaultFieldResolverImpl fieldResolver = new DefaultFieldResolverImpl(field);
		if (field.isAnnotationPresent(ProtoField.class)) {
			try {
				initializeFieldResolver(fieldResolver, field.getAnnotation(ProtoField.class));
			} catch (WriteException e) {
				throw new ConverterException("Can't initialize field resolver", e);
			}
		}
		return fieldResolver;
	}

	private void initializeFieldResolver(final DefaultFieldResolverImpl resolver, final ProtoField annotation) throws
			WriteException {
		if (!"".equals(annotation.name())) {
			resolver.setProtobufName(annotation.name());
		}
		Class<?> protobufType = FieldUtils.extractProtobufFieldType(annotation.converter(), resolver.getProtobufType());
		resolver.setProtobufType(protobufType);
		resolver.setConverter(AnnotationUtils.createTypeConverter(annotation));
		resolver.setNullValueInspector(AnnotationUtils.createNullValueInspector(annotation));
		resolver.setDefaultValue(AnnotationUtils.createDefaultValue(annotation));
	}

}
