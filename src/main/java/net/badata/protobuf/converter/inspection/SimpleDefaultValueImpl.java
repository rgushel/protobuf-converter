package net.badata.protobuf.converter.inspection;

/**
 * Implementation of {@link net.badata.protobuf.converter.inspection.DefaultValue DefaultValue} that is
 * applied by default.
 *
 * @author jsjem
 * @author Roman Gushel
 */
public class SimpleDefaultValueImpl implements DefaultValue {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object generateValue(final Class<?> type) {
		if (type.isAssignableFrom(byte.class)) {
			return (byte) 0;
		}
		if (type.isAssignableFrom(short.class)) {
			return (short) 0;
		}
		if (type.isAssignableFrom(int.class)) {
			return 0;
		}
		if (type.isAssignableFrom(long.class)) {
			return 0L;
		}
		if (type.isAssignableFrom(float.class)) {
			return 0.0f;
		}
		if (type.isAssignableFrom(double.class)) {
			return 0.0;
		}
		if (type.isAssignableFrom(boolean.class)) {
			return false;
		}
		return null;
	}
}
