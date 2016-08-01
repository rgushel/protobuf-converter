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

package net.badata.protobuf.converter.type;


import java.lang.reflect.ParameterizedType;

/**
 *  Converts domain {@link java.lang.Enum Enum} field value to protobuf {@link java.lang.String String} field value.
 *
 * @author future
 */
public class EnumStringConverter<T extends Enum<T>> implements TypeConverter<T, String> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	@SuppressWarnings("unchecked")
	public T toDomainValue(Object instance) {
		if (((String) instance).isEmpty()) {
			return null;
		}
		Class<T> enumType = (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
		return Enum.valueOf(enumType, (String) instance);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toProtobufValue(Object instance) {
		return instance.toString();
	}
}
