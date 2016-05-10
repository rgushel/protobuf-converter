package net.badata.protobuf.example.client.domain;

import com.google.protobuf.Message;
import net.badata.protobuf.converter.exception.MappingException;
import net.badata.protobuf.converter.mapping.DefaultMapperImpl;
import net.badata.protobuf.converter.mapping.MappingResult;
import net.badata.protobuf.example.proto.Book;

import java.lang.reflect.Field;

/**
 * Created by jsjem on 05.05.2016.
 *
 * @author jsjem
 */
public class BookMapper extends DefaultMapperImpl {

	private static final String FIELD_OWNER_NAME = "ownerName";

	@Override
	public <T extends Message> MappingResult mapToDomainField(final Field field, final T protobuf,
			final Object domain) throws MappingException {
		if (isField(field, FIELD_OWNER_NAME)) {
			Book book = (Book) protobuf;
			return new MappingResult(MappingResult.Result.MAPPED, book.getOwner().getName(), domain);
		}
		return super.mapToDomainField(field, protobuf, domain);
	}

	private boolean isField(final Field field, final String fieldName) {
		return fieldName.equals(field.getName());
	}

	@Override
	public <T extends Message.Builder> MappingResult mapToProtobufField(final Field field, final Object domain,
			final T protobufBuilder) throws MappingException {
		if (isField(field, FIELD_OWNER_NAME)) {
			Book.Builder bookBuilder = (Book.Builder) protobufBuilder;
			ReadingBook bookDomain = (ReadingBook) domain;
			return new MappingResult(MappingResult.Result.MAPPED, bookDomain.getOwnerName(), bookBuilder.getOwnerBuilder());
		}
		return super.mapToProtobufField(field, domain, protobufBuilder);
	}
}
