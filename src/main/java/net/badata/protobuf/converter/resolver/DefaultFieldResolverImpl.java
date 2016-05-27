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

import net.badata.protobuf.converter.inspection.DefaultNullValueInspectorImpl;
import net.badata.protobuf.converter.inspection.DefaultValue;
import net.badata.protobuf.converter.inspection.NullValueInspector;
import net.badata.protobuf.converter.inspection.SimpleDefaultValueImpl;
import net.badata.protobuf.converter.type.DefaultConverterImpl;
import net.badata.protobuf.converter.type.TypeConverter;

import java.lang.reflect.Field;

/**
 * Default implementation of {@link net.badata.protobuf.converter.resolver.FieldResolver FieldResolver}.
 *
 * @author jsjem
 */
public class DefaultFieldResolverImpl implements FieldResolver {

	private final Field field;
	private String domainName;
	private String protobufName;
	private TypeConverter<?, ?> converter;
	private NullValueInspector nullValueInspector;
	private DefaultValue defaultValue;


	/**
	 * Constructor.
	 *
	 * @param field Domain field.
	 */
	public DefaultFieldResolverImpl(final Field field) {
		this.field = field;
		this.domainName = field.getName();
		this.protobufName = field.getName();
		this.converter = new DefaultConverterImpl();
		this.nullValueInspector = new DefaultNullValueInspectorImpl();
		this.defaultValue = new SimpleDefaultValueImpl();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field getField() {
		return field;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getDomainName() {
		return domainName;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getProtobufName() {
		return protobufName;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TypeConverter<?, ?> getTypeConverter() {
		return converter;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public NullValueInspector getNullValueInspector() {
		return nullValueInspector;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DefaultValue getDefaultValue() {
		return defaultValue;
	}

	public void setProtobufName(final String protobufName) {
		this.protobufName = protobufName;
	}

	public void setConverter(final TypeConverter<?, ?> converter) {
		this.converter = converter;
	}

	public void setNullValueInspector(final NullValueInspector nullValueInspector) {
		this.nullValueInspector = nullValueInspector;
	}

	public void setDefaultValue(final DefaultValue defaultValue) {
		this.defaultValue = defaultValue;
	}
}
