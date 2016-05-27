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

import com.google.protobuf.Message;
import net.badata.protobuf.converter.mapping.DefaultMapperImpl;
import net.badata.protobuf.converter.mapping.Mapper;
import net.badata.protobuf.converter.resolver.AnnotatedFieldResolverFactoryImpl;
import net.badata.protobuf.converter.resolver.FieldResolverFactory;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation that marks domain class that may be converted to Protobuf messages.
 *
 * @author jsjem
 */
@Target(value = ElementType.TYPE)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface ProtoClass {

	/**
	 * Retrieve related protobuf class.
	 *
	 * @return Class that represents protobuf dto.
	 */
	Class<? extends Message> value();

	/**
	 * Retrieve class that perform data mapping between domain and protobuf instances.
	 *
	 * @return Class for mapping data.
	 */
	Class<? extends Mapper> mapper() default DefaultMapperImpl.class;

	/**
	 * Retrieve factory class that creates resolvers for domain class fields.
	 *
	 * @return implementation of {@link net.badata.protobuf.converter.resolver.FieldResolverFactory
	 * FieldResolverFactory}
	 */
	Class<? extends FieldResolverFactory> fieldFactory() default AnnotatedFieldResolverFactoryImpl.class;
}
