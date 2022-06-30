package hust.soict.ictglobal.gui.awt;

import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseEventDemo extends Frame {
	private TextField tfMouseX;
	private TextField tfMouseY;

	public MouseEventDemo() {
		// TODO Auto-generated constructor
		setLayout(new FlowLayout());

		add(new Label("X-Click: "));

		tfMouseX = new TextField(10);
		tfMouseX.setEditable(false);
		add(tfMouseX);

		add(new Label("Y-Click: "));

		tfMouseY = new TextField(10);
		tfMouseY.setEditable(false);
		add(tfMouseY);

		addMouseListener(new MyMouseListener());

		setTitle("MouseEvent Demo");
		setSize(350, 100);
		setVisible(true);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new MouseEventDemo();
	}

	private class MyMouseListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			tfMouseX.setText(e.getX() + "");
			tfMouseY.setText(e.getY() + "");
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

	}
}
