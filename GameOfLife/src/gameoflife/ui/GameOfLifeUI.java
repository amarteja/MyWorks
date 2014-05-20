package gameoflife.ui;

import gameoflife.GameOfLife;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GameOfLifeUI extends Application
{
	private GameOfLife _GameOfLife;
	private Button[][] buttonArray;
	boolean cells[][] = new boolean[20][20];

	Pane root = new Pane();
	GridPane boardGrid = new GridPane();

	private boolean stop;

	public static void main(String[] args){
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		_GameOfLife = new GameOfLife();
		primaryStage.setTitle("Game Of Life");
		buttonArray = new Button[20][20];

		for(int i = 0; i < 20; i++)
			for(int j = 0; j < 20; j++)
				SetBoard(i, j, boardGrid);

		boardGrid.getMaxWidth();
		boardGrid.getMaxHeight();
		boardGrid.setGridLinesVisible(true);
		initializeScene(primaryStage, boardGrid);
	}

	private void SetBoard(int i, int j, GridPane boardGrid) {
		buttonArray[i][j] = new Button();
		buttonArray[i][j].autosize();
		buttonArray[i][j].setFocusTraversable(false);
		buttonArray[i][j].setMinSize(07, 07);
		buttonArray[i][j].setStyle("-fx-background-color: GREY;");

		buttonArray[i][j].addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) 
			{
				cellClick(event);
			}

		});

		boardGrid.add(buttonArray[i][j], j, i);	

	}

	private void cellClick(MouseEvent event) {

		int row = GridPane.getRowIndex((Node) event.getSource());
		int column = GridPane.getColumnIndex((Node) event.getSource());
		cells[row][column] = _GameOfLife.ALIVE;
		buttonArray[row][column].setStyle("-fx-background-color: YELLOW;");

	}

	private void initializeScene(Stage primaryStage, GridPane boardGrid)
	{
		HBox hbox = new HBox(40);

		Button startButton = new Button("Start");
		Button stopButton = new Button("Stop");

		ComboBox<String> patterns = new ComboBox<String>();
		patterns.getItems().addAll(
				"clear",
				"Glider",
				"Small Exploder",
				"Tumbler",
				"Toad",
				"DieHard",
				"Exploder",
				"10 Cell Row");

		HBox.setHgrow(startButton, Priority.ALWAYS);
		HBox.setHgrow(stopButton, Priority.ALWAYS);
		HBox.setHgrow(patterns, Priority.ALWAYS);
		startButton.setMaxWidth(Double.MAX_VALUE);
		stopButton.setMaxWidth(Double.MAX_VALUE);
		patterns.setMaxWidth(Double.MAX_VALUE);

		hbox.getChildren().addAll(startButton, stopButton, patterns);


		startButton.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>(){
			@Override
			public void handle(final MouseEvent event) {

				stop = false;
				boolean[][] firstPattern = _GameOfLife.nextGeneration(cells);
				startGame(firstPattern);
			}
		});

		stopButton.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>(){
			@Override
			public void handle(final MouseEvent event) {
				stop = true;	
			}
		});


		hbox.setLayoutX(50);
		hbox.setLayoutY(450);
		patterns.valueProperty().addListener(new ChangeListener<String>() {
			@Override 
			public void changed(ObservableValue<? extends String> ov, String previousPattern, String selectedPattern) {
				selectPatternFromDropDown(selectedPattern);
			}    
		});

		root.getChildren().add(boardGrid);
		root.getChildren().add(hbox);

		Scene scene = new Scene(root, 450, 530, Color.WHITE);

		primaryStage.setScene(scene);
		primaryStage.sizeToScene();
		primaryStage.setResizable(false);
		primaryStage.show();

	}

	private void startGame(final boolean[][] pattern){

		if(!stop){
			updateBoardWithPattern(pattern);

			PauseTransition pauseTransition = new PauseTransition();
			pauseTransition.setDuration(Duration.seconds(0.25));
			pauseTransition.play();
			pauseTransition.onFinishedProperty().set(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					
					boolean[][] nextPattern = _GameOfLife.nextGeneration(pattern);
					updatePattern(nextPattern);
					startGame(nextPattern);

				}
			});
		}
	}

	protected void updatePattern(boolean[][] nextPattern) {

		for(int i = 0; i < 20; i++)
			for(int j = 0; j < 20; j++)
				cells[i][j] = nextPattern[i][j];
	}

	private void updateBoardWithPattern(boolean[][] gridCells) {

		for(int i = 0; i < 20; i++){
			for(int j = 0; j < 20; j++){
				if(gridCells[i][j])
					buttonArray[i][j].setStyle("-fx-background-color: YELLOW;");
				else
					buttonArray[i][j].setStyle("-fx-background-color: GREY;");
			}
		}
	}

	public void selectPatternFromDropDown(String selectedPattern) {

		switch(selectedPattern)
		{
		case "clear"        	 : clearBoard();
										break;

		case "Glider"  			 : int  pattern1[][] = {{8, 5}, {8, 6}, {8, 7}, {7, 7}, {6, 6}};
									makeCellAlive(pattern1);
									break;

		case "Small Exploder"	 : int  pattern2[][] = {{11, 10}, {12, 9}, {12, 10}, {12, 11},
							       {13, 9}, {13, 11}, {14, 10}};
							   	   makeCellAlive(pattern2);
							   	   break;

		case "Tumbler" 			 : int  pattern3[][] = {{10, 10}, {10, 11}, {10, 13}, {10, 14}, {11, 10},
								   {11, 11}, {11, 13}, {11, 14}, {12, 11}, {12, 13},{13, 9}, {13, 11},
								   {13, 13}, {13, 15}, {14, 9}, {14, 11}, {14, 13}, {14, 15}, {15, 9}, 
								   {15, 10}, {15, 15}, {15, 14}};
								   makeCellAlive(pattern3);
								   break;

		case "Toad"    			 : int[][] pattern4 = {{10, 10}, {10, 11}, {10, 12}, {11, 9}, {11, 10}, {11, 11}};
							       makeCellAlive(pattern4);
								   break;

		case "DieHard" 			  : int[][] pattern5 = {{10, 8}, {10, 9}, {11, 9}, {9, 14}, {11, 13}, {11, 14}, {11, 15}};
						           makeCellAlive(pattern5);
						           break;

		case "Exploder"			  : int[][] pattern6 = {{7, 7}, {7, 9}, {7, 11}, {8, 7}, {8, 11}, {9, 7}, {9, 11}, {10, 7},
								    {10, 11}, {11, 7}, {11, 9}, {11, 11}};
									makeCellAlive(pattern6);
						            break;

		case "10 Cell Row"		  : int[][] pattern7 = {{8, 7}, {8, 8}, {8, 9}, {8, 10}, {8, 11}, {8, 12}, {8, 13}, 
						   			{8, 14 }, {8, 5}, {8, 6}};
						   			makeCellAlive(pattern7);
						   			break;
							
		default					  : break;
		
		}
	}

	public void makeCellAlive(int positions[][]){
		clearBoard();
		for(int i = 0; i < positions.length; i++ ){
			cells[positions[i][0]][positions[i][1]] = _GameOfLife.ALIVE;
			buttonArray[positions[i][0]][positions[i][1]].setStyle("-fx-background-color: YELLOW;");
		}

	}

	protected void clearBoard() {
		for(int i = 0; i < 20; i++)
			for(int j = 0; j < 20; j++)
			{
				cells[i][j]=_GameOfLife.DEAD;
				buttonArray[i][j].setStyle("-fx-background-color: GREY;");
			}
	}

}