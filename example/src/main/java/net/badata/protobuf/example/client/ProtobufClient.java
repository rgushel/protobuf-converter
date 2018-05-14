package net.badata.protobuf.example.client;

import com.google.protobuf.Any;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import net.badata.protobuf.converter.Converter;
import net.badata.protobuf.example.client.domain.Reader;
import net.badata.protobuf.example.client.domain.ReadingBook;
import net.badata.protobuf.example.proto.Book;
import net.badata.protobuf.example.proto.Books;
import net.badata.protobuf.example.proto.Failure;
import net.badata.protobuf.example.proto.LibraryServiceGrpc;
import net.badata.protobuf.example.proto.Request;
import net.badata.protobuf.example.proto.Response;
import net.badata.protobuf.example.proto.User;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author jsjem
 * @author Roman Gushel
 */
public class ProtobufClient {

	private static Reader currentUser;
	private final ManagedChannel channel;
	private final LibraryServiceGrpc.LibraryServiceBlockingStub blockingStub;
	private String userToken;

	public ProtobufClient(String host, int port) {
		channel = ManagedChannelBuilder.forAddress(host, port)
				.usePlaintext(true)
				.build();
		blockingStub = LibraryServiceGrpc.newBlockingStub(channel);
	}

	public void shutdown() {
		try {
			channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static boolean isCurrentUser(final String name) {
		return currentUser.getName().equals(name);
	}

	public boolean signIn(final Reader user) {
		User userCredentials = Converter.create().toProtobuf(User.class, user);
		userToken = blockingStub.signIn(userCredentials).getToken();
		if(userToken != null && !userToken.isEmpty()) {
			currentUser = user;
			return true;
		}
		return false;
	}

	public void logOut() {
		userToken = null;
	}

	public ReadingBook getBook(final ReadingBook book) throws ClientException {
		Converter converter = Converter.create();
		Book bookInfo = converter.toProtobuf(Book.class, book);
		Response response = blockingStub.getBook(createRequest(bookInfo));
		return converter.toDomain(ReadingBook.class, extractResponseData(response, Book.class));
	}

	private Request createRequest(final Message message) {
		Request.Builder builder = Request.newBuilder().setUserToken(userToken);
		if (message != null) {
			builder.setData(Any.pack(message));
		}
		return builder.build();
	}

	private <T extends Message> T extractResponseData(final Response response, final Class<T> clazz) throws
			ClientException {
		Any data = response.getData();
		try {
			if (data.is(Failure.class)) {
				throw new ClientException(data.unpack(Failure.class).getCause());
			}
			return data.unpack(clazz);
		} catch (InvalidProtocolBufferException e) {
			throw new ClientException(e.getMessage());
		}
	}

	public List<ReadingBook> addBook(final ReadingBook book) throws ClientException {
		Converter converter = Converter.create();
		Book bookInfo = converter.toProtobuf(Book.class, book);
		Response response = blockingStub.addBook(createRequest(bookInfo));
		return converter.toDomain(ReadingBook.class,
				extractResponseData(response, Books.class).getBookList());
	}
	public List<ReadingBook> showAllBooks() throws ClientException {
		Response response = blockingStub.showLibrary(createRequest(null));
		return Converter.create().toDomain(ReadingBook.class,
				extractResponseData(response, Books.class).getBookList());
	}

}
