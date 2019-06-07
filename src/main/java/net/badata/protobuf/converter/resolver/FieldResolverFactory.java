package net.badata.protobuf.converter.resolver;

import java.lang.reflect.Field;

/**
 * Factory interface for creating field resolvers.
 *
 * @author jsjem
 * @author Roman Gushel
 */
public interface FieldResolverFactory {

	/**
	 * Create field resolver.
	 *
	 * @param field Domain class field.
     * @param domainObject Domain enclosing object.
	 * @return instance of {@link net.badata.protobuf.converter.resolver.FieldResolver FieldResolver}
	 */
	FieldResolver createResolver(final Field field, final Object domainObject);
}
