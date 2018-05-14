package net.badata.protobuf.example.client.view;

import net.badata.protobuf.example.client.ProtobufClient;
import net.badata.protobuf.example.client.domain.ReadingBook;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * @author jsjem
 * @author Roman Gushel
 */
public class LibraryView implements ActionListener, ListSelectionListener {

	private final JPanel form;
	private final JList<ReadingBook> bookList;
	private final JButton getButton;
	private final JButton returnButton;
	private final JButton updateButton;

	private OnLibraryActionListener listener;


	public LibraryView(final JFrame windowFrame) {
		form = new JPanel();
		form.setLayout(null);
		form.setBounds(250, 10, 240, 480);

		bookList = new JList<ReadingBook>();
		bookList.setCellRenderer(new ListCell());
		bookList.addListSelectionListener(this);
		JScrollPane scrollPanel = new JScrollPane(bookList);
		scrollPanel.setBounds(250, 10, 240, 430);
		form.add(scrollPanel);

		getButton = new JButton("Get");
		getButton.setBounds(250, 440, 80, 25);
		getButton.setEnabled(false);
		form.add(getButton);
		getButton.addActionListener(this);

		returnButton = new JButton("Return");
		returnButton.setBounds(330, 440, 80, 25);
		returnButton.setEnabled(false);
		form.add(returnButton);
		returnButton.addActionListener(this);

		updateButton = new JButton("Update");
		updateButton.setBounds(410, 440, 80, 25);
		form.add(updateButton);
		updateButton.addActionListener(this);

		windowFrame.add(form);
	}

	public void setListener(final OnLibraryActionListener listener) {
		this.listener = listener;
	}

	public void setVisible(final boolean visible) {
		form.setVisible(visible);
	}

	public void updateList(final List<ReadingBook> books) {
		bookList.setModel(new BookListModel(books));
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		if(getButton == e.getSource()) {
			notifyBookRequested(getSelectedBook());
		} else if (returnButton == e.getSource()) {
			notifyBookReturned(getSelectedBook());
		} else if (updateButton == e.getSource()) {
			notifyUpdate();
		}
	}

	private ReadingBook getSelectedBook() {
		return bookList.getModel().getElementAt(bookList.getSelectedIndex());
	}

	private void notifyBookRequested(final ReadingBook book) {
		if(listener != null) {
			listener.onRequestBook(book);
		}
	}

	private void notifyBookReturned(final ReadingBook book) {
		if(listener != null) {
			listener.onReturnBook(book);
		}
	}

	private void notifyUpdate() {
		if(listener != null) {
			listener.onUpdateLibrary();
		}
	}

	@Override
	public void valueChanged(final ListSelectionEvent e) {
		if(bookList.getSelectedIndex() >= 0) {
			ReadingBook book = bookList.getModel().getElementAt(bookList.getSelectedIndex());
			returnButton.setEnabled(isOwnBook(book));
			getButton.setEnabled(book.isAvailable());
		} else {
			returnButton.setEnabled(false);
			getButton.setEnabled(false);
		}
	}

	private boolean isOwnBook(final ReadingBook book) {
		return !book.isAvailable() && ProtobufClient.isCurrentUser(book.getOwnerName());
	}

	private static class BookListModel extends AbstractListModel<ReadingBook> {

		private final List<ReadingBook> books;

		public BookListModel (final List<ReadingBook> books) {
			this.books = books;
		}

		@Override
		public int getSize() {
			return books.size();
		}

		@Override
		public ReadingBook getElementAt(final int index) {
			return books.get(index);
		}

	}

	public class ListCell extends JLabel implements ListCellRenderer<ReadingBook> {

		public ListCell() {
			setOpaque(true);
		}

		@Override
		public Component getListCellRendererComponent(final JList<? extends ReadingBook> list, final ReadingBook
				value, final int index, final boolean isSelected, final boolean cellHasFocus) {
			setText(value.toString());
			setBackground(isSelected ? Color.LIGHT_GRAY : Color.WHITE);
			if(value.isAvailable()) {
				setForeground(Color.BLACK);
			} else if(ProtobufClient.isCurrentUser(value.getOwnerName())) {
				setForeground(Color.BLUE);
			} else {
				setForeground(Color.RED);
			}
			return this;
		}
	}

	public interface OnLibraryActionListener {

		void onRequestBook(final ReadingBook book);

		void onReturnBook(final ReadingBook book);

		void onUpdateLibrary();
	}
}
