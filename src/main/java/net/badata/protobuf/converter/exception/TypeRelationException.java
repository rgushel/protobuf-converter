package net.badata.protobuf.converter.exception;

import com.google.protobuf.Message;

/**
 * Exception notifies that protobuf class specified in the {@link net.badata.protobuf.converter.annotation.ProtoClass
 * ProtoClass} is different form the required protobuf class.
 *
 * @author jsjem
 * @author Roman Gushel
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
