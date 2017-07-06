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

package net.badata.protobuf.converter.mapping;

import java.lang.reflect.InvocationTargetException;

import com.google.protobuf.Message;

import net.badata.protobuf.converter.exception.MappingException;
import net.badata.protobuf.converter.resolver.FieldResolver;
import net.badata.protobuf.converter.utils.FieldUtils;


/**
 * Implementation of {@link net.badata.protobuf.converter.mapping.Mapper Mapper} that is applied by default.
 * This implementation maps fields values directly from domain instance to related protobuf instance and vice versa.
 *
 * @author jsjem
 */
public class DefaultMapperImpl implements Mapper {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <T extends Message> MappingResult mapToDomainField(final FieldResolver fieldResolver, final T protobuf,
			final Object domain) throws MappingException {
		Object protobufFieldValue = getFieldValue(FieldUtils.createProtobufGetterName(fieldResolver), protobuf);
		if (FieldUtils.isComplexType(fieldResolver.getField())) {
			return new MappingResult(MappingResult.Result.NESTED_MAPPING, protobufFieldValue, domain);
		}
		if (FieldUtils.isCollectionType(fieldResolver.getField())) {
			return new MappingResult(MappingResult.Result.COLLECTION_MAPPING, protobufFieldValue, domain);
		}
		if (FieldUtils.isMapType(fieldResolver.getField())) {
			return new MappingResult(MappingResult.Result.MAP_MAPPING, protobufFieldValue, domain);
		}
		return new MappingResult(MappingResult.Result.MAPPED, protobufFieldValue, domain);
	}

	private Object getFieldValue(final String getterName, final Object source) throws MappingException {
		Class<?> sourceClass = source.getClass();
		try {
			return sourceClass.getMethod(getterName).invoke(source);
		} catch (IllegalAccessException e) {
			throw new MappingException(
					String.format("Access denied. '%s.%s()'", sourceClass.getName(), getterName));
		} catch (InvocationTargetException e) {
			throw new MappingException(
					String.format("Can't set field value through '%s.%s()'", sourceClass.getName(), getterName));
		} catch (NoSuchMethodException e) {
			throw new MappingException(
					String.format("Getter not found. '%s.%s()'", sourceClass.getName(), getterName));
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <T extends Message.Builder> MappingResult mapToProtobufField(final FieldResolver fieldResolver, final
	Object domain, final T
			protobufBuilder) throws MappingException {
		Object domainFieldValue = getFieldValue(FieldUtils.createDomainGetterName(fieldResolver), domain);
		if (FieldUtils.isComplexType(fieldResolver.getField())) {
			return new MappingResult(MappingResult.Result.NESTED_MAPPING, domainFieldValue, protobufBuilder);
		}
		if (FieldUtils.isCollectionType(fieldResolver.getField())) {
			return new MappingResult(MappingResult.Result.COLLECTION_MAPPING, domainFieldValue, protobufBuilder);
		}
		if (FieldUtils.isMapType(fieldResolver.getField())) {
			return new MappingResult(MappingResult.Result.MAP_MAPPING, domainFieldValue, protobufBuilder);
		}
		return new MappingResult(MappingResult.Result.MAPPED, domainFieldValue, protobufBuilder);
	}

}
