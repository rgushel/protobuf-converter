package net.badata.protobuf.converter;

import com.google.protobuf.ByteString;
import net.badata.protobuf.converter.domain.ConverterDomain;
import net.badata.protobuf.converter.proto.ConverterProto;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static net.badata.protobuf.converter.domain.ConverterDomain.TestEnumConverter.TestEnum;

/**
 * @author jsjem
 * @author Roman Gushel
 */
public class ConverterTest {

	private ConverterDomain.Test testDomain;
	private ConverterProto.ConverterTest testProtobuf;

	private FieldsIgnore fieldsIgnore;
	private ConverterDomain.DomainConstructorTest domainConstructorTest;
	private ConverterProto.DomainConstructorTest domainConstructorTestProto;

	@Before
	public void setUp() throws Exception {
		createTestProtobuf();
		createTestDomain();
		createIgnoredFieldsMap();
	}

	private void createTestProtobuf() {
		testProtobuf = ConverterProto.ConverterTest.newBuilder()
				.setBooleanValue(false)
				.setFloatValue(0.1f)
				.setDoubleValue(0.5)
				.setIntValue(1)
				.setLongValue(2L)
				.setStringValue("3")
				.setPrimitiveValue(ConverterProto.PrimitiveTest.newBuilder()
						.setBooleanValue(true)
						.setFloatValue(-0.1f)
						.setDoubleValue(-0.5)
						.setIntValue(-1)
						.setLongValue(-2L))
				.setFieldConversionValue(ConverterProto.FieldConverterTest.newBuilder()
						.setEnumString("THREE")
						.setDateLong(System.currentTimeMillis())
						.addStringSetValue("11"))
				.setNullDefaultValue(ConverterProto.NullDefaultTest.newBuilder()
								.setCustomInspectionString("Assumed as null value")
								.setDefaultPrimitives(ConverterProto.PrimitiveTest.newBuilder())
				)
				.addStringListValue("10")
				.addComplexListValue(ConverterProto.PrimitiveTest.newBuilder().setIntValue(1001))
				.addComplexSetValue(ConverterProto.PrimitiveTest.newBuilder().setIntValue(1002))
				.setBytesValue(ByteString.copyFrom(new byte[]{ 0, 1, 3, 7 }))
				.setRecursiveValue(ConverterProto.ConverterTest.newBuilder().setIntValue(1))
				.build();

		domainConstructorTestProto = ConverterProto.DomainConstructorTest.newBuilder()
				.setMsg("Hello Proto")
				.build();
	}

	private void createTestDomain() {
		ConverterDomain.PrimitiveTest primitiveTest = new ConverterDomain.PrimitiveTest();
		primitiveTest.setBooleanValue(true);
		primitiveTest.setFloatValue(-0.2f);
		primitiveTest.setDoubleValue(-0.6);
		primitiveTest.setIntValue(-101);
		primitiveTest.setLongValue(-102L);

		ConverterDomain.FieldConverterTest fieldConverterTest = new ConverterDomain.FieldConverterTest();
		fieldConverterTest.setEnumString(TestEnum.TWO);
		fieldConverterTest.setDateLong(new Date());
		Set<String> stringSet = new HashSet<String>();
		stringSet.add("111");
		fieldConverterTest.setStringSetValue(stringSet);

		testDomain = new ConverterDomain.Test();
		testDomain.setBoolValue(false);
		testDomain.setFloatValue(0.2f);
		testDomain.setDoubleValue(0.6);
		testDomain.setIntValue(101);
		testDomain.setLongValue(102L);
		testDomain.setStringValue("103");
		testDomain.setPrimitiveValue(primitiveTest);
		testDomain.setFieldConversionValue(fieldConverterTest);
		testDomain.setSimpleListValue(Arrays.asList("110"));

		domainConstructorTest = new ConverterDomain.DomainConstructorTest();
		domainConstructorTest.setMsg("Hello Domain Object");

		ConverterDomain.PrimitiveTest primitiveTestItem = new ConverterDomain.PrimitiveTest();
		primitiveTestItem.setIntValue(-1001);
		testDomain.setComplexListValue(Arrays.asList(primitiveTestItem));
		ConverterDomain.PrimitiveTest primitiveTestSItem = new ConverterDomain.PrimitiveTest();
		primitiveTestItem.setIntValue(-1002);
		testDomain.setComplexSetValue(new HashSet<ConverterDomain.PrimitiveTest>(Arrays.asList(primitiveTestSItem)));
		testDomain.setComplexNullableCollectionValue(null);

		testDomain.setBytesValue(ByteString.copyFrom(new byte[]{ 0, 1, 3, 7 }));

		ConverterDomain.Test nestedValue = new ConverterDomain.Test();
		nestedValue.setIntValue(1);
		testDomain.setRecursiveValue(nestedValue);
	}

