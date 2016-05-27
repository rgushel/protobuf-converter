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

import net.badata.protobuf.converter.annotation.ProtoField;
import net.badata.protobuf.converter.exception.ConverterException;
import net.badata.protobuf.converter.exception.WriteException;
import net.badata.protobuf.converter.utils.AnnotationUtils;

import java.lang.reflect.Field;

/**
 * Implementation of {@link net.badata.protobuf.converter.resolver.FieldResolverFactory FieldResolverFactory} that
 * creates FieldResolver according to data stored in the {@link net.badata.protobuf.converter.annotation.ProtoField
 * ProtoField} annotation.
 *
 * @author jsjem
 */
public class AnnotatedFieldResolverFactoryImpl implements FieldResolverFactory {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public FieldResolver createResolver(final Field field) {
		DefaultFieldResolverImpl fieldResolver = new DefaultFieldResolverImpl(field);
		if (field.isAnnotationPresent(ProtoField.class)) {
			ProtoField protoField = field.getAnnotation(ProtoField.class);
			if (!"".equals(protoField.name())) {
				fieldResolver.setProtobufName(protoField.name());
			}
			try {
				fieldResolver.setConverter(AnnotationUtils.createTypeConverter(protoField));
				fieldResolver.setNullValueInspector(AnnotationUtils.createNullValueInspector(protoField));
				fieldResolver.setDefaultValue(AnnotationUtils.createDefaultValue(protoField));
			} catch (WriteException e) {
				throw new ConverterException("Can't create field resolver", e);
			}
		}
		return fieldResolver;
	}
}
