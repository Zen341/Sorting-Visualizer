package hust.soict.ictglobal.project;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

public class BubbleSortWorker2 extends JPanel {
	private static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private static int BAR_WIDTH = 30;
	private static int BAR_HEIGHT_MAX = 400;
	private static JButton generate = new JButton("Generate Data");
	private static JButton sort = new JButton("Sort Data");

	public static int milisec = 300;
	public static int numItems = 10;
	public static Color defaultColor = Color.RED;
	public static Color selectedColor = Color.GREEN;
	private int[] items;
	private static Color[] colors;
//	private boolean everySwap;

	public static Color[] getColors() {
		return colors;
	}

	public static void setColors(Color[] colors) {
		BubbleSortWorker2.colors = colors;
	}

	public int getMilisec() {
		return milisec;
	}

	public void setMilisec(int milisec) {
		BubbleSortWorker2.milisec = milisec;
	}

	public int getNumItems() {
		return numItems;
	}

	public void setNumItems(int numItems) {
		BubbleSortWorker2.numItems = numItems;
	}

	public Color getDefaultColor() {
		return defaultColor;
	}

	public void setDefaultColor(Color defaultColor) {
		BubbleSortWorker2.defaultColor = defaultColor;
	}

	public Color getSelectedColor() {
		return selectedColor;
	}

	public void setSelectedColor(Color selectedColor) {
		BubbleSortWorker2.selectedColor = selectedColor;
	}

//	public BubbleSortWorker2() {
//		// TODO Auto-generated constructor stub
//		setToDefaultColor();
//	}
//
//	public BubbleSortWorker2(int[] items) {
//		this.items = items;
//		setToDefaultColor();
//	}

	public BubbleSortWorker2(int numItems, int milisec, Color defCo, Color selCo) {
		this.setMilisec(milisec);
		this.setNumItems(numItems);
		this.setDefaultColor(defCo);
		this.setSelectedColor(selCo);
		items = new int[numItems];
		colors = new Color[numItems];
		this.items = generateRandomNumbers();
		setToDefaultColor();

		BubbleSortWorker2.BAR_WIDTH = (Integer) screenSize.width / numItems;
		BAR_HEIGHT_MAX = screenSize.height / 5 * 4;
	}

	private void setNewColor(Color color, int i) {
		BubbleSortWorker2.colors[i] = color;
	}

	private void setToDefaultColor() {
		for (int i = 0; i < colors.length; i++) {
			getColors()[i] = getDefaultColor();
		}
	}

	public void setItems(int[] items) {
		this.items = items;
		repaint();
	}

	public void sort() {
		new SortWorker(items).execute();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		for (int i = 0; i < items.length; i++) {
			int x = i * BAR_WIDTH;
			int y = getHeight() - items[i];

//			g.setColor(Color.RED);
			g.setColor(BubbleSortWorker2.getColors()[i]);
			g.fillRect(x, y, BAR_WIDTH, items[i]);

			g.setColor(Color.BLACK);
			g.drawString("" + items[i], x, y);
		}

	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(items.length * BAR_WIDTH, BAR_HEIGHT_MAX + 20);
	}

	class SortWorker extends SwingWorker<Void, int[]> {
		private int[] items;

		public SortWorker(int[] unsortedItems) {
			items = Arrays.copyOf(unsortedItems, unsortedItems.length);
		}

		@Override
		protected Void doInBackground() {
			int n = items.length;
			int temp = 0;

			for (int i = 0; i < n; i++) {
				for (int j = 1; j < (n - i); j++) {
					// Update at each item selection
					setNewColor(getSelectedColor(), j);
					setNewColor(getSelectedColor(), j - 1);
					publish(Arrays.copyOf(items, items.length));
					try {
						Thread.sleep(getMilisec());
					} catch (Exception e) {
					}

					// Sorting Algorithm
					if (items[j - 1] > items[j]) {
						temp = items[j - 1];
						items[j - 1] = items[j];
						items[j] = temp;

						// Update after every swap is done
						publish(Arrays.copyOf(items, items.length));
						try {
							Thread.sleep(getMilisec());
						} catch (Exception e) {
						}
					}
					setNewColor(getDefaultColor(), j);
					setNewColor(getDefaultColor(), j - 1);
				}
			}

			return null;
		}

		@Override
		protected void process(List<int[]> list) {
			int[] items = list.get(list.size() - 1);
			setItems(items);
		}

		@Override
		protected void done() {
			setToDefaultColor();
			publish(Arrays.copyOf(items, items.length));
			try {
				Thread.sleep(milisec);
			} catch (Exception e) {
			}
			generate.setEnabled(true);
			sort.setEnabled(true);
		}
	}

	public static int[] generateRandomNumbers() {
		int[] items = new int[numItems];

		for (int i = 0; i < items.length; i++) {
			items[i] = (int) (Math.random() * BubbleSortWorker2.BAR_HEIGHT_MAX);
		}

		return items;
	}

	public static void createAndShowGUI() {
//		BubbleSortWorker2 bubbleSort = new BubbleSortWorker2(BubbleSortWorker2.generateRandomNumbers());
		BubbleSortWorker2 bubbleSort = new BubbleSortWorker2(numItems, milisec, defaultColor, selectedColor);
		generate.addActionListener((e) -> bubbleSort.setItems(BubbleSortWorker2.generateRandomNumbers()));

		sort.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				generate.setEnabled(false);
				sort.setEnabled(false);
				bubbleSort.sort();
			}
		});

		JPanel bottom = new JPanel();
		bottom.add(generate);
		bottom.add(sort);

		JFrame frame = new JFrame("Bubble Sort Visualization");
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setResizable(false);
		frame.add(bubbleSort, BorderLayout.CENTER);
		frame.add(bottom, BorderLayout.PAGE_END);
		frame.pack();
		frame.setLocationRelativeTo(null); // Center Screen
		frame.setVisible(true);
	}

	public static void main(String[] args) throws Exception {
		EventQueue.invokeLater(() -> createAndShowGUI());
	}

}
