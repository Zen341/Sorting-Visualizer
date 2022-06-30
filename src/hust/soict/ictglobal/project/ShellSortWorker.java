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

public class ShellSortWorker extends JPanel {
	private static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private static int BAR_WIDTH = 30;
	private static int BAR_HEIGHT_MAX = 400;
	private static JButton generate = new JButton("Generate Data");
	private static JButton sort = new JButton("Sort Data");
	private static JLabel additionalInfo = new JLabel("Gap value: null - Temp value: null");

	public static int milisec = 1000;
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
		ShellSortWorker.colors = colors;
	}

	public int getMilisec() {
		return milisec;
	}

	public void setMilisec(int milisec) {
		ShellSortWorker.milisec = milisec;
	}

	public int getNumItems() {
		return numItems;
	}

	public void setNumItems(int numItems) {
		ShellSortWorker.numItems = numItems;
	}

	public Color getDefaultColor() {
		return defaultColor;
	}

	public void setDefaultColor(Color defaultColor) {
		ShellSortWorker.defaultColor = defaultColor;
	}

	public Color getSelectedColor() {
		return selectedColor;
	}

	public void setSelectedColor(Color selectedColor) {
		ShellSortWorker.selectedColor = selectedColor;
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

	public ShellSortWorker(int numItems, int milisec, Color defCo, Color selCo) {
		this.setMilisec(milisec);
		this.setNumItems(numItems);
		this.setDefaultColor(defCo);
		this.setSelectedColor(selCo);
		items = new int[numItems];
		colors = new Color[numItems];
		this.items = generateRandomNumbers();
		setToDefaultColor();

		ShellSortWorker.BAR_WIDTH = (Integer) screenSize.width / numItems;
		BAR_HEIGHT_MAX = screenSize.height / 5 * 4;
	}

	private void setNewColor(Color color, int i) {
		ShellSortWorker.colors[i] = color;
	}

	private void setToDefaultColor() {
		for (int i = 0; i < colors.length; i++) {
			getColors()[i] = getDefaultColor();
		}
	}

	private static void infoLabelChange(int gap, int temp) {
		additionalInfo.setText("Gap value: " + gap + " - Temp value: " + temp);
	}

	private static void infoLabelReset() {
		additionalInfo.setText("Gap value: null - Temp value: null");
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
			g.setColor(ShellSortWorker.getColors()[i]);
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

			// Start with a big gap, then reduce the gap
			for (int gap = n / 2; gap > 0; gap /= 2) {
				infoLabelChange(gap, 0);
				// Do a gapped insertion sort for this gap size.
				// The first gap elements a[0..gap-1] are already
				// in gapped order keep adding one more element
				// until the entire array is gap sorted
				for (int i = gap; i < n; i += 1) {
					setNewColor(getSelectedColor(), i);
					publish(Arrays.copyOf(items, items.length));
					try {
						Thread.sleep(milisec);
					} catch (Exception e) {
					}

					// add a[i] to the elements that have been gap
					// sorted save a[i] in temp and make a hole at
					// position i
					int temp = items[i];
					infoLabelChange(gap, temp);

					// shift earlier gap-sorted elements up until
					// the correct location for a[i] is found
					int j;
					for (j = i; j >= gap && items[j - gap] > temp; j -= gap) {
//						setNewColor(getSelectedColor(), j);
						setNewColor(getSelectedColor(), j - gap);
						publish(Arrays.copyOf(items, items.length));
						try {
							Thread.sleep(milisec);
						} catch (Exception e) {
						}

						items[j] = items[j - gap];

//						setNewColor(getDefaultColor(), j);
//						setNewColor(getDefaultColor(), j - gap);
						publish(Arrays.copyOf(items, items.length));
						try {
							Thread.sleep(milisec);
						} catch (Exception e) {
						}
					}
					// put temp (the original a[i]) in its correct
					// location
					setNewColor(getSelectedColor(), j);
					items[j] = temp;
					publish(Arrays.copyOf(items, items.length));
					try {
						Thread.sleep(milisec);
					} catch (Exception e) {
					}

					setToDefaultColor();
					publish(Arrays.copyOf(items, items.length));
//					try {
//						Thread.sleep(milisec);
//					} catch (Exception e) {
//					}
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
			items[i] = (int) (Math.random() * ShellSortWorker.BAR_HEIGHT_MAX);
		}

		return items;
	}

	public static void createAndShowGUI() {
//		BubbleSortWorker2 shellSort = new BubbleSortWorker2(BubbleSortWorker2.generateRandomNumbers());
		ShellSortWorker shellSort = new ShellSortWorker(numItems, milisec, defaultColor, selectedColor);
		generate.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				shellSort.setItems(ShellSortWorker.generateRandomNumbers());
				infoLabelReset();
			}
		});
		sort.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				generate.setEnabled(false);
				sort.setEnabled(false);
				shellSort.sort();
			}
		});

		JPanel bottom = new JPanel();
		bottom.add(generate);
		bottom.add(sort);
		bottom.add(additionalInfo);

		JFrame frame = new JFrame("Shell Sort Visualization");
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setResizable(false);
		frame.add(shellSort, BorderLayout.CENTER);
		frame.add(bottom, BorderLayout.PAGE_END);
		frame.pack();
		frame.setLocationRelativeTo(null); // Center Screen
		frame.setVisible(true);
	}

	public static void main(String[] args) throws Exception {
		EventQueue.invokeLater(() -> createAndShowGUI());
	}

}
