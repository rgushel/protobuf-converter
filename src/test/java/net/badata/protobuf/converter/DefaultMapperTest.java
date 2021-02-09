package net.badata.protobuf.converter;

import net.badata.protobuf.converter.domain.MappingDomain;
import net.badata.protobuf.converter.exception.MappingException;
import net.badata.protobuf.converter.mapping.DefaultMapperImpl;
import net.badata.protobuf.converter.mapping.MappingResult;
import net.badata.protobuf.converter.proto.MappingProto;
import net.badata.protobuf.converter.resolver.AnnotatedFieldResolverFactoryImpl;
import net.badata.protobuf.converter.resolver.FieldResolver;
import net.badata.protobuf.converter.resolver.FieldResolverFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Arrays;

import static net.badata.protobuf.converter.mapping.MappingResult.Result;

/**
 * @author jsjem
 * @author Roman Gushel
 */
public class DefaultMapperTest {

	private DefaultMapperImpl mapper;
	private FieldResolverFactory fieldResolverFactory;
	private MappingDomain.Test testDomain;
	private MappingDomain.InaccessibleTest inaccessibleTestDomain;
	private MappingDomain.PrimitiveTest primitiveTestDomain;
	private MappingProto.MappingTest testProtobuf;

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Before
	public void setUp() throws Exception {
		mapper = new DefaultMapperImpl();
		fieldResolverFactory = new AnnotatedFieldResolverFactoryImpl();
		createTestProtobuf();
		createTestDomain();
		createPrimitiveTestDomain();
		inaccessibleTestDomain = new MappingDomain.InaccessibleTest();
	}

	@After
	public void tearDown() throws Exception {
		mapper = null;
		testProtobuf = null;
		testDomain = null;
		inaccessibleTestDomain = null;
		primitiveTestDomain = null;
	}

	private void createTestProtobuf() {
		testProtobuf =  MappingProto.MappingTest.newBuilder()
				.setBooleanValue(true)
				.setFloatValue(0.1f)
				.setDoubleValue(0.5)
				.setIntValue(1)
				.setLongValue(2L)
				.setStringValue("3")
				.setNestedValue(MappingProto.NestedTest.newBuilder().setStringValue("4"))
				.addStringListValue("10")
				.addNestedListValue(MappingProto.NestedTest.newBuilder().setStringValue("20"))
				.build();
	}

	private void createTestDomain() {
		testDomain = new MappingDomain.Test();
		testDomain.setBoolValue(false);
		testDomain.setFloatValue(0.2f);
		testDomain.setDoubleValue(0.6);
		testDomain.setIntValue(101);
		testDomain.setLongValue(102L);
		testDomain.setStringValue("103");
		MappingDomain.NestedTest nested = new MappingDomain.NestedTest();
		nested.setStringValue("104");
		testDomain.setNestedValue(nested);
		testDomain.setSimpleListValue(Arrays.asList("110"));
		MappingDomain.NestedTest nestedList = new MappingDomain.NestedTest();
		nested.setStringValue("120");
		testDomain.setNestedListValue(Arrays.asList(nestedList));
	}

	private void createPrimitiveTestDomain() {
		primitiveTestDomain = new MappingDomain.PrimitiveTest();
		primitiveTestDomain.setBooleanValue(true);
		primitiveTestDomain.setFloatValue(0.3f);
		primitiveTestDomain.setDoubleValue(0.7);
		primitiveTestDomain.setIntValue(201);
		primitiveTestDomain.setLongValue(202L);
	}

