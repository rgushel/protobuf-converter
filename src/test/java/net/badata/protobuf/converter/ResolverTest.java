package net.badata.protobuf.converter;

import net.badata.protobuf.converter.domain.ResolverDomain;
import net.badata.protobuf.converter.proto.ResolverProto;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * @author jsjem
 * @author Roman Gushel
 */
public class ResolverTest {
	private static final String DELIMITED_STRING = "one,two,three";
	private static final List<String> STRING_LIST = Arrays.asList("four", "five", "six");

	private ResolverDomain.Test testDomain;
	private ResolverProto.ResolverTest testProtobuf;

	@Before
	public void setUp() {
		createTestDomain();
		createTestProtobuf();
	}

	private void createTestDomain() {
		testDomain = new ResolverDomain.Test();
		testDomain.setCommaDelimitedStringValue(DELIMITED_STRING);
		testDomain.setStringList(STRING_LIST);
	}

	private void createTestProtobuf() {
		testProtobuf = ResolverProto.ResolverTest.newBuilder()
				.addAllStringListValue(STRING_LIST)
				.setDelimitedStringValue(DELIMITED_STRING)
				.build();
	}

	@Test
	public void testDomainToProtobuf() {
		ResolverProto.ResolverTest result = Converter.create().toProtobuf(ResolverProto.ResolverTest.class,
				testDomain);

		Assert.assertNotNull(result);
		Assert.assertEquals(testDomain.getCommaDelimitedStringValue(),
				String.join(",", result.getStringListValueList()));
		Assert.assertEquals(testDomain.getStringList(), Arrays.asList(result.getDelimitedStringValue().split(",")));
	}


	@Test
	public void testProtobufToDomain() {
		ResolverDomain.Test result = Converter.create().toDomain(ResolverDomain.Test.class, testProtobuf);

		Assert.assertNotNull(result);
		Assert.assertEquals(testProtobuf.getStringListValueList(),
				Arrays.asList(result.getCommaDelimitedStringValue().split(",")));
		Assert.assertEquals(testProtobuf.getDelimitedStringValue(), String.join(",", result.getStringList()));
	}
}
