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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Converts domain {@link java.util.Set Set} field value to protobuf {@link java.util.List List} field value.
 *
 * @author jsjem
 */
public class SetListConverterImpl implements TypeConverter<Set<?>, List<?>> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<?> toDomainValue(final Object instance) {
		return new HashSet<Object>((List<?>) instance);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<?> toProtobufValue(final Object instance) {
		return new ArrayList<Object>((Set<?>) instance);
	}
}
