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

package net.badata.protobuf.converter.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation that marks domain class that may be converted to Protobuf message. This annotation may contain several
 * {@link net.badata.protobuf.converter.annotation.ProtoClass ProtoClass} annotations.
 *
 * @author jsjem
 */
@Target(value = ElementType.TYPE)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface ProtoClasses {

	/**
	 * Retrieve array of possible {@code ProtoClass}es for domain.
	 *
	 * @return Array of {@link net.badata.protobuf.converter.annotation.ProtoClass ProtoClass}.
	 */
	ProtoClass[] value();
}
