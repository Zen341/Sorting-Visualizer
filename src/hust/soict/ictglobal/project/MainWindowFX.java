package hust.soict.ictglobal.project;

import java.util.Arrays;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MainWindowFX extends Application {

	@Override
	public void start(Stage primaryStage) {
		try {
			GridPane root = new GridPane();
			root.setPadding(new Insets(20));
			root.setHgap(25);

			root.setVgap(15);

			Label lbSortAl = new Label("Sorting Algorithm: ");
			ObservableList<String> sortStrings = FXCollections.observableArrayList(
					Arrays.asList("Bubble sort", "Selection sort", "Insertion sort", "Shell sort"));
			ComboBox<String> cbSortAl = new ComboBox<>(sortStrings);
			cbSortAl.getSelectionModel().selectFirst();

			Label lbNumItem = new Label("Number of items (5-100): ");
			Spinner<Integer> spNumItem = new Spinner<>(5, 100, 10, 1);
			spNumItem.setEditable(true);

			Label lbWaitTime = new Label("Waiting time per step (second, 0-5): ");
			Spinner<Double> spWaitTime = new Spinner<>(0, 5, 0.5, 0.05);
			spWaitTime.setEditable(true);

			Label lbDefCo = new Label("Default item color: ");
			ColorPicker defaultColorPicker = new ColorPicker(Color.RED);

			Label lbSelCo = new Label("Selected item color: ");
			ColorPicker selectedColorPicker = new ColorPicker(Color.GREEN);

			Button btnOpen = new Button("Open visual");
			btnOpen.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					String sortString = cbSortAl.getValue();
					Color fxDefCo = defaultColorPicker.getValue();
					Color fxSelCo = selectedColorPicker.getValue();
					java.awt.Color defColor = new java.awt.Color((float) fxDefCo.getRed(), (float) fxDefCo.getGreen(),
							(float) fxDefCo.getBlue(), (float) fxDefCo.getOpacity());
					java.awt.Color selColor = new java.awt.Color((float) fxSelCo.getRed(), (float) fxSelCo.getGreen(),
							(float) fxSelCo.getBlue(), (float) fxSelCo.getOpacity());
					switch (sortString) {
					case "Bubble sort":
						bubbleSortWindow(spNumItem.getValue(), ((Double) (spWaitTime.getValue() * 1000)).intValue(),
								defColor, selColor);
						break;

					case "Selection sort":
						selectionSortWindow(spNumItem.getValue(), ((Double) (spWaitTime.getValue() * 1000)).intValue(),
								defColor, selColor);
						break;

					case "Insertion sort":
						insertionSortWindow(spNumItem.getValue(), ((Double) (spWaitTime.getValue() * 1000)).intValue(),
								defColor, selColor);
						break;

					case "Shell sort":
						shellSortWindow(spNumItem.getValue(), ((Double) (spWaitTime.getValue() * 1000)).intValue(),
								defColor, selColor);
						break;

					default:
						System.err.println("Combobox wrong value");
						break;
					}
				}
			});

			Tooltip sortAlTooltip = new Tooltip("The algorithm will be used to sort the array.");
			Tooltip numItemTooltip = new Tooltip(
					"The number of items in the generated unsorted array." + "\nMin: 5\nMax: 100");
			Tooltip waitTimeTooltip = new Tooltip(
					"The waiting time (seconds) between steps during the sorting operation."
							+ "\nIMPORTANT NOTE: Each 'step' probably means any significant changes like swaping or selecting items."
							+ "\nMin: 0 (Any value smaller than 0,001 will be considered as 0)\nMax: 5");
			Tooltip defCoTooltip = new Tooltip("The item default (unselected) color");
			Tooltip selCoTooltip = new Tooltip("The color is used to highlight item(s) during the sorting operation");
			Tooltip btnOpenTooltip = new Tooltip("Open sorting visualization using parameters above");
			lbSortAl.setTooltip(sortAlTooltip);
			cbSortAl.setTooltip(sortAlTooltip);
			lbNumItem.setTooltip(numItemTooltip);
			spNumItem.setTooltip(numItemTooltip);
			lbWaitTime.setTooltip(waitTimeTooltip);
			spWaitTime.setTooltip(waitTimeTooltip);
			lbDefCo.setTooltip(defCoTooltip);
			defaultColorPicker.setTooltip(defCoTooltip);
			lbSelCo.setTooltip(selCoTooltip);
			selectedColorPicker.setTooltip(selCoTooltip);

			root.add(lbSortAl, 0, 0);
			root.add(cbSortAl, 1, 0);
			root.add(lbNumItem, 0, 1);
			root.add(spNumItem, 1, 1);
			root.add(lbWaitTime, 0, 2);
			root.add(spWaitTime, 1, 2);
			root.add(lbDefCo, 0, 3);
			root.add(defaultColorPicker, 1, 3);
			root.add(lbSelCo, 0, 4);
			root.add(selectedColorPicker, 1, 4);
			root.setHalignment(btnOpen, HPos.CENTER);
			root.add(btnOpen, 0, 5, 2, 1);

			Scene scene = new Scene(root);

			primaryStage.setTitle("Sorting Algorithm Visualizer");
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	private static void bubbleSortWindow(int numItems, int milisec, java.awt.Color defCo, java.awt.Color selCo) {
		BubbleSortWorker2 bubbleSortWorker2 = new BubbleSortWorker2(numItems, milisec, defCo, selCo);
		bubbleSortWorker2.createAndShowGUI();
	}

	private static void selectionSortWindow(int numItems, int milisec, java.awt.Color defCo, java.awt.Color selCo) {
		SelectionSortWorker selectionSortWorker = new SelectionSortWorker(numItems, milisec, defCo, selCo);
		selectionSortWorker.createAndShowGUI();
	}

	private static void insertionSortWindow(int numItems, int milisec, java.awt.Color defCo, java.awt.Color selCo) {
		InsertionSortWorker insertionSortWorker = new InsertionSortWorker(numItems, milisec, defCo, selCo);
		insertionSortWorker.createAndShowGUI();
	}

	private static void shellSortWindow(int numItems, int milisec, java.awt.Color defCo, java.awt.Color selCo) {
		ShellSortWorker shellSortWorker = new ShellSortWorker(numItems, milisec, defCo, selCo);
		shellSortWorker.createAndShowGUI();
	}
}
