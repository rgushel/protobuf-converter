package net.badata.protobuf.converter.inspection;

/**
 * Interface for examine protobuf fields for null(default) values.
 *
 * @author jsjem
 * @author Roman Gushel
 */
public interface NullValueInspector {

	/**
	 * Check whether some value satisfy conditions held by this inspector.
	 *
	 * @param value Value for test.
	 * @return True if inspection is successful.
	 */
	boolean isNull(final Object value);
}
