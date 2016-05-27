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

/**
 * Interface for converting fields values if their types in the domain object and protobuf object different.
 *
 * @param <T> Field type in the domain object.
 * @param <E> Field type in the protobuf object.
 * @author jsjem
 */
public interface TypeConverter<T, E> {

	/**
	 * Convert instance from protobuf object type to domain object type.
	 *
	 * @param instance Instance for conversion.
	 * @return converted data.
	 */
	T toDomainValue(final Object instance);

	/**
	 * Convert instance from domain object type to protobuf object type.
	 *
	 * @param instance Instance for conversion.
	 * @return converted data.
	 */
	E toProtobufValue(final Object instance);

}
