package net.badata.protobuf.converter;

import net.badata.protobuf.converter.annotation.ProtoField;
import net.badata.protobuf.converter.utils.FieldUtils;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Stores fields and classes that have to be ignored during conversion.
 *
 * @author jsjem
 */
public class FieldsIgnore {

	private final Map<Class<?>, Set<String>> ignoreMapping = new HashMap<Class<?>, Set<String>>();

	/**
	 * Add class to ignore map. Method {@link #ignored(Field) ignored()} return true if field type is similar to
	 * ignoreClass or field owner class is ignoreClass.
	 *
	 * @param ignoredClass Class for ignore.
	 * @return Instance of IgnoredFieldsMap.
	 */
	public FieldsIgnore add(final Class<?> ignoredClass) {
		ignoreMapping.put(ignoredClass, Collections.<String>emptySet());
		return this;
	}

	/**
	 * Add class field to ignore map.
	 *
	 * @param ignoredClass Owner of the ignored field.
	 * @param ignoredField Field for ignore.
	 * @return Instance of IgnoredFieldsMap.
	 */
	public FieldsIgnore add(final Class<?> ignoredClass, final String ignoredField) {
		Set<String> ignoredFields = ignoreMapping.get(ignoredClass);
		if (ignoredFields == null || ignoredFields.isEmpty()) {
			ignoredFields = new HashSet<String>();
			ignoreMapping.put(ignoredClass, ignoredFields);
		}
		ignoredFields.add(ignoredField);
		return this;
	}

	/**
	 * Remove class field from ignore map.
	 *
	 * @param ignoredClass Owner of the ignored field.
	 * @param ignoredField Field for ignore.
	 * @return Instance of IgnoredFieldsMap.
	 */
	public FieldsIgnore remove(final Class<?> ignoredClass, final String ignoredField) {
		Set<String> ignoredFields = ignoreMapping.get(ignoredClass);
		if (ignoredFields != null && !ignoredFields.isEmpty()) {
			ignoredFields.remove(ignoredField);
		}
		return this;
	}

	/**
	 * Remove class from ignore map. Class and all its fields will not be ignored any more.
	 *
	 * @param ignoredClass Class to remove from ignore map.
	 * @return Instance of IgnoredFieldsMap.
	 */
	public FieldsIgnore remove(final Class<?> ignoredClass) {
		ignoreMapping.remove(ignoredClass);
		return this;
	}

	/**
	 * Clear ignore map.
	 */
	public void clear() {
		ignoreMapping.clear();
	}

	/**
	 * Check whether field has to be ignored. A
	 *
	 * @param field Field instance for test.
	 * @return true if field class or field name in the ignore or field is not annotated by
	 * {@link net.badata.protobuf.converter.annotation.ProtoField ProtoField}.
	 */
	public boolean ignored(final Field field) {
		return !field.isAnnotationPresent(ProtoField.class) || isClassIgnored(field) || isFieldIgnored(field);
	}

	private boolean isClassIgnored(final Field field) {
		Class<?> verifiedClass = FieldUtils.isCollectionType(field) ? FieldUtils.extractCollectionType(field) :
				field.getType();
		Set<String> ignoredFields = ignoreMapping.get(verifiedClass);
		return ignoredFields != null && ignoredFields.isEmpty();
	}

	private boolean isFieldIgnored(final Field field) {
		Set<String> ignoredFields = ignoreMapping.get(field.getDeclaringClass());
		if (ignoredFields != null) {
			ProtoField annotation = field.getAnnotation(ProtoField.class);
			return ignoredFields.isEmpty() || ignoredFields.contains(field.getName())
					|| ignoredFields.contains(annotation.name());
		}
		return false;
	}
}
