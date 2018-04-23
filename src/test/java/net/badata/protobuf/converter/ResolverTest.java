/*
 * Copyright (C) 2017  BAData Creative Studio
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

package net.badata.protobuf.converter;

import net.badata.protobuf.converter.domain.ResolverDomain;
import net.badata.protobuf.converter.proto.ResolverProto;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Created by jsjem on 10.03.2017.
 *
 * @author jsjem
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
