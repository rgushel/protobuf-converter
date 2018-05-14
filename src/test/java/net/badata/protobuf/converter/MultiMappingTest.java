package net.badata.protobuf.converter;

import net.badata.protobuf.converter.domain.MultiMappingDomain;
import net.badata.protobuf.converter.proto.MultiMappingProto;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

/**
 * @author jsjem
 * @author Roman Gushel
 */
public class MultiMappingTest {

	private MultiMappingDomain.MultiMappingOwner testDomain;
	private MultiMappingProto.MultiMappingTest testProtobuf;


	@Before
	public void setUp() throws Exception {
		createTestProtobuf();
		createTestDomain();
	}

	private void createTestProtobuf() {
		testProtobuf = MultiMappingProto.MultiMappingTest.newBuilder()
				.setMultiMappingValue(MultiMappingProto.MultiMappingFirst.newBuilder()
						.setIntValue(13)
						.setLongValue(14))
				.addMultiMappingListValue(MultiMappingProto.MultiMappingSecond.newBuilder()
						.setIntValue(16)
						.setLongValueChanged(17)
						.setUnusableValue("Some string"))
				.build();
	}

	private void createTestDomain() {
		testDomain = new MultiMappingDomain.MultiMappingOwner();
		MultiMappingDomain.MultiMappingChild child = new MultiMappingDomain.MultiMappingChild();
		child.setIntValue(31);
		child.setLongValue(41);
		testDomain.setMultiMappingValue(child);
		MultiMappingDomain.MultiMappingChild listChild = new MultiMappingDomain.MultiMappingChild();
		listChild.setIntValue(61);
		listChild.setLongValue(71);
		testDomain.setMultiMappingListValue(Collections.singletonList(listChild));
	}

	@Test
	public void testMultiMapping() {
		Converter converter = Converter.create();
		MultiMappingProto.MultiMappingTest protobufResult = converter
				.toProtobuf(MultiMappingProto.MultiMappingTest.class, testDomain);

		Assert.assertNotNull(protobufResult);

		MultiMappingProto.MultiMappingFirst multiMappingFirst = protobufResult.getMultiMappingValue();
		MultiMappingProto.MultiMappingSecond multiMappingSecond = protobufResult.getMultiMappingListValue(0);

		Assert.assertEquals(testDomain.getMultiMappingValue().getIntValue(), (Object) multiMappingFirst.getIntValue());
		Assert.assertEquals(testDomain.getMultiMappingValue().getLongValue(),
				(Object) multiMappingFirst.getLongValue());

		Assert.assertEquals(testDomain.getMultiMappingListValue().get(0).getIntValue(),
				(Object) multiMappingSecond.getIntValue());
		Assert.assertEquals(testDomain.getMultiMappingListValue().get(0).getLongValue(), (Object) multiMappingSecond
				.getLongValueChanged());
		Assert.assertEquals("", multiMappingSecond.getUnusableValue());

		MultiMappingDomain.MultiMappingOwner domainResult = converter
				.toDomain(MultiMappingDomain.MultiMappingOwner.class, testProtobuf);

		Assert.assertNotNull(domainResult);

		MultiMappingDomain.MultiMappingChild child = domainResult.getMultiMappingValue();
		MultiMappingDomain.MultiMappingChild listChild = domainResult.getMultiMappingListValue().get(0);

		Assert.assertEquals((Object) testProtobuf.getMultiMappingValue().getIntValue(), child.getIntValue());
		Assert.assertEquals((Object) testProtobuf.getMultiMappingValue().getLongValue(), child.getLongValue());
		Assert.assertEquals((Object) testProtobuf.getMultiMappingListValue(0).getIntValue(), listChild.getIntValue());
		Assert.assertEquals((Object) testProtobuf.getMultiMappingListValue(0).getLongValueChanged(),
				listChild.getLongValue());
	}


}