	@Test
	public void testMapObjectToDomain() throws MappingException {
		exception = ExpectedException.none();

		MappingResult result = mapper.mapToDomainField(findDomainField("floatValue"), testProtobuf, testDomain);
		testMappingResult(result, Result.MAPPED, testProtobuf.getFloatValue(), testDomain);

		result = mapper.mapToDomainField(findDomainField("doubleValue"), testProtobuf, testDomain);
		testMappingResult(result, Result.MAPPED, testProtobuf.getDoubleValue(), testDomain);

		result = mapper.mapToDomainField(findDomainField("intValue"), testProtobuf, testDomain);
		testMappingResult(result, Result.MAPPED, testProtobuf.getIntValue(), testDomain);

		result = mapper.mapToDomainField(findDomainField("longValue"), testProtobuf, testDomain);
		testMappingResult(result, Result.MAPPED, testProtobuf.getLongValue(), testDomain);

		result = mapper.mapToDomainField(findDomainField("stringValue"), testProtobuf, testDomain);
		testMappingResult(result, Result.MAPPED, testProtobuf.getStringValue(), testDomain);

	}

	private FieldResolver findDomainField(final String fieldName) {
		try {
			return fieldResolverFactory.createResolver(MappingDomain.Test.class.getDeclaredField(fieldName), null);
		} catch (NoSuchFieldException e) {
			throw new IllegalArgumentException("No such field: " + fieldName, e);
		}
	}

	private void testMappingResult(final MappingResult result, final MappingResult.Result code, final Object value,
			final Object destination) {
		Assert.assertEquals(code, result.getCode());
		Assert.assertEquals(value, result.getValue());
		Assert.assertEquals(destination, result.getDestination());
	}

	@Test
	public void testMapFieldWithDifferentNameToDomain() throws MappingException {
		exception = ExpectedException.none();
		MappingResult result = mapper.mapToDomainField(findDomainField("boolValue"), testProtobuf, testDomain);
		testMappingResult(result, Result.MAPPED, testProtobuf.getBooleanValue(), testDomain);
		result = mapper.mapToDomainField(findDomainField("simpleListValue"), testProtobuf, testDomain);
		testMappingResult(result, Result.COLLECTION_MAPPING, testProtobuf.getStringListValueList(), testDomain);
	}

	@Test
	public void testMapCollectionToDomain() throws MappingException {
		exception = ExpectedException.none();
		MappingResult result = mapper.mapToDomainField(findDomainField("simpleListValue"), testProtobuf, testDomain);
		testMappingResult(result, Result.COLLECTION_MAPPING, testProtobuf.getStringListValueList(), testDomain);

		result = mapper.mapToDomainField(findDomainField("nestedListValue"), testProtobuf, testDomain);
		testMappingResult(result, Result.COLLECTION_MAPPING, testProtobuf.getNestedListValueList(), testDomain);
	}

	@Test
	public void testMapNestedToDomain() throws MappingException {
		exception = ExpectedException.none();
		MappingResult result = mapper.mapToDomainField(findDomainField("nestedValue"), testProtobuf, testDomain);
		testMappingResult(result, Result.NESTED_MAPPING, testProtobuf.getNestedValue(), testDomain);
	}

	@Test
	public void testMapObjectToProtobuf() throws MappingException {
		exception = ExpectedException.none();
		MappingProto.MappingTest.Builder protobufBuilder = MappingProto.MappingTest.newBuilder();
		MappingResult result = mapper.mapToProtobufField(findDomainField("boolValue"), testDomain, protobufBuilder);
		testMappingResult(result, Result.MAPPED, testDomain.getBoolValue(), protobufBuilder);

		result = mapper.mapToProtobufField(findDomainField("floatValue"), testDomain, protobufBuilder);
		testMappingResult(result, Result.MAPPED, testDomain.getFloatValue(), protobufBuilder);

		result = mapper.mapToProtobufField(findDomainField("doubleValue"), testDomain, protobufBuilder);
		testMappingResult(result, Result.MAPPED, testDomain.getDoubleValue(), protobufBuilder);

		result = mapper.mapToProtobufField(findDomainField("intValue"), testDomain, protobufBuilder);
		testMappingResult(result, Result.MAPPED, testDomain.getIntValue(), protobufBuilder);

		result = mapper.mapToProtobufField(findDomainField("longValue"), testDomain, protobufBuilder);
		testMappingResult(result, Result.MAPPED, testDomain.getLongValue(), protobufBuilder);

		result = mapper.mapToProtobufField(findDomainField("stringValue"), testDomain, protobufBuilder);
		testMappingResult(result, Result.MAPPED, testDomain.getStringValue(), protobufBuilder);
	}