	private void createIgnoredFieldsMap() {
		fieldsIgnore = new FieldsIgnore();
		fieldsIgnore.add(ConverterDomain.PrimitiveTest.class);
		fieldsIgnore.add(ConverterDomain.FieldConverterTest.class, "enumString");
		fieldsIgnore.add(ConverterDomain.Test.class, "boolValue");
	}


	@Test
	public void testProtobufToDomain() {
		ConverterDomain.Test result = Converter.create().toDomain(ConverterDomain.Test.class, testProtobuf);

		Assert.assertNotNull(result);

		Assert.assertEquals(testProtobuf.getBooleanValue(), result.getBoolValue());
		Assert.assertEquals((Object) testProtobuf.getFloatValue(), result.getFloatValue());
		Assert.assertEquals((Object) testProtobuf.getDoubleValue(), result.getDoubleValue());
		Assert.assertEquals((Object) testProtobuf.getIntValue(), result.getIntValue());
		Assert.assertEquals((Object) testProtobuf.getLongValue(), result.getLongValue());
		Assert.assertEquals(testProtobuf.getStringValue(), result.getStringValue());

		ConverterProto.PrimitiveTest primitiveProto = testProtobuf.getPrimitiveValue();
		ConverterDomain.PrimitiveTest primitiveDomain = result.getPrimitiveValue();

		Assert.assertEquals(primitiveProto.getLongValue(), primitiveDomain.getLongValue());
		Assert.assertEquals(primitiveProto.getIntValue(), primitiveDomain.getIntValue());
		Assert.assertEquals(primitiveProto.getFloatValue(), primitiveDomain.getFloatValue(), 0f);
		Assert.assertEquals(primitiveProto.getDoubleValue(), primitiveDomain.getDoubleValue(), 0);
		Assert.assertEquals(primitiveProto.getBooleanValue(), primitiveDomain.isBooleanValue());

		ConverterProto.FieldConverterTest conversionProto = testProtobuf.getFieldConversionValue();
		ConverterDomain.FieldConverterTest conversionDomain = result.getFieldConversionValue();

		Assert.assertEquals(conversionProto.getDateLong(), conversionDomain.getDateLong().getTime());
		Assert.assertEquals(conversionProto.getEnumString(), conversionDomain.getEnumString().name());
		Assert.assertTrue(conversionDomain.getStringSetValue().remove(conversionProto.getStringSetValue(0)));

		ConverterDomain.NullDefaultTest nullDefaultDomain = result.getNullDefaultValue();

		Assert.assertEquals(nullDefaultDomain.getCustomInspectionString(), new ConverterDomain.StringDefaultValue()
				.generateValue(null));
		Assert.assertNull(nullDefaultDomain.getDefaultPrimitives().getLongValue());
		Assert.assertNull(nullDefaultDomain.getDefaultPrimitives().getIntValue());
		Assert.assertNull(nullDefaultDomain.getDefaultPrimitives().getFloatValue());
		Assert.assertNull(nullDefaultDomain.getDefaultPrimitives().getDoubleValue());

		Assert.assertEquals(testProtobuf.getStringListValue(0), result.getSimpleListValue().get(0));
		Assert.assertEquals(testProtobuf.getComplexListValue(0).getIntValue(),
				result.getComplexListValue().get(0).getIntValue());
		Assert.assertEquals(testProtobuf.getComplexSetValue(0).getIntValue(),
				result.getComplexSetValue().iterator().next().getIntValue());

		Assert.assertTrue(result.getComplexNullableCollectionValue().isEmpty());

		Assert.assertEquals(testProtobuf.getBytesValue(), result.getBytesValue());
		Assert.assertEquals((Object) testProtobuf.getRecursiveValue().getIntValue(), result.getRecursiveValue().getIntValue());
	}

	@Test
	public void testFieldIgnoreProtobufToDomain() {
		Configuration configuration = Configuration.builder().addIgnoredFields(fieldsIgnore).build();
		ConverterDomain.Test result = Converter.create(configuration)
				.toDomain(ConverterDomain.Test.class, testProtobuf);

		Assert.assertNotNull(result);

		Assert.assertNull(result.getBoolValue());
		Assert.assertNull(result.getPrimitiveValue());
		Assert.assertNull(result.getFieldConversionValue().getEnumString());
		Assert.assertNull(result.getComplexListValue());
	}


