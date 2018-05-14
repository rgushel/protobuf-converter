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
 * @author Roman Gushel
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