	@Test
	public void testMapCollectionToProtobuf() throws MappingException {
		exception = ExpectedException.none();
		MappingProto.MappingTest.Builder protobufBuilder = MappingProto.MappingTest.newBuilder();
		MappingResult result = mapper
				.mapToProtobufField(findDomainField("simpleListValue"), testDomain, protobufBuilder);
		testMappingResult(result, Result.COLLECTION_MAPPING, testDomain.getSimpleListValue(), protobufBuilder);

		result = mapper.mapToProtobufField(findDomainField("nestedListValue"), testDomain, protobufBuilder);
		testMappingResult(result, Result.COLLECTION_MAPPING, testDomain.getNestedListValue(), protobufBuilder);
	}

	@Test
	public void testMapNestedToProtobuf() throws MappingException {
		exception = ExpectedException.none();
		MappingProto.MappingTest.Builder protobufBuilder = MappingProto.MappingTest.newBuilder();
		MappingResult result = mapper.mapToProtobufField(findDomainField("nestedValue"), testDomain, protobufBuilder);
		testMappingResult(result, Result.NESTED_MAPPING, testDomain.getNestedValue(), protobufBuilder);
	}

	@Test
	public void testMapPrimitiveToProtobuf() throws MappingException {
		exception = ExpectedException.none();
		MappingProto.MappingTest.Builder protobufBuilder = MappingProto.MappingTest.newBuilder();
		MappingResult result = mapper
				.mapToProtobufField(findPrimitiveField("booleanValue"), primitiveTestDomain, protobufBuilder);
		testMappingResult(result, Result.MAPPED, primitiveTestDomain.isBooleanValue(), protobufBuilder);

		result = mapper.mapToProtobufField(findPrimitiveField("floatValue"), primitiveTestDomain, protobufBuilder);
		testMappingResult(result, Result.MAPPED, primitiveTestDomain.getFloatValue(), protobufBuilder);

		result = mapper.mapToProtobufField(findPrimitiveField("doubleValue"), primitiveTestDomain, protobufBuilder);
		testMappingResult(result, Result.MAPPED, primitiveTestDomain.getDoubleValue(), protobufBuilder);

		result = mapper.mapToProtobufField(findPrimitiveField("intValue"), primitiveTestDomain, protobufBuilder);
		testMappingResult(result, Result.MAPPED, primitiveTestDomain.getIntValue(), protobufBuilder);

		result = mapper.mapToProtobufField(findPrimitiveField("longValue"), primitiveTestDomain, protobufBuilder);
		testMappingResult(result, Result.MAPPED, primitiveTestDomain.getLongValue(), protobufBuilder);

	}

	private FieldResolver findPrimitiveField(final String fieldName) {
		try {
			return fieldResolverFactory.createResolver(MappingDomain.PrimitiveTest.class.getDeclaredField(fieldName), null);
		} catch (NoSuchFieldException e) {
			throw new IllegalArgumentException("No such field: " + fieldName, e);
		}
	}

	@Test
	public void testInaccessibleDomainFiled() throws MappingException {
		exception.expect(MappingException.class);
		mapper.mapToProtobufField(findInaccessibleField("inaccessibleField"), inaccessibleTestDomain,
				MappingProto.MappingTest.newBuilder());
	}


	private FieldResolver findInaccessibleField(final String fieldName) {
		try {
			return fieldResolverFactory.createResolver(MappingDomain.InaccessibleTest.class.getDeclaredField
					(fieldName), null);
		} catch (NoSuchFieldException e) {
			throw new IllegalArgumentException("No such field: " + fieldName, e);
		}
	}

	@Test
	public void testProtectedDomainGetter() throws MappingException {
		exception.expect(MappingException.class);
		mapper.mapToProtobufField(findInaccessibleField("protectedGetterField"), inaccessibleTestDomain,
				MappingProto.MappingTest.newBuilder());
	}
}
