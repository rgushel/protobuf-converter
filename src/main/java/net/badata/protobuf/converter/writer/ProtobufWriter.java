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

package net.badata.protobuf.converter.writer;

import com.google.protobuf.Message;
import java.util.Map;
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
		if (Map.class.isAssignableFrom(valueClass)) {
			return Map.class;
		}
		return valueClass;
	}
}
