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

package net.badata.protobuf.converter.mapping;

import com.google.protobuf.Message;
import net.badata.protobuf.converter.exception.MappingException;
import net.badata.protobuf.converter.resolver.FieldResolver;

/**
 * Interface for mapping field values between domain and protobuf objects.
 *
 * @author jsjem
 */
public interface Mapper {

	/**
	 * Maps data from Protobuf field to domain field.
	 *
	 * @param fieldResolver Field resolver for which mapping is performed.
	 * @param protobuf      Protobuf dto instance that holds data.
	 * @param domain        Domain object waiting for data mapping.
	 * @param <T>           Type of Protobuf dto.
	 * @return Result of the data mapping from Protobuf dto to domain object.
	 * @throws net.badata.protobuf.converter.exception.MappingException throws when Protobuf dto field data access
	 *                                                                  problems is occurred.
	 */
	<T extends Message> MappingResult mapToDomainField(final FieldResolver fieldResolver, final T protobuf,
			final Object domain) throws MappingException;

	/**
	 * Maps data from domain field to Protobuf field.
	 *
	 * @param fieldResolver   Field resolver for which mapping is performed.
	 * @param domain          Domain object that holds data.
	 * @param protobufBuilder Builder for Protobuf dto that waiting for data mapping.
	 * @param <T>             Type of Protobuf dto.
	 * @return Result of the data mapping from domain object to Protobuf dto.
	 * @throws MappingException throws when domain object field data access problems is occurred.
	 */
	<T extends Message.Builder> MappingResult mapToProtobufField(final FieldResolver fieldResolver,
			final Object domain, final T protobufBuilder) throws MappingException;
}
