package net.badata.protobuf.converter.utils;

import net.badata.protobuf.converter.annotation.ProtoClass;
import net.badata.protobuf.converter.annotation.ProtoField;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;

/**
 * Created by jsjem on 25.04.2016.
 */
public final class FieldUtils {


	private static final String GETTER_PREFIX = "get";
	private static final String SETTER_PREFIX = "set";
	private static final String BOOLEAN_GETTER_PREFIX = "is";
	private static final String PROTOBUF_LIST_GETTER_POSTFIX = "List";
	private static final String PROTOBUF_LIST_SETTER_PREFIX = "addAll";
	private static final String PROTOBUF_NESTED_BUILDER_POSTFIX = "Builder";


	/**
	 * Check whether field has own mapper.
	 *
	 * @param field Testing field.
	 * @return true if field type has {@link net.badata.protobuf.converter.annotation.ProtoClass} annotation.
	 */
	public static boolean isComplexType(final Field field) {
		return isComplexType(field.getType());
	}

	/**
	 * Check whether type has own mapper.
	 *
	 * @param type Testing type.
	 * @return true if field type has {@link net.badata.protobuf.converter.annotation.ProtoClass} annotation.
	 */
	public static boolean isComplexType(final Class<?> type) {
		return type.isAnnotationPresent(ProtoClass.class);
	}

	/**
	 * Check whether field type implements Collection interface.
	 *
	 * @param field Testing field.
	 * @return true if field type implements {@link java.util.Collection}, otherwise false.
	 */
	public static boolean isCollectionType(final Field field) {
		return Collection.class.isAssignableFrom(field.getType());
	}

	/**
	 * Create protobuf getter name for domain field.
	 *
	 * @param field Domain object field.
	 * @return Protobuf field getter name.
	 */
	public static String createProtobufGetterName(final Field field) {
		String getterName = StringUtils.createMethodName(GETTER_PREFIX, getProtobufFieldName(field));
		if (isCollectionType(field)) {
			return getterName + PROTOBUF_LIST_GETTER_POSTFIX;
		}
		return getterName;
	}

	/**
	 * Find protobuf field name related to the domain field.
	 *
	 * @param field Domain object field.
	 * @return Protobuf field name.
	 */
	public static String getProtobufFieldName(final Field field) {
		String protobufFieldName = field.getAnnotation(ProtoField.class).name();
		return "".equals(protobufFieldName) ? field.getName() : protobufFieldName;
	}

	/**
	 * Create protobuf setter name for domain field.
	 *
	 * @param field Domain object field.
	 * @return Protobuf field setter name.
	 */
	public static String createProtobufSetterName(final Field field) {
		if (isCollectionType(field)) {
			return StringUtils.createMethodName(PROTOBUF_LIST_SETTER_PREFIX, getProtobufFieldName(field));
		}
		return StringUtils.createMethodName(SETTER_PREFIX, getProtobufFieldName(field));
	}

	/**
	 * Create protobuf builder getter name for complex domain field.
	 *
	 * @param field Domain object field.
	 * @return Protobuf field builder getter name.
	 */
	public static String createProtobufBuilderName(final Field field) {
		String getterName = StringUtils.createMethodName(GETTER_PREFIX, getProtobufFieldName(field));
		return getterName + PROTOBUF_NESTED_BUILDER_POSTFIX;
	}

	/**
	 * Create domain field getter name.
	 *
	 * @param field Domain object field.
	 * @return Domain field getter name.
	 */
	public static String createDomainGetterName(final Field field) {
		if (field.getType() == boolean.class) {
			return StringUtils.createMethodName(BOOLEAN_GETTER_PREFIX, field.getName());
		}
		return StringUtils.createMethodName(GETTER_PREFIX, field.getName());
	}

	/**
	 * Create domain field setter name.
	 *
	 * @param field Domain object field.
	 * @return Domain field setter name.
	 */
	public static String createDomainSetterName(final Field field) {
		return StringUtils.createMethodName(SETTER_PREFIX, field.getName());
	}

	/**
	 * Extract parameter type of the collection.
	 *
	 * @param field Field with type derived from {@link java.util.Collection}.
	 * @return Collection parameter.
	 */
	public static Class<?> extractCollectionType(final Field field) {
		ParameterizedType stringListType = (ParameterizedType) field.getGenericType();
		return (Class<?>) stringListType.getActualTypeArguments()[0];
	}

	private FieldUtils() {
		// empty
	}
}
