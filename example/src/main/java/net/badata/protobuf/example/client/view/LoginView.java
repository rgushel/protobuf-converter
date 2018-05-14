package net.badata.protobuf.example.client.view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author jsjem
 * @author Roman Gushel
 */
public class LoginView implements ActionListener {

	private final JPanel form;
	private final JLabel userLabel;
	private final JLabel passwordLabel;
	private final JTextField userText;
	private final JPasswordField passwordText;
	private final JButton loginButton;

	private OnLoginActionListener listener;
	private boolean userAuthorized;

	public LoginView(final JFrame windowFrame) {
		form = new JPanel();
		form.setLayout(null);
		form.setBounds(0, 10, 250, 200);

		userLabel = new JLabel("User");
		userLabel.setBounds(10, 10, 80, 25);
		form.add(userLabel);

		userText = new JTextField(20);
		userText.setBounds(80, 10, 160, 25);
		form.add(userText);

		passwordLabel = new JLabel("Password");
		passwordLabel.setBounds(10, 40, 80, 25);
		form.add(passwordLabel);

		passwordText = new JPasswordField(20);
		passwordText.setBounds(80, 40, 160, 25);
		form.add(passwordText);


		loginButton = new JButton("Login");
		loginButton.setBounds(160, 80, 80, 25);
		form.add(loginButton);
		loginButton.addActionListener(this);

		windowFrame.add(form);
	}

	public void setListener(final OnLoginActionListener listener) {
		this.listener = listener;
	}

	public void setVisible(final boolean visible) {
		form.setVisible(visible);
	}

	public void showLogoutActions() {
		userAuthorized = true;
		loginButton.setText("Logout");
		passwordText.setEnabled(false);
		userText.setEnabled(false);
	}

	public void showLoginActions() {
		userAuthorized = false;
		loginButton.setText("Login");
		passwordText.setEnabled(true);
		userText.setEnabled(true);
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		if (userAuthorized) {
			notifyLogout();
		} else {
			notifyLogin();
		}
	}

	private void notifyLogin() {
		if (listener != null) {
			listener.onUserLogin(userText.getText(), new String(passwordText.getPassword()));
		}
	}

	private void notifyLogout() {
		if (listener != null) {
			listener.onUserLogout();
		}
	}

	public interface OnLoginActionListener {

		void onUserLogin(final String name, final String password);

		void onUserLogout();
	}

}
