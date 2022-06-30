package hust.soict.ictglobal.gui.awt;

import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyEventDemo extends Frame {
	private TextField tfInput;
	private TextArea taDisplay;

	public KeyEventDemo() {
		// TODO Auto-generated constructor stub
		setLayout(new FlowLayout());

		add(new Label("Enter Text: "));
		tfInput = new TextField(10);
		add(tfInput);
		taDisplay = new TextArea(5, 40);
		add(taDisplay);

		tfInput.addKeyListener(new MyKeyListener());

		setTitle("KeyEvent Demo");
		setSize(400, 200);
		setVisible(true);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new KeyEventDemo();
	}

	private class MyKeyListener implements KeyListener {

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			taDisplay.append("You have typed " + e.getKeyChar() + "\n");
		}

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub

		}

	}
}
