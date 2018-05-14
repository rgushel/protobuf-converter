package net.badata.protobuf.converter.inspection;

/**
 * Interface for creating default values for domain fields.
 *
 * @author jsjem
 * @author Roman Gushel
 */
public interface DefaultValue {

	/**
	 * Create default value.
	 *
	 * @param type Field type.
	 * @return Object that will be used as default value.
	 */
	Object generateValue(final Class<?> type);
}
