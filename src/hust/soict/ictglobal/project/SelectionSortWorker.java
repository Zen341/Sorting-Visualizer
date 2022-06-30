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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

public class SelectionSortWorker extends JPanel {
	private static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private static int BAR_WIDTH = 30;
	private static int BAR_HEIGHT_MAX = 400;
	private static JButton generate = new JButton("Generate Data");
	private static JButton sort = new JButton("Sort Data");
	private static JLabel minValueLabel = new JLabel("Min value: null - Min index: null");

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
		SelectionSortWorker.colors = colors;
	}

	public int getMilisec() {
		return milisec;
	}

	public void setMilisec(int milisec) {
		SelectionSortWorker.milisec = milisec;
	}

	public int getNumItems() {
		return numItems;
	}

	public void setNumItems(int numItems) {
		SelectionSortWorker.numItems = numItems;
	}

	public Color getDefaultColor() {
		return defaultColor;
	}

	public void setDefaultColor(Color defaultColor) {
		SelectionSortWorker.defaultColor = defaultColor;
	}

	public Color getSelectedColor() {
		return selectedColor;
	}

	public void setSelectedColor(Color selectedColor) {
		SelectionSortWorker.selectedColor = selectedColor;
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

	public SelectionSortWorker(int numItems, int milisec, Color defCo, Color selCo) {
		this.setMilisec(milisec);
		this.setNumItems(numItems);
		this.setDefaultColor(defCo);
		this.setSelectedColor(selCo);
		items = new int[numItems];
		colors = new Color[numItems];
		this.items = generateRandomNumbers();
		setToDefaultColor();

		SelectionSortWorker.BAR_WIDTH = (Integer) screenSize.width / numItems;
		BAR_HEIGHT_MAX = screenSize.height / 5 * 4;
	}

	private void setNewColor(Color color, int i) {
		SelectionSortWorker.colors[i] = color;
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
			g.setColor(SelectionSortWorker.getColors()[i]);
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

			for (int i = 0; i < n - 1; i++) {
				int minIdx = i;
				minValueLabel.setText("Min value: " + items[minIdx] + " - Min index: " + minIdx);

				// Mark the first item of the currently examined array
				setNewColor(getSelectedColor(), i);
				publish(Arrays.copyOf(items, items.length));
				try {
					Thread.sleep(getMilisec());
				} catch (Exception e) {
				}

				for (int j = i + 1; j < n; j++) {

					// Mark the item judged
					setNewColor(getSelectedColor(), j);
					publish(Arrays.copyOf(items, items.length));
					try {
						Thread.sleep(getMilisec());
					} catch (Exception e) {
					}

					if (items[j] < items[minIdx]) {
						minIdx = j;
						minValueLabel.setText("Min value: " + items[minIdx] + " - Min index: " + minIdx);
					}

					// Unmark the item judged
					setNewColor(getDefaultColor(), j);
					publish(Arrays.copyOf(items, items.length));
					try {
						Thread.sleep(getMilisec());
					} catch (Exception e) {
					}
				}

				// Mark the discovered min item
				setNewColor(getSelectedColor(), minIdx);
				publish(Arrays.copyOf(items, items.length));
				try {
					Thread.sleep(getMilisec());
				} catch (Exception e) {
				}

				temp = items[minIdx];
				items[minIdx] = items[i];
				items[i] = temp;

				// Clear all color
				setToDefaultColor();
				publish(Arrays.copyOf(items, items.length));
				try {
					Thread.sleep(getMilisec());
				} catch (Exception e) {
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
			items[i] = (int) (Math.random() * SelectionSortWorker.BAR_HEIGHT_MAX);
		}

		return items;
	}

	public static void createAndShowGUI() {
//		BubbleSortWorker2 selectionSort = new BubbleSortWorker2(BubbleSortWorker2.generateRandomNumbers());
		SelectionSortWorker selectionSort = new SelectionSortWorker(numItems, milisec, defaultColor, selectedColor);
//		generate.addActionListener((e) -> );
		generate.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				minValueLabel.setText("Min value: null - Min index: null");
				selectionSort.setItems(SelectionSortWorker.generateRandomNumbers());
			}
		});
		sort.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				generate.setEnabled(false);
				sort.setEnabled(false);
				selectionSort.sort();
			}
		});

		JPanel bottom = new JPanel();
		bottom.add(generate);
		bottom.add(sort);
		bottom.add(minValueLabel);

		JFrame frame = new JFrame("Selection Sort Visualization");
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setResizable(false);
		frame.add(selectionSort, BorderLayout.CENTER);
		frame.add(bottom, BorderLayout.PAGE_END);
		frame.pack();
		frame.setLocationRelativeTo(null); // Center Screen
		frame.setVisible(true);
	}

	public static void main(String[] args) throws Exception {
		EventQueue.invokeLater(() -> createAndShowGUI());
	}

}
