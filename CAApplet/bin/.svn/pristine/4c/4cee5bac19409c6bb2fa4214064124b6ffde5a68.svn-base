package com.ecoit.ca.applet;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.io.IOException;
import java.util.Date;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PinInputter extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5926423178158402692L;
	private final JPanel contentPanel = new JPanel();
	private JPasswordField passwordField;

	/**
	 * Create the dialog.
	 */
	public PinInputter() {
		
		setBounds(100, 100, 260, 119);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JLabel lblNewLabel = new JLabel("Please enter your PIN");
			contentPanel.add(lblNewLabel, BorderLayout.NORTH);
		}
		{
			passwordField = new JPasswordField();
			contentPanel.add(passwordField, BorderLayout.CENTER);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						PinInputter.this.dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						PinInputter.this.passwordField.setText("");
						PinInputter.this.dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}

		setModal(true);
	}

	public static CallbackHandler getCallbackHandler() {
		CallbackHandler handler = new CallbackHandler() {

			@Override
			public void handle(Callback[] callbacks) throws IOException,
					UnsupportedCallbackException {
				for (Callback callback : callbacks) {
					if (callback instanceof PasswordCallback) {
						PinInputter pinInput = new PinInputter();
						pinInput.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
						pinInput.setVisible(true);

						((PasswordCallback) callback)
								.setPassword(pinInput.passwordField
										.getPassword());
					} else {
						throw new UnsupportedCallbackException(callback);
					}
				}
			}
		};

		return handler;
	}
}
