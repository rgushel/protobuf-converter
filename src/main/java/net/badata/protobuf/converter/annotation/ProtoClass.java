package net.badata.protobuf.converter.annotation;

import com.google.protobuf.Message;
import net.badata.protobuf.converter.mapping.DefaultMapperImpl;
import net.badata.protobuf.converter.mapping.Mapper;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation that marks domain classes that may be converted to Protobuf messages.
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
}
