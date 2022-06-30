package hust.soict.ictglobal.project;

import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.List;
import javax.swing.*;
import javax.swing.Timer;

public class BubbleSortWorker extends JPanel {
	private final static int BAR_WIDTH = 30;
	private final static int BAR_HEIGHT_MAX = 400;

	private int[] items;
	private boolean everySwap;

	public BubbleSortWorker(int[] items, boolean everySwap) {
		this.items = items;
		this.everySwap = everySwap;
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

			g.setColor(Color.RED);
			g.fillRect(x, y, BAR_WIDTH, items[i]);

			g.setColor(Color.BLUE);
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
					if (items[j - 1] > items[j]) {
						temp = items[j - 1];
						items[j - 1] = items[j];
						items[j] = temp;

						// Update after every swap is done
						if (everySwap) {
							publish(Arrays.copyOf(items, items.length));
							try {
								Thread.sleep(1000);
//								System.out.println(everySwap);
							} catch (Exception e) {
							}
						}
					}
				}

				// Update once per iteration

				if (!everySwap) {
					publish(Arrays.copyOf(items, items.length));
					try {
						Thread.sleep(1000);
//						System.out.println("Update once per iteration");
					} catch (Exception e) {
					}
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
		}
	}

	public static int[] generateRandomNumbers() {
		int[] items = new int[10];

		for (int i = 0; i < items.length; i++) {
			items[i] = (int) (Math.random() * BubbleSortWorker.BAR_HEIGHT_MAX);
		}

		return items;
	}

	private static void createAndShowGUI() {
		BubbleSortWorker bubbleSort = new BubbleSortWorker(BubbleSortWorker.generateRandomNumbers(), true);

		JButton generate = new JButton("Generate Data");
		generate.addActionListener((e) -> bubbleSort.setItems(BubbleSortWorker.generateRandomNumbers()));

		JButton sort = new JButton("Sort Data");
		sort.addActionListener((e) -> bubbleSort.sort());

		JPanel bottom = new JPanel();
		bottom.add(generate);
		bottom.add(sort);

		JFrame frame = new JFrame("Bubble Sort Visualization");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(bubbleSort, BorderLayout.CENTER);
		frame.add(bottom, BorderLayout.PAGE_END);
		frame.pack();
		frame.setLocationByPlatform(true);
		frame.setVisible(true);
	}

	public static void main(String[] args) throws Exception {
		EventQueue.invokeLater(() -> createAndShowGUI());
	}
}