package net.badata.protobuf.converter.utils;

import com.google.protobuf.Message;
import net.badata.protobuf.converter.annotation.ProtoField;
import net.badata.protobuf.converter.exception.MappingException;
import net.badata.protobuf.converter.inspection.DefaultValue;
import net.badata.protobuf.converter.mapping.Mapper;
import net.badata.protobuf.converter.annotation.ProtoClass;
import net.badata.protobuf.converter.exception.WriteException;
import net.badata.protobuf.converter.inspection.NullValueInspector;
import net.badata.protobuf.converter.type.TypeConverter;

/**
 *
 *
 * @author jsjem
 */
public class AnnotationUtils {

	/**
	 * Extract Protobuf message class related to domain class.
	 *
	 * @param domainClass Domain class annotated by {@link net.badata.protobuf.converter.annotation.ProtoClass
	 *                    ProtoClass}.
	 * @return Protobuf {@link com.google.protobuf.Message Message} instance.
	 */
	public static Class<? extends Message> extractMessageType(final Class<?> domainClass) {
		if(domainClass.isAnnotationPresent(ProtoClass.class)) {
			return domainClass.getAnnotation(ProtoClass.class).value();
		}
		return Message.class;
	}

	/**
	 * Create {@link net.badata.protobuf.converter.mapping.Mapper Mapper} implementation from class specified in the
	 * {@link net.badata.protobuf.converter.annotation.ProtoClass ProtoClass} mapper field.
	 *
	 * @param annotation Instance of {@link net.badata.protobuf.converter.annotation.ProtoClass ProtoClass} annotation.
	 * @return Instance of the {@link net.badata.protobuf.converter.mapping.Mapper Mapper} interface.
	 * @throws MappingException If mapper instance does not contain default constructor or default constructor not
	 *                          public.
	 */
	public static Mapper createMapper(final ProtoClass annotation) throws MappingException {
		try {
			return annotation.mapper().newInstance();
		} catch (InstantiationException e) {
			throw new MappingException("Default constructor not found.");
		} catch (IllegalAccessException e) {
			throw new MappingException("Make default constructor public for "
					+ annotation.mapper().getSimpleName(), e);
		}
	}

	/**
	 * Create {@link net.badata.protobuf.converter.type.TypeConverter TypeConverter} implementation from class
	 * specified in the {@link net.badata.protobuf.converter.annotation.ProtoClass ProtoClass} converter field.
	 *
	 * @param annotation Instance of {@link net.badata.protobuf.converter.annotation.ProtoClass ProtoClass} annotation.
	 * @return Instance of the {@link net.badata.protobuf.converter.type.TypeConverter TypeConverter} interface.
	 * @throws WriteException If converter instance does not contain default constructor or default constructor not
	 *                          public.
	 */
	public static TypeConverter<?, ?> createTypeConverter(final ProtoField annotation) throws WriteException {
		try {
			return annotation.converter().newInstance();
		} catch (InstantiationException e) {
			throw new WriteException("Default constructor not found.");
		} catch (IllegalAccessException e) {
			throw new WriteException("Make default constructor public for "
					+ annotation.converter().getSimpleName(), e);
		}
	}

	/**
	 * Create {@link net.badata.protobuf.converter.inspection.NullValueInspector NullValueInspector} implementation
	 * from class specified in the {@link net.badata.protobuf.converter.annotation.ProtoClass ProtoClass} nullValue
	 * field.
	 *
	 * @param annotation Instance of {@link net.badata.protobuf.converter.annotation.ProtoClass ProtoClass} annotation.
	 * @return Instance of the {@link net.badata.protobuf.converter.inspection.NullValueInspector NullValueInspector}
	 * interface.
	 * @throws WriteException If null value inspector instance does not contain default constructor or default
	 *                          constructor not public.
	 */
	public static NullValueInspector createNullValueInspector(final ProtoField annotation) throws WriteException {
		try {
			return annotation.nullValue().newInstance();
		} catch (InstantiationException e) {
			throw new WriteException("Default constructor not found.");
		} catch (IllegalAccessException e) {
			throw new WriteException("Make default constructor public for "
					+ annotation.nullValue().getSimpleName(), e);
		}
	}

	/**
	 * Create {@link net.badata.protobuf.converter.inspection.DefaultValue DefaultValue} implementation
	 * from class specified in the {@link net.badata.protobuf.converter.annotation.ProtoClass ProtoClass} nullValue
	 * field.
	 *
	 * @param annotation Instance of {@link net.badata.protobuf.converter.annotation.ProtoClass ProtoClass} annotation.
	 * @return Instance of the {@link net.badata.protobuf.converter.inspection.DefaultValue DefaultValue} interface.
	 * @throws WriteException If null default value creator instance does not contain default constructor or default
	 *                          constructor not public.
	 */
	public static DefaultValue createDefaultValue(final ProtoField annotation) throws WriteException {
		try {
			return annotation.defaultValue().newInstance();
		} catch (InstantiationException e) {
			throw new WriteException("Default constructor not found.");
		} catch (IllegalAccessException e) {
			throw new WriteException("Make default constructor public for "
					+ annotation.defaultValue().getSimpleName(), e);
		}
	}
}
