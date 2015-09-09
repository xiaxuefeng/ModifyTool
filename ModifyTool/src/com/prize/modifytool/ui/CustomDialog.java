package com.prize.modifytool.ui;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class CustomDialog extends JDialog implements ActionListener, PropertyChangeListener {

	private static final long serialVersionUID = 1L;

    private JOptionPane optionPane;
    
    private String title;
    private String message;
    
	public CustomDialog(Frame aFrame) {
		super(aFrame, true);
		setResizable(false);
		initOptionPane();
	}

	public CustomDialog(Frame aFrame, String title, String message) {
		super(aFrame, true);

		setResizable(false);
		setDialogTitle(title);
		setMessage(message);
		initOptionPane();

	}

	private void initOptionPane() {
		optionPane = new JOptionPane();
		optionPane.setMessageType(JOptionPane.WARNING_MESSAGE);
		optionPane.setMessage(message);
		optionPane.setSize(250, 150);
		setContentPane(optionPane);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we) {
				optionPane.setValue(JOptionPane.CLOSED_OPTION);
			}
		});
		optionPane.addPropertyChangeListener(this);
	}
    
	public void setDialogTitle(String title) {
		this.title = title;
		setTitle(title);
	}

	public void setMessage(String message) {
		optionPane.setMessage(message);
		this.message = message;
	}
    
    @Override
    public void propertyChange(PropertyChangeEvent e) {
        String propName = e.getPropertyName();

        if ( isVisible()
             && e.getSource()==optionPane
             && (JOptionPane.VALUE_PROPERTY.equals(propName) || JOptionPane.INPUT_VALUE_PROPERTY.equals(propName))) {

            Object value = optionPane.getValue();
            if (value == JOptionPane.UNINITIALIZED_VALUE) {
                return;
            }
            optionPane.setValue( JOptionPane.UNINITIALIZED_VALUE );
            clearAndHide();
        }
    }

    public void clearAndHide() {
        setVisible(false);
        dispose();
    }

	@Override
	public void actionPerformed(ActionEvent e) {
	}

}
