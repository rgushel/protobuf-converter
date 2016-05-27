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
 * Implementation of {@link net.badata.protobuf.converter.inspection.DefaultValue DefaultValue} that is
 * applied by default.
 *
 * @author jsjem
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
