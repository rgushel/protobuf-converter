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

package net.badata.protobuf.converter.inspection;

/**
 * Implementation of {@link net.badata.protobuf.converter.inspection.NullValueInspector NullValueInspector} that is
 * applied by default.
 *
 * @author jsjem
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
