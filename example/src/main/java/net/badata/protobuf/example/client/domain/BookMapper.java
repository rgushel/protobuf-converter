package net.badata.protobuf.example.client.domain;

import com.google.protobuf.Message;
import net.badata.protobuf.converter.exception.MappingException;
import net.badata.protobuf.converter.mapping.DefaultMapperImpl;
import net.badata.protobuf.converter.mapping.MappingResult;
import net.badata.protobuf.converter.resolver.FieldResolver;
import net.badata.protobuf.example.proto.Book;

/**
 * @author jsjem
 * @author Roman Gushel
 */
public class BookMapper extends DefaultMapperImpl {

	private static final String FIELD_OWNER_NAME = "ownerName";

	@Override
	public <T extends Message> MappingResult mapToDomainField(final FieldResolver fieldResolver, final T protobuf,
			final Object domain) throws MappingException {
		if (isField(fieldResolver, FIELD_OWNER_NAME)) {
			Book book = (Book) protobuf;
			return new MappingResult(MappingResult.Result.MAPPED, book.getOwner().getName(), domain);
		}
		return super.mapToDomainField(fieldResolver, protobuf, domain);
	}

	private boolean isField(final FieldResolver fieldResolver, final String fieldName) {
		return fieldName.equals(fieldResolver.getDomainName());
	}

	@Override
	public <T extends Message.Builder> MappingResult mapToProtobufField(final FieldResolver fieldResolver, final Object domain,
			final T protobufBuilder) throws MappingException {
		if (isField(fieldResolver, FIELD_OWNER_NAME)) {
			Book.Builder bookBuilder = (Book.Builder) protobufBuilder;
			ReadingBook bookDomain = (ReadingBook) domain;
			return new MappingResult(MappingResult.Result.MAPPED, bookDomain.getOwnerName(), bookBuilder.getOwnerBuilder());
		}
		return super.mapToProtobufField(fieldResolver, domain, protobufBuilder);
	}
}
