/*
 * Copyright (C) 2016  BAData Creative Studio
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package net.badata.protobuf.converter.utils;

import net.badata.protobuf.converter.annotation.ProtoClass;
import net.badata.protobuf.converter.annotation.ProtoClasses;
import net.badata.protobuf.converter.resolver.FieldResolver;

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
	 * @return true if field type has {@link net.badata.protobuf.converter.annotation.ProtoClass}  or
	 * {@link net.badata.protobuf.converter.annotation.ProtoClasses} annotation.
	 */
	public static boolean isComplexType(final Field field) {
		return isComplexType(field.getType());
	}

	/**
	 * Check whether type has own mapper.
	 *
	 * @param type Testing type.
	 * @return true if field type has {@link net.badata.protobuf.converter.annotation.ProtoClass} or
	 * {@link net.badata.protobuf.converter.annotation.ProtoClasses} annotation.
	 */
	public static boolean isComplexType(final Class<?> type) {
		return type.isAnnotationPresent(ProtoClass.class) || type.isAnnotationPresent(ProtoClasses.class);
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
	 * @param fieldResolver Domain object field resolver.
	 * @return Protobuf field getter name.
	 */
	public static String createProtobufGetterName(final FieldResolver fieldResolver) {
		String getterName = StringUtils.createMethodName(GETTER_PREFIX, fieldResolver.getProtobufName());
		if (isCollectionType(fieldResolver.getField())) {
			return getterName + PROTOBUF_LIST_GETTER_POSTFIX;
		}
		return getterName;
	}

	/**
	 * Create protobuf setter name for domain field.
	 *
	 * @param fieldResolver Domain object field resolver.
	 * @return Protobuf field setter name.
	 */
	public static String createProtobufSetterName(final FieldResolver fieldResolver) {
		if (isCollectionType(fieldResolver.getField())) {
			return StringUtils.createMethodName(PROTOBUF_LIST_SETTER_PREFIX, fieldResolver.getProtobufName());
		}
		return StringUtils.createMethodName(SETTER_PREFIX, fieldResolver.getProtobufName());
	}

	/**
	 * Create protobuf builder getter name for complex domain field.
	 *
	 * @param fieldResolver Domain object field resolver.
	 * @return Protobuf field builder getter name.
	 */
	public static String createProtobufBuilderName(final FieldResolver fieldResolver) {
		String getterName = StringUtils.createMethodName(GETTER_PREFIX, fieldResolver.getProtobufName());
		return getterName + PROTOBUF_NESTED_BUILDER_POSTFIX;
	}

	/**
	 * Create domain field getter name.
	 *
	 * @param fieldResolver Domain object field resolver.
	 * @return Domain field getter name.
	 */
	public static String createDomainGetterName(final FieldResolver fieldResolver) {
		if (fieldResolver.getField().getType() == boolean.class) {
			return StringUtils.createMethodName(BOOLEAN_GETTER_PREFIX, fieldResolver.getDomainName());
		}
		return StringUtils.createMethodName(GETTER_PREFIX, fieldResolver.getDomainName());
	}

	/**
	 * Create domain field setter name.
	 *
	 * @param fieldResolver Domain object field resolver.
	 * @return Domain field setter name.
	 */
	public static String createDomainSetterName(final FieldResolver fieldResolver) {
		return StringUtils.createMethodName(SETTER_PREFIX, fieldResolver.getDomainName());
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
