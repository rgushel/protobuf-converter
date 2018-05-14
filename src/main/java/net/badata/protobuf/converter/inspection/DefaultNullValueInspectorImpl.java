package net.badata.protobuf.converter.inspection;

/**
 * Implementation of {@link net.badata.protobuf.converter.inspection.NullValueInspector NullValueInspector} that is
 * applied by default.
 *
 * @author jsjem
 * @author Roman Gushel
 */
public final class DefaultNullValueInspectorImpl implements NullValueInspector {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isNull(final Object value) {
		if (value instanceof Byte) {
			return (Byte) value == 0;
		}
		if (value instanceof Short) {
			return (Short) value == 0;
		}
		if (value instanceof Integer) {
			return (Integer) value == 0;
		}
		if (value instanceof Long) {
			return (Long) value == 0L;
		}
		if (value instanceof Float) {
			return (Float) value == 0.0f;
		}
		if (value instanceof Double) {
			return (Double) value == 0.0;
		}
		if (value instanceof String) {
			return ((String) value).isEmpty();
		}
		return value == null;
	}
}
