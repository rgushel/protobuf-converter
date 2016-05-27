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

package net.badata.protobuf.converter.exception;

import com.google.protobuf.Message;

/**
 * Exception notifies that protobuf class specified in the {@link net.badata.protobuf.converter.annotation.ProtoClass
 * ProtoClass} is different form the required protobuf class.
 *
 * @author jsjem
 */
public class TypeRelationException extends Exception {

	/**
	 * Constructs a new TypeRelationExcetion with default message.
	 *
	 * @param domainType   domain instance class.
	 * @param protobufType protobuf dto instance class.
	 */
	public TypeRelationException(final Class<?> domainType, final Class<? extends Message> protobufType) {
		super(domainType.getSimpleName() + " is not bound to " + protobufType.getSimpleName());
	}
}
