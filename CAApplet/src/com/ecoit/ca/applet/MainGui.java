package com.ecoit.ca.applet;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.ecoit.ca.token.TokenModule;
import com.ecoit.ca.token.TokenModules;

import java.beans.*; //property change stuff
import java.security.cert.X509Certificate;
import java.util.Date;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.*;

public class MainGui extends JDialog implements PropertyChangeListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JOptionPane optionPane;

	private String btnString1 = "X\u00E1c nh\u1EADn";
	private String btnString2 = "H\u1EE7y b\u1ECF";
	public int ans = 0;
	public MainGui(JFrame frame ) {
		super(frame,true);
		
		// Create components to be displayed.
		
		JPanel mainPanel = new JPanel();
		JPanel contentPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());

		contentPanel.setLayout(new GridLayout(1, 0));
		String[] columnNames = { "Ng\u01B0\u1EDDi s\u1EDF h\u1EEFu", "C\u01A1 quan ch\u1EE9ng th\u1EF1c",
				"Ng\u00E0y \u0111\u0103ng k\u00FD", "Ng\u00E0y h\u1EBFt h\u1EA1n" };
		Object[][] obj;
		try {
			TokenModule token = TokenModules.newDefaultTokenModule();
			X509Certificate cer = (X509Certificate) token.getCertificate();
			String user = cer.getSubjectDN().getName();
			int index = user.indexOf("CN=");
			int indexLast = user.indexOf(",", index);
			user = user

			.substring(index + 3, indexLast);
			String ca = cer.getIssuerDN().getName();
			index = ca.indexOf("CN=");
			indexLast = ca.indexOf(",", index);
			ca = ca

			.substring(index + 3, indexLast);
			Date date = cer.getNotBefore();
			Date expire = cer.getNotAfter();
			obj = new Object[][] { { user, ca, date, expire } };
		} catch (Exception ex) {
			obj = new Object[][] { { null, null, null, null } };
			ex.printStackTrace();
		}
		JTable table = new JTable(obj, columnNames);
		JScrollPane scroll = new JScrollPane(table);
		table.setEnabled(false);
		contentPanel.add(scroll);
		mainPanel.add(contentPanel, BorderLayout.CENTER);
		
		
		Object[] options = { btnString1, btnString2 };
		optionPane = new JOptionPane(contentPanel, JOptionPane.QUESTION_MESSAGE,
				JOptionPane.YES_NO_OPTION, null, options, options[0]);

		// Make this dialog display it.
		setContentPane(optionPane);

		// Handle window closing correctly.
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				/*
				 * Instead of directly closing the window, we're going to change
				 * the JOptionPane's value property.
				 */
				ans = 0;
				optionPane.setValue(new Integer(JOptionPane.CLOSED_OPTION));
				clearAndHide();
			}
		});

		// Ensure the text field always gets the first focus.
		addComponentListener(new ComponentAdapter() {
			public void componentShown(ComponentEvent ce) {
				
			}
		});


		// Register an event handler that reacts to option pane state changes.
		optionPane.addPropertyChangeListener(this);
		setSize(600, 150);
		setResizable(false);
		setVisible(true);
		
	}
	public void propertyChange(PropertyChangeEvent e) {
		String prop = e.getPropertyName();

		if (isVisible()
				&& (e.getSource() == optionPane)
				&& (JOptionPane.VALUE_PROPERTY.equals(prop) || JOptionPane.INPUT_VALUE_PROPERTY
						.equals(prop))) {
			ans = 1;
			clearAndHide();
			

			if (btnString1.equals(optionPane.getValue())) {
				
			} else { // user closed dialog or clicked cancel
				ans = 0;
				clearAndHide();
			}
		}
	}

	/** This method clears the dialog and hides it. */
	public void clearAndHide() {
		dispose();
	}
}