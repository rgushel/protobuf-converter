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

package net.badata.protobuf.converter.annotation;

import net.badata.protobuf.converter.inspection.DefaultValue;
import net.badata.protobuf.converter.inspection.SimpleDefaultValueImpl;
import net.badata.protobuf.converter.inspection.DefaultNullValueInspectorImpl;
import net.badata.protobuf.converter.inspection.NullValueInspector;
import net.badata.protobuf.converter.type.DefaultConverterImpl;
import net.badata.protobuf.converter.type.TypeConverter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation that marks domain class fields that may be converted to Protobuf messages fields.
 *
 * @author jsjem
 */
@Target(value = ElementType.FIELD)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface ProtoField {

	/**
	 * Retrieve field name in the protobuf message declaration.
	 *
	 * @return Name of the field in the protobuf dto class or empty string if name matches the name of annotated field.
	 */
	String name() default "";

	/**
	 * Retrieve class that perform field value conversion.
	 *
	 * @return Class for converting field value or DefaultConverterImpl.
	 */
	Class<? extends TypeConverter<?, ?>> converter() default DefaultConverterImpl.class;

	/**
	 * Retrieve class for generating field default value for domain instance if protobuf field is null.
	 *
	 * @return Class for generating default field value.
	 */
	Class<? extends DefaultValue> defaultValue() default SimpleDefaultValueImpl.class;

	/**
	 * Retrieve class that perform protobuf field value check for null.
	 *
	 * @return Class for null check.
	 */
	Class<? extends NullValueInspector> nullValue() default DefaultNullValueInspectorImpl.class;
}
