package net.badata.protobuf.example.server.domain;

import net.badata.protobuf.converter.annotation.ProtoClass;
import net.badata.protobuf.converter.annotation.ProtoField;
import net.badata.protobuf.example.client.domain.BooleanEnumConverter;
import net.badata.protobuf.example.proto.Book;

import java.util.Objects;

/**
 * @author jsjem
 * @author Roman Gushel
 */
@ProtoClass(Book.class)
public class LibraryBook {

	@ProtoField(name = "id")
	private long bookId;
	@ProtoField
	private String author;
	@ProtoField
	private String title;
	@ProtoField
	private int pages;
	@ProtoField(name = "state", converter = BooleanEnumConverter.class)
	private boolean available;
	@ProtoField
	private Reader owner;

	public long getBookId() {
		return bookId;
	}

	public void setBookId(final long bookId) {
		this.bookId = bookId;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(final String author) {
		this.author = author;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	public int getPages() {
		return pages;
	}

	public void setPages(final int pages) {
		this.pages = pages;
	}

	public Reader getOwner() {
		return owner;
	}

	public void setOwner(final Reader owner) {
		this.owner = owner;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(final boolean available) {
		this.available = available;
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof LibraryBook) {
			LibraryBook book = (LibraryBook) obj;
			return Objects.equals(book.author, author) && Objects.equals(book.title, title) &&
					Objects.equals(book.bookId, bookId);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(title, author, bookId);
	}
}
