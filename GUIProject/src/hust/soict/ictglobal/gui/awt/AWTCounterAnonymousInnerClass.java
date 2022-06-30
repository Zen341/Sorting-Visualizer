package hust.soict.ictglobal.gui.awt;

import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AWTCounterAnonymousInnerClass extends Frame {
	private TextField tfCount;
	private Button btnCount;
	private int count = 0;

	public AWTCounterAnonymousInnerClass() {
		// TODO Auto-generated constructor stub
		setLayout(new FlowLayout());
		add(new Label("Counter"));
		tfCount = new TextField("0", 10);
		tfCount.setEditable(false);
		add(tfCount);

		btnCount = new Button("Count");
		add(btnCount);

		btnCount.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				++count;
				tfCount.setText(count + "");
			}
		});

		setTitle("AWT Counter");
		setSize(250, 100);
		setVisible(true);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new AWTCounterAnonymousInnerClass();

	}

}
