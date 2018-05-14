package net.badata.protobuf.converter.utils;

/**
 * @author jsjem
 * @author Roman Gushel
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
