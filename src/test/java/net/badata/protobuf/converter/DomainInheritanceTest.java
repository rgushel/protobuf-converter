package net.badata.protobuf.converter;

import net.badata.protobuf.converter.domain.DomainInheritanceDomain;
import net.badata.protobuf.converter.proto.DomainInheritanceProto;
import org.junit.Before;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * @author jsjem
 * @author Roman Gushel
 */
public class DomainInheritanceTest {

	private DomainInheritanceDomain.Test testDomain;
	private DomainInheritanceProto.Test testProtobuf;

	@Before
	public void setUp() throws Exception {
		createTestProtobuf();
		createTestDomain();
	}

	private void createTestProtobuf() {
		testProtobuf = DomainInheritanceProto.Test.newBuilder()
				.setInheritedInt(200)
				.setInheritedFloat(90f)
				.setOwnLong(987654L)
				.setOwnDouble(0.653)
				.build();
	}

	private void createTestDomain() {
		testDomain = new DomainInheritanceDomain.Test();
		testDomain.setInheritedInt(100);
		testDomain.setInheritedFloat(40f);
		testDomain.setOwnLong(456789L);
		testDomain.setOwnDouble(0.356);
	}

	@org.junit.Test
	public void testAllDomainFieldsToProtobuf() {
		Configuration configuration = Configuration.builder().withInheritedFields().build();
		DomainInheritanceProto.Test proto = Converter.create(configuration)
				.toProtobuf(DomainInheritanceProto.Test.class, testDomain);

		assertEquals(testDomain.getInheritedInt(), proto.getInheritedInt());
		assertEquals(testDomain.getInheritedFloat(), proto.getInheritedFloat(), 0f);
		assertEquals(testDomain.getOwnLong(), (Long)proto.getOwnLong());
		assertEquals(testDomain.getOwnDouble(), proto.getOwnDouble(), 0.001);

	}

	@org.junit.Test
	public void testOwnDomainFieldsToProtobuf() {
		DomainInheritanceProto.Test proto = Converter.create()
				.toProtobuf(DomainInheritanceProto.Test.class, testDomain);

		assertEquals(0, proto.getInheritedInt());
		assertEquals(0f, proto.getInheritedFloat(), 0f);
		assertEquals(testDomain.getOwnLong(), (Long)proto.getOwnLong());
		assertEquals(testDomain.getOwnDouble(), proto.getOwnDouble(), 0.001);

	}

	@org.junit.Test
	public void testProtobufToAllDomainFields() {
		Configuration configuration = Configuration.builder().withInheritedFields().build();
		DomainInheritanceDomain.Test domain = Converter.create(configuration).toDomain(DomainInheritanceDomain.Test.class, testProtobuf);

		assertEquals(testProtobuf.getInheritedInt(), domain.getInheritedInt());
		assertEquals(testProtobuf.getInheritedFloat(), domain.getInheritedFloat(), 0f);
		assertEquals((Long)testProtobuf.getOwnLong(), domain.getOwnLong());
		assertEquals(testProtobuf.getOwnDouble(), domain.getOwnDouble(), 0.001);

	}

	@org.junit.Test
	public void testProtobufToOwnDomainFields() {
		DomainInheritanceDomain.Test domain = Converter.create().toDomain(DomainInheritanceDomain.Test.class, testProtobuf);

		assertEquals(0, domain.getInheritedInt());
		assertNull(domain.getInheritedFloat());
		assertEquals((Long)testProtobuf.getOwnLong(), domain.getOwnLong());
		assertEquals(testProtobuf.getOwnDouble(), domain.getOwnDouble(), 0.001);

	}
}
