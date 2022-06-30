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

public class InsertionSortWorker extends JPanel {
	private static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private static int BAR_WIDTH = 30;
	private static int BAR_HEIGHT_MAX = 400;
	private static JButton generate = new JButton("Generate Data");
	private static JButton sort = new JButton("Sort Data");
	private static JLabel additionalInfo = new JLabel("Key value: null");

	public static int milisec = 500;
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
		InsertionSortWorker.colors = colors;
	}

	public int getMilisec() {
		return milisec;
	}

	public void setMilisec(int milisec) {
		InsertionSortWorker.milisec = milisec;
	}

	public int getNumItems() {
		return numItems;
	}

	public void setNumItems(int numItems) {
		InsertionSortWorker.numItems = numItems;
	}

	public Color getDefaultColor() {
		return defaultColor;
	}

	public void setDefaultColor(Color defaultColor) {
		InsertionSortWorker.defaultColor = defaultColor;
	}

	public Color getSelectedColor() {
		return selectedColor;
	}

	public void setSelectedColor(Color selectedColor) {
		InsertionSortWorker.selectedColor = selectedColor;
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

	public InsertionSortWorker(int numItems, int milisec, Color defCo, Color selCo) {
		this.setMilisec(milisec);
		this.setNumItems(numItems);
		this.setDefaultColor(defCo);
		this.setSelectedColor(selCo);
		items = new int[numItems];
		colors = new Color[numItems];
		this.items = generateRandomNumbers();
		setToDefaultColor();

		InsertionSortWorker.BAR_WIDTH = (Integer) screenSize.width / numItems;
		BAR_HEIGHT_MAX = screenSize.height / 5 * 4;
	}

	private void setNewColor(Color color, int i) {
		InsertionSortWorker.colors[i] = color;
	}

	private void setToDefaultColor() {
		for (int i = 0; i < colors.length; i++) {
			getColors()[i] = getDefaultColor();
		}
	}

	private static void infoLabelChange(int key) {
		additionalInfo.setText("Key value: " + key);
	}

	private static void infoLabelReset() {
		additionalInfo.setText("Key value: null");
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
			g.setColor(InsertionSortWorker.getColors()[i]);
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
//			int temp = 0;

			for (int i = 1; i < n; ++i) {
				// Mark the position of first item of the unsorted array
				setNewColor(getSelectedColor(), i);
				publish(Arrays.copyOf(items, items.length));
				try {
					Thread.sleep(getMilisec());
				} catch (Exception e) {
				}

				int key = items[i];
				infoLabelChange(key);
				int j = i - 1;

				// Mark the position of last item of the sorted array
				setNewColor(getSelectedColor(), j);
				publish(Arrays.copyOf(items, items.length));
				try {
					Thread.sleep(getMilisec());
				} catch (Exception e) {
				}
				/*
				 * Move elements of items[0..i-1], that are greater than key, to one position
				 * ahead of their current position
				 */
				while (j >= 0 && items[j] > key) {

					// Mark the item being push forward in the array
					setNewColor(getSelectedColor(), j);
					publish(Arrays.copyOf(items, items.length));
					try {
						Thread.sleep(getMilisec());
					} catch (Exception e) {
					}

					items[j + 1] = items[j];

					// Unmark the item
//					setNewColor(getDefaultColor(), j);
					publish(Arrays.copyOf(items, items.length));
					try {
						Thread.sleep(getMilisec());
					} catch (Exception e) {
					}

					j = j - 1;
				}

				items[j + 1] = key;

				// Mark the final position where key is place
				setNewColor(getSelectedColor(), j + 1);
				publish(Arrays.copyOf(items, items.length));
				try {
					Thread.sleep(getMilisec());
				} catch (Exception e) {
				}

				// Unmark all
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
			items[i] = (int) (Math.random() * InsertionSortWorker.BAR_HEIGHT_MAX);
		}

		return items;
	}

	public static void createAndShowGUI() {
//		BubbleSortWorker2 insertionSort = new BubbleSortWorker2(BubbleSortWorker2.generateRandomNumbers());
		InsertionSortWorker insertionSort = new InsertionSortWorker(numItems, milisec, defaultColor, selectedColor);
//		generate.addActionListener((e) -> );
		generate.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				insertionSort.setItems(InsertionSortWorker.generateRandomNumbers());
				infoLabelReset();
			}
		});
		sort.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				generate.setEnabled(false);
				sort.setEnabled(false);
				insertionSort.sort();
			}
		});

		JPanel bottom = new JPanel();
		bottom.add(generate);
		bottom.add(sort);
		bottom.add(additionalInfo);

		JFrame frame = new JFrame("Insertion Sort Visualization");
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setResizable(false);
		frame.add(insertionSort, BorderLayout.CENTER);
		frame.add(bottom, BorderLayout.PAGE_END);
		frame.pack();
		frame.setLocationRelativeTo(null); // Center Screen
		frame.setVisible(true);
	}

	public static void main(String[] args) throws Exception {
		EventQueue.invokeLater(() -> createAndShowGUI());
	}

}
