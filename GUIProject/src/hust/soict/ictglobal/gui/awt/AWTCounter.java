package hust.soict.ictglobal.gui.awt;

import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class AWTCounter extends Frame implements ActionListener {
	private Label lblCount;
	private TextField tfCount;
	private Button btnCount;
	private int count = 0;

	public AWTCounter() {
		setLayout(new FlowLayout());

		lblCount = new Label("Counter");
		add(lblCount);

		tfCount = new TextField(count + "", 10);
		tfCount.setEditable(false);
		add(tfCount);

		btnCount = new Button("Count");
		add(btnCount);

//		btnCount.addActionListener(this); // Comment out due to example 7

		// Example 7
		btnCount.addActionListener(new BtnCountListener());
		//

		// Example 3
		addWindowListener(new MyWindowListener());
		//

		setTitle("AWT Counter");
		setSize(250, 100);

		// Inspection
//		System.out.println(this);
//		System.out.println(lblCount);
//		System.out.println(tfCount);
//		System.out.println(btnCount);

		setVisible(true);

//		System.out.println(this);
//		System.out.println(lblCount);
//		System.out.println(tfCount);
//		System.out.println(btnCount);

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		AWTCounter app = new AWTCounter();
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		// TODO Auto-generated method stub
		++count;
		tfCount.setText(count + "");
	}

	// Example 3
	private class MyWindowListener implements WindowListener {

		@Override
		public void windowOpened(WindowEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void windowClosing(WindowEvent e) {
			// TODO Auto-generated method stub
			System.exit(0);
		}

		@Override
		public void windowClosed(WindowEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void windowIconified(WindowEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void windowDeiconified(WindowEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void windowActivated(WindowEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void windowDeactivated(WindowEvent e) {
			// TODO Auto-generated method stub

		}

	}
	//

	// Example 7
	private class BtnCountListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			++count;
			tfCount.setText(count + "");
		}

	}
	//
}
