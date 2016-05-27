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
			writeValue(destination, fieldResolver, defaultValueCreator.generateValue(fieldResolver.getField().getType()));
		} else {
			writeValue(destination, fieldResolver, typeConverter.toDomainValue(value));
		}
	}

	private void writeValue(final Object destination,  final FieldResolver fieldResolver, final Object value) throws WriteException {
		String setterName = FieldUtils.createDomainSetterName(fieldResolver);
		try {
			destinationClass.getMethod(setterName, fieldResolver.getField().getType()).invoke(destination, value);
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
