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
 * @author Roman Gushel
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