	@Test
	public void testDomainToProtobuf() {
		ConverterProto.ConverterTest result = Converter.create().toProtobuf(ConverterProto.ConverterTest.class,
				testDomain);

		Assert.assertNotNull(result);

		Assert.assertEquals(testDomain.getBoolValue(), result.getBooleanValue());
		Assert.assertEquals(testDomain.getFloatValue(), (Object) result.getFloatValue());
		Assert.assertEquals(testDomain.getDoubleValue(), (Object) result.getDoubleValue());
		Assert.assertEquals(testDomain.getIntValue(), (Object) result.getIntValue());
		Assert.assertEquals(testDomain.getLongValue(), (Object) result.getLongValue());
		Assert.assertEquals(testDomain.getStringValue(), result.getStringValue());

		ConverterProto.PrimitiveTest primitiveProto = result.getPrimitiveValue();
		ConverterDomain.PrimitiveTest primitiveDomain = testDomain.getPrimitiveValue();

		Assert.assertEquals(primitiveDomain.getLongValue(), primitiveProto.getLongValue());
		Assert.assertEquals(primitiveDomain.getIntValue(), primitiveProto.getIntValue());
		Assert.assertEquals(primitiveDomain.getFloatValue(), primitiveProto.getFloatValue(), 0f);
		Assert.assertEquals(primitiveDomain.getDoubleValue(), primitiveProto.getDoubleValue(), 0);
		Assert.assertEquals(primitiveDomain.isBooleanValue(), primitiveProto.getBooleanValue());

		ConverterProto.FieldConverterTest conversionProto = result.getFieldConversionValue();
		ConverterDomain.FieldConverterTest conversionDomain = testDomain.getFieldConversionValue();

		Assert.assertEquals(conversionDomain.getDateLong().getTime(), conversionProto.getDateLong());
		Assert.assertEquals(conversionDomain.getEnumString().name(), conversionProto.getEnumString());
		Assert.assertTrue(conversionDomain.getStringSetValue().remove(conversionProto.getStringSetValue(0)));

		Assert.assertFalse(result.hasNullDefaultValue());

		Assert.assertEquals(testDomain.getSimpleListValue().get(0), result.getStringListValue(0));
		Assert.assertEquals(testDomain.getComplexListValue().get(0).getIntValue(),
				result.getComplexListValue(0).getIntValue());
		Assert.assertEquals(testDomain.getComplexSetValue().iterator().next().getIntValue(),
				result.getComplexSetValue(0).getIntValue());

		Assert.assertTrue(result.getComplexNullableCollectionValueList().isEmpty());
		Assert.assertEquals((Object) testDomain.getRecursiveValue().getIntValue(), result.getRecursiveValue().getIntValue());
	}

	@Test
	public void testFieldIgnoreDomainToProtobuf() {
		Configuration configuration = Configuration.builder().addIgnoredFields(fieldsIgnore).build();
		ConverterProto.ConverterTest result = Converter.create(configuration)
				.toProtobuf(ConverterProto.ConverterTest.class, testDomain);

		Assert.assertNotNull(result);

		Assert.assertFalse(result.getBooleanValue());
		Assert.assertFalse(result.hasPrimitiveValue());
		Assert.assertEquals("", result.getFieldConversionValue().getEnumString());
		Assert.assertTrue(result.getComplexListValueList().isEmpty());
	}

	@Test
	public void testDomainObjectConstructor() {
		ConverterDomain.DomainConstructorTest domainConstructorTestResult = Converter.create().toDomain(ConverterDomain.DomainConstructorTest.class, domainConstructorTestProto);

		Assert.assertEquals("Hello Proto", domainConstructorTestResult.getMsg());
		Assert.assertEquals("toDomainValue!", domainConstructorTestResult.getMsg2());

		Assert.assertEquals("initialValue", domainConstructorTest.getMsg2());
		ConverterProto.DomainConstructorTest domainConstructorTestProtoResult = Converter.create().toProtobuf(ConverterProto.DomainConstructorTest.class, domainConstructorTest);

		Assert.assertEquals("toProtobufValue!", domainConstructorTest.getMsg2());
		Assert.assertEquals("Hello Domain Object", domainConstructorTestProtoResult.getMsg());
	}
}
