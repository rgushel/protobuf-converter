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

package net.badata.protobuf.converter.utils;

/**
 * Created by jsjem on 25.04.2016.
 */
public final class StringUtils {

	/**
	 * <p>Capitalizes a String changing the first letter to title case as
	 * per {@link Character#toTitleCase(char)}. No other letters are changed.</p>
	 *
	 * <p>For a word based algorithm, see {org.apache.commons.lang3.text.WordUtils#capitalize(String)}.
	 * A {@code null} input String returns {@code null}.</p>
	 *
	 * <pre>
	 * StringUtils.capitalize(null)  = null
	 * StringUtils.capitalize("")    = ""
	 * StringUtils.capitalize("cat") = "Cat"
	 * StringUtils.capitalize("cAt") = "CAt"
	 * </pre>
	 *
	 * @param str the String to capitalize, may be null
	 * @return the capitalized String, {@code null} if null String input
	 */
	public static String capitalize(final String str) {
		int strLen;
		if (str == null || (strLen = str.length()) == 0) {
			return str;
		}
		final char firstChar = str.charAt(0);
		if (Character.isTitleCase(firstChar)) {
			return str;
		}
		return new StringBuilder(strLen)
				.append(Character.toTitleCase(firstChar))
				.append(str.substring(1))
				.toString();
	}

	/**
	 * Create access method name for field.
	 *
	 * @param prefix    method prefix that starts with lower case letter.
	 * @param fieldName field name.
	 * @return method name according to JLS.
	 */
	public static String createMethodName(final String prefix, final String fieldName) {
		return prefix + StringUtils.capitalize(fieldName);
	}

	private StringUtils() {
		// empty
	}
}
