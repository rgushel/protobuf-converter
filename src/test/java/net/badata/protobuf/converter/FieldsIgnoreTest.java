package net.badata.protobuf.converter;

import net.badata.protobuf.converter.annotation.ProtoField;
import net.badata.protobuf.converter.utils.FieldUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;

import static net.badata.protobuf.converter.domain.IgnoreDomain.IgnoreDataTest;
import static net.badata.protobuf.converter.domain.IgnoreDomain.NoIgnoreDataTest;

/**
 * @author jsjem
 * @author Roman Gushel
 */
public class FieldsIgnoreTest {

	private FieldsIgnore fieldsIgnore;

	@Before
	public void setUp() {
		fieldsIgnore = new FieldsIgnore();
	}

	@After
	public void tearDown() {
		fieldsIgnore.clear();
	}

	@Test
	public void testFieldIgnore() throws NoSuchFieldException {
		fieldsIgnore.clear();

		Assert.assertFalse(fieldsIgnore.ignored(IgnoreDataTest.class.getField("fieldName")));
		Assert.assertFalse(fieldsIgnore.ignored(IgnoreDataTest.class.getField("protoFieldName")));

		fieldsIgnore.add(IgnoreDataTest.class, "fieldName", "protofield", "notProtoField");

		Assert.assertTrue(fieldsIgnore.ignored(IgnoreDataTest.class.getField("fieldName")));
		Assert.assertTrue(fieldsIgnore.ignored(IgnoreDataTest.class.getField("protoFieldName")));
		Assert.assertTrue(fieldsIgnore.ignored(IgnoreDataTest.class.getField("notProtoField")));

		Assert.assertFalse(fieldsIgnore.ignored(IgnoreDataTest.class.getField("notIgnored")));
		Assert.assertFalse(fieldsIgnore.ignored(NoIgnoreDataTest.class.getField("fieldName")));

		fieldsIgnore.remove(IgnoreDataTest.class, "fieldName", "protofield", "notProtoField");

		Assert.assertFalse(fieldsIgnore.ignored(IgnoreDataTest.class.getField("fieldName")));
		Assert.assertFalse(fieldsIgnore.ignored(IgnoreDataTest.class.getField("protoFieldName")));

		fieldsIgnore.add(IgnoreDataTest.class, (String)null);

		Assert.assertFalse(fieldsIgnore.ignored(IgnoreDataTest.class.getField("fieldName")));

		fieldsIgnore.add(IgnoreDataTest.class, (String[]) null);

		Assert.assertFalse(fieldsIgnore.ignored(IgnoreDataTest.class.getField("fieldName")));
	}


	@Test
	public void testClassIgnore() {
		fieldsIgnore.clear();
		fieldsIgnore.add(IgnoreDataTest.class);
		for (Field field : IgnoreDataTest.class.getDeclaredFields()) {
			Assert.assertTrue(fieldsIgnore.ignored(field));
		}
		for (Field field : NoIgnoreDataTest.class.getDeclaredFields()) {
			Class<?> verifiedClass = FieldUtils.isCollectionType(field) ? FieldUtils.extractCollectionType(field) :
					field.getType();
			if (field.isAnnotationPresent(ProtoField.class) && !verifiedClass.equals(IgnoreDataTest.class)) {
				Assert.assertFalse(fieldsIgnore.ignored(field));
			} else {
				Assert.assertTrue(fieldsIgnore.ignored(field));
			}
		}
	}

	@Test
	public void testFieldThenClassIgnore() throws NoSuchFieldException {
		fieldsIgnore.clear();

		fieldsIgnore.add(IgnoreDataTest.class, "fieldName");

		Assert.assertTrue(fieldsIgnore.ignored(IgnoreDataTest.class.getField("fieldName")));
		Assert.assertFalse(fieldsIgnore.ignored(IgnoreDataTest.class.getField("protoFieldName")));

		fieldsIgnore.add(IgnoreDataTest.class);

		Assert.assertTrue(fieldsIgnore.ignored(IgnoreDataTest.class.getField("fieldName")));
		Assert.assertTrue(fieldsIgnore.ignored(IgnoreDataTest.class.getField("protoFieldName")));
	}

}
