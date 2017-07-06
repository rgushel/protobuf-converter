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

/**
 * Represents results of field mapping performed by {@link net.badata.protobuf.converter.mapping.Mapper} implementation.
 *
 * @author jsjem
 */
public class MappingResult {

	private final Result code;
	private final Object value;
	private final Object destination;

	/**
	 * Result of field mapping performed by {@link net.badata.protobuf.converter.mapping.Mapper Mapper}.
	 *
	 * @param code        Mapping result code.
	 * @param value       Mapped value.
	 * @param destination Destination object to which value has to be written.
	 */
	public MappingResult(final Result code, final Object value, final Object destination) {
		this.code = code;
		this.value = value;
		this.destination = destination;
	}

	/**
	 * Getter for mapping result code.
	 *
	 * @return Constant value of {@link net.badata.protobuf.converter.mapping.MappingResult.Result Result} enum.
	 */
	public Result getCode() {
		return code;
	}

	/**
	 * Getter for mapping value.
	 *
	 * @return Value that has to be written to destination  object.
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * Getter for instance that must store mapped value.
	 *
	 * @return Instance for storing mapped value.
	 */
	public Object getDestination() {
		return destination;
	}

	/**
	 * Enum of mapping results.
	 */
	public enum Result {
		/**
		 * Single simple field is mapped.
		 */
		MAPPED,
		/**
		 * Single complex field is mapped.
		 */
		NESTED_MAPPING,
		/**
		 * Collection field is mapped.
		 */
		COLLECTION_MAPPING,
		/**
		 * Map Field is mapped.
		 */
		MAP_MAPPING;
	}
}

