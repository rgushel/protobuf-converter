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

package net.badata.protobuf.converter.resolver;

import net.badata.protobuf.converter.inspection.DefaultValue;
import net.badata.protobuf.converter.inspection.NullValueInspector;
import net.badata.protobuf.converter.type.TypeConverter;

import java.lang.reflect.Field;

/**
 * Resolver for domain field data that necessary for performing conversion.
 *
 * @author jsjem
 */
public interface FieldResolver {

	/**
	 * Getter for {@link java.lang.reflect.Field Field} instance.
	 *
	 * @return domain class field.
	 */
	Field getField();

	/**
	 * Getter for domain class field name.
	 *
	 * @return String with domain field name.
	 */
	String getDomainName();

	/**
	 * Getter for protobuf message field name.
	 *
	 * @return String with protobuf field name.
	 */
	String getProtobufName();

	/**
	 * Getter for field type converter.
	 *
	 * @return instance of field type converter.
	 */
	TypeConverter<?, ?> getTypeConverter();

	/**
	 * Getter for protobuf field null value inspector.
	 *
	 * @return instance of null value inspector.
	 */
	NullValueInspector getNullValueInspector();

	/**
	 * Getter for domain field default value generator.
	 *
	 * @return instance of default value generator.
	 */
	DefaultValue getDefaultValue();

}
