package net.badata.protobuf.example.client.domain;

import net.badata.protobuf.converter.annotation.ProtoClass;
import net.badata.protobuf.converter.annotation.ProtoField;
import net.badata.protobuf.example.proto.Book;

/**
 * @author jsjem
 * @author Roman Gushel
 */
@ProtoClass(value = Book.class, mapper = BookMapper.class)
public class ReadingBook {

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
	@ProtoField(name = "name")
	private String ownerName;

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

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(final boolean available) {
		this.available = available;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(final String ownerName) {
		this.ownerName = ownerName;
	}

	@Override
	public String toString() {
		return title + " by " + author + ". - " + pages;
	}
}
