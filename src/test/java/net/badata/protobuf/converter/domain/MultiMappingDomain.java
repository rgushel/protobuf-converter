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

package net.badata.protobuf.converter.domain;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import com.google.protobuf.Message;

import net.badata.protobuf.converter.annotation.ProtoClass;
import net.badata.protobuf.converter.annotation.ProtoClasses;
import net.badata.protobuf.converter.annotation.ProtoField;
import net.badata.protobuf.converter.exception.MappingException;
import net.badata.protobuf.converter.mapping.DefaultMapperImpl;
import net.badata.protobuf.converter.mapping.MappingResult;
import net.badata.protobuf.converter.proto.MultiMappingProto;
import net.badata.protobuf.converter.resolver.AnnotatedFieldResolverFactoryImpl;
import net.badata.protobuf.converter.resolver.DefaultFieldResolverImpl;
import net.badata.protobuf.converter.resolver.FieldResolver;

/**
 * Created by jsjem on 19.08.2016.
 *
 * @author jsjem
 */
public class MultiMappingDomain {

	@ProtoClass(MultiMappingProto.MultiMappingTest.class)
	public static class MultiMappingOwner {
		@ProtoField
		private MultiMappingChild multiMappingValue;
		@ProtoField
		private List<MultiMappingChild> multiMappingListValue;
		@ProtoField
		private Map<String, MultiMappingChild> multiMappingMapValue;

		public MultiMappingChild getMultiMappingValue() {
			return multiMappingValue;
		}

		public void setMultiMappingValue(final MultiMappingChild multiMappingValue) {
			this.multiMappingValue = multiMappingValue;
		}

		public List<MultiMappingChild> getMultiMappingListValue() {
			return multiMappingListValue;
		}

		public void setMultiMappingListValue(final List<MultiMappingChild> multiMappingListValue) {
			this.multiMappingListValue = multiMappingListValue;
		}

		public Map<String, MultiMappingChild> getMultiMappingMapValue() {
			return multiMappingMapValue;
		}

		public void setMultiMappingMapValue(Map<String, MultiMappingChild> multiMappingMapValue) {
			this.multiMappingMapValue = multiMappingMapValue;
		}
	}

	@ProtoClasses({@ProtoClass(MultiMappingProto.MultiMappingFirst.class),
						@ProtoClass(value = MultiMappingProto.MultiMappingSecond.class,
									mapper = MultiMappingMapperImpl.class,
									fieldFactory = FieldResolverFactoryImpl.class)})
	public static class MultiMappingChild {
		@ProtoField
		private int intValue;
		@ProtoField
		private long longValue;

		public int getIntValue() {
			return intValue;
		}

		public void setIntValue(final int intValue) {
			this.intValue = intValue;
		}

		public long getLongValue() {
			return longValue;
		}

		public void setLongValue(final long longValue) {
			this.longValue = longValue;
		}
	}

	public static class FieldResolverFactoryImpl extends AnnotatedFieldResolverFactoryImpl {

		public static final String FIELD_INT_VALUE = "intValue";
		public static final String FIELD_LONG_VALUE = "longValue";

		@Override
		public FieldResolver createResolver(final Field field) {
			if (FIELD_INT_VALUE.equals(field.getName())) {
				return super.createResolver(field);
			}
			if (FIELD_LONG_VALUE.equals(field.getName())) {
				DefaultFieldResolverImpl fieldResolver = (DefaultFieldResolverImpl) super.createResolver(field);
				fieldResolver.setProtobufName("longValueChanged");
				return fieldResolver;
			}
			return new DefaultFieldResolverImpl(field);
		}
	}

	public static class MultiMappingMapperImpl extends DefaultMapperImpl {

		@Override
		public <T extends Message.Builder> MappingResult mapToProtobufField(final FieldResolver fieldResolver,
				final Object domain, final T protobufBuilder) throws MappingException {
			if (FieldResolverFactoryImpl.FIELD_INT_VALUE.equals(fieldResolver.getDomainName()) ||
					FieldResolverFactoryImpl.FIELD_LONG_VALUE.equals(fieldResolver.getDomainName())) {
				return super.mapToProtobufField(fieldResolver, domain, protobufBuilder);
			}
			return new MappingResult(MappingResult.Result.MAPPED, null, protobufBuilder);
		}

		@Override
		public <T extends Message> MappingResult mapToDomainField(final FieldResolver fieldResolver, final T
				protobuf, final Object domain) throws MappingException {
			if (FieldResolverFactoryImpl.FIELD_INT_VALUE.equals(fieldResolver.getDomainName()) ||
					FieldResolverFactoryImpl.FIELD_LONG_VALUE.equals(fieldResolver.getDomainName())) {
				return super.mapToDomainField(fieldResolver, protobuf, domain);
			}
			return new MappingResult(MappingResult.Result.MAPPED, null, domain);
		}
	}
}
