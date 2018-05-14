package net.badata.protobuf.example.server;

import com.google.protobuf.Any;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import io.grpc.stub.StreamObserver;
import net.badata.protobuf.converter.Converter;
import net.badata.protobuf.converter.FieldsIgnore;
import net.badata.protobuf.example.proto.Book;
import net.badata.protobuf.example.proto.Books;
import net.badata.protobuf.example.proto.Failure;
import net.badata.protobuf.example.proto.LibraryServiceGrpc;
import net.badata.protobuf.example.proto.RegisteredUser;
import net.badata.protobuf.example.proto.Request;
import net.badata.protobuf.example.proto.Response;
import net.badata.protobuf.example.proto.User;
import net.badata.protobuf.example.server.domain.LibraryBook;
import net.badata.protobuf.example.server.domain.Reader;

import java.util.List;

/**
 * @author jsjem
 * @author Roman Gushel
 */
public class LibraryService extends LibraryServiceGrpc.LibraryServiceImplBase {

	private final MemoryDatabase database;

	public LibraryService(final MemoryDatabase database) {
		this.database = database;
	}

	@Override
	public void signIn(final User request, final StreamObserver<RegisteredUser> responseObserver) {
		Reader user = Converter.create().toDomain(Reader.class, request);
		String token = createToken(user);
		database.addUser(user, token);
		RegisteredUser response = RegisteredUser.newBuilder()
				.setUser(request)
				.setToken(token)
				.build();
		responseObserver.onNext(response);
		responseObserver.onCompleted();
	}

	private String createToken(final Reader user) {
		return user.getName() + user.getPassword();
	}

	@Override
	public void getBook(final Request request, final StreamObserver<Response> responseObserver) {
		if (isUserRegistered(request)) {
			Reader user = database.findUser(request.getUserToken());
			Converter converter = Converter.create();
			try {
				LibraryBook book = converter.toDomain(LibraryBook.class, request.getData().unpack(Book
						.class));
				if(database.giveBook(book, user)) {
					responseObserver.onNext(createResponse(converter.toProtobuf(Book.class, book)));
				} else {
					responseObserver.onNext(createErrorResponse("Book not available"));
				}
			} catch (InvalidProtocolBufferException e) {
				responseObserver.onNext(createErrorResponse("Book info corrupted."));
			}
		} else {
			responseObserver.onNext(createErrorResponse("User not registered."));
		}
		responseObserver.onCompleted();

	}

	private boolean isUserRegistered(final Request request) {
		return database.findUser(request.getUserToken()) != null;
	}

	private Response createErrorResponse(final String cause) {
		return createResponse(Failure.newBuilder().setCause(cause).build());
	}

	private Response createResponse(final Message message) {
		return Response.newBuilder().setData(Any.pack(message)).build();
	}

	@Override
	public void addBook(final Request request, final StreamObserver<Response> responseObserver) {
		if (isUserRegistered(request)) {
			Reader user = database.findUser(request.getUserToken());
			FieldsIgnore ignoredFields = new FieldsIgnore().add(LibraryBook.class, "owner");
			Converter converter = Converter.create(ignoredFields);
			try {
				LibraryBook book = converter.toDomain(LibraryBook.class, request.getData().unpack(Book
						.class));
				book.setOwner(user);
				book.setAvailable(true);
				database.putBook(book);
				responseObserver.onNext(createBookListResponse());
			} catch (InvalidProtocolBufferException e) {
				responseObserver.onNext(createErrorResponse("Book info corrupted."));
			}
		} else {
			responseObserver.onNext(createErrorResponse("User not registered."));
		}
		responseObserver.onCompleted();
	}

	private Response createBookListResponse() {
		FieldsIgnore ignoredFields = new FieldsIgnore().add(Reader.class, "password");
		List<Book> books = Converter.create(ignoredFields).toProtobuf(Book.class, database.getAllBooks());
		return  createResponse(Books.newBuilder()
				.addAllBook(books)
				.build());
	}

	@Override
	public void showLibrary(final Request request, final StreamObserver<Response> responseObserver) {
		if (isUserRegistered(request)) {
			responseObserver.onNext(createBookListResponse());
		} else {
			responseObserver.onNext(createErrorResponse("User not registered."));
		}
		responseObserver.onCompleted();
	}
}
