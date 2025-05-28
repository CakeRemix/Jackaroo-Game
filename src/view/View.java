package view;

import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import javafx.scene.input.KeyCode;
import engine.*;
import engine.board.Board;
import engine.board.Cell;
import engine.board.SafeZone;
import exception.GameException;
import model.Colour;
import model.card.Card;
import model.card.Deck;
import model.card.standard.Ace;
import model.card.standard.Jack;
import model.card.standard.Standard;
import model.card.standard.Suit;
import model.card.wild.Saver;
import model.player.Marble;
import model.player.Player;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.transform.Rotate;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class View extends Application {
	private MediaPlayer backgroundMediaPlayer; 
	public static StackPane gamePane = new StackPane();
	public static Game game;
	public static AnchorPane gameroot;
	public static String playerName;
	public static ArrayList<Player> players;
	public static ArrayList<ArrayList<Pair>> safeZoneCenter = new ArrayList<>();
	public static ArrayList<Circle> track = new ArrayList<>();
	public static ArrayList<ArrayList<Pair>> homeIndexToPoint;
	public static ArrayList<Color> colOrder = new ArrayList<>();
	public static AnchorPane boardCells;
	public static Color CellColour = Color.BLACK;
	/** maps **/
	public static ArrayList<Pair> indexToPoint = new ArrayList<>();
	public static TreeMap<Pair, Button> pointToButton = new TreeMap<>();
	public static TreeMap<Button, Pair> buttonToPoint = new TreeMap<>();
	public static TreeMap<Pair, Integer> pointToIndex = new TreeMap<>();
	public static HashMap<Cell,Pair> cellToPoint = new HashMap<>();
	public static StackPane firePit;
	public static AnchorPane marblesPane;
	// Cards
	public static HBox topPlayer = new HBox(10);
	public static HBox rightPlayer = new HBox(10);
	public static HBox leftPlayer = new HBox(10);
	public static Circle minionRed = new Circle(25);
	public static Circle minionBlue = new Circle(25);
	public static Circle minionYellow = new Circle(25);
	public static Circle minionGreen = new Circle(25);
	
	public static HBox bottomPlayer = new HBox(10);
	public static AnchorPane playPane;
	public static final double WIDTH = Screen.getPrimary().getVisualBounds()
			.getWidth();
	public static final double HEIGHT = Screen.getPrimary().getVisualBounds()
			.getHeight();
	public static Scene GameScene;
	public static StackPane splitDistancePane;
	public static int split;
	public static ArrayList<Cell> trackCells;
	public static ArrayList<SafeZone> safeZones;
	public static ArrayList<ArrayList<Marble>> homeZones;
	public static AnchorPane cellsPane;
	public static ImageView playerIconImage;
	public static String iconIndex;
	public static Player player;
	public static Button deselectAll;
	public static Label turnLabel;
	public static Image playerImage;
	public static Stage temp;
	public static ArrayList<Circle> boardCircles;
	public static void supercellificate(){
		for (int i=0; i<boardCircles.size();i++){
			if (i%25==0)
				boardCircles.get(i).setStroke(colOrder.get(i/25));
			boardCircles.get(i).setFill(new ImagePattern(StartBoard.supercell));
		}
	}
	private void addAnimatedWaves() {
	    Image waveImage0 = new Image("/media/wave0.png");
	    ImageView wave0 = new ImageView(waveImage0);
	    wave0.setFitWidth(WIDTH);
	    wave0.setPreserveRatio(true);
	    wave0.setOpacity(0.6);
	    wave0.setTranslateY(HEIGHT);
	    wave0.setScaleX(1.0);
	    wave0.setScaleY(1.0);
	    TranslateTransition rise0 = new TranslateTransition(Duration.seconds(8), wave0);
	    rise0.setFromY(HEIGHT);
	    rise0.setToY(-waveImage0.getHeight());
	    rise0.setCycleCount(TranslateTransition.INDEFINITE);
	    rise0.setInterpolator(Interpolator.LINEAR);
	    rise0.setAutoReverse(false);
	    gamePane.getChildren().add(wave0);
	    StackPane.setAlignment(wave0, Pos.BOTTOM_CENTER);
	    rise0.play();

	    Image waveImage1 = new Image("/media/wave1.png");
	    ImageView wave1 = new ImageView(waveImage1);
	    wave1.setFitWidth(WIDTH);
	    wave1.setPreserveRatio(true);
	    wave1.setOpacity(0.5);
	    wave1.setTranslateY(HEIGHT + 100);
	    wave1.setScaleX(1.2);
	    wave1.setScaleY(1.2);
	    TranslateTransition rise1 = new TranslateTransition(Duration.seconds(12), wave1);
	    rise1.setFromY(HEIGHT + 100);
	    rise1.setToY(-waveImage1.getHeight() * 1.2);
	    rise1.setCycleCount(TranslateTransition.INDEFINITE);
	    rise1.setInterpolator(Interpolator.LINEAR);
	    rise1.setAutoReverse(false);
	    gamePane.getChildren().add(wave1);
	    StackPane.setAlignment(wave1, Pos.BOTTOM_CENTER);
	    rise1.play();

	    Image waveImage2 = new Image("/media/wave2.png");
	    ImageView wave2 = new ImageView(waveImage2);
	    wave2.setFitWidth(WIDTH);
	    wave2.setPreserveRatio(true);
	    wave2.setOpacity(0.4);
	    wave2.setTranslateY(HEIGHT + 200);
	    wave2.setScaleX(1.4);
	    wave2.setScaleY(1.4);
	    TranslateTransition rise2 = new TranslateTransition(Duration.seconds(16), wave2);
	    rise2.setFromY(HEIGHT + 200);
	    rise2.setToY(-waveImage2.getHeight() * 1.4);
	    rise2.setCycleCount(TranslateTransition.INDEFINITE);
	    rise2.setInterpolator(Interpolator.LINEAR);
	    rise2.setAutoReverse(false);
	    gamePane.getChildren().add(wave2);
	    StackPane.setAlignment(wave2, Pos.BOTTOM_CENTER);
	    rise2.play();
	}
	@Override
	public void start(Stage primaryStage) throws Exception {
		Image bgImage = new Image("/media/main_menu.jpg");
		ImageView bgImageView = new ImageView(bgImage);
		bgImageView.setFitWidth(1980);
		bgImageView.setFitHeight(1080);
		bgImageView.setPreserveRatio(false);
		addAnimatedWaves();
		MediaPlayer bgMusicPlayer = new MediaPlayer(
		        new Media(getClass().getResource("/media/Audio/main_menu_music.mp3").toExternalForm())
		    );
		bgMusicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
		bgMusicPlayer.play();
		Button startButton = new Button("Start");

		startButton.setPrefSize(313, 100);
		startButton
				.setFont(Font.font("Cinzel Decorative", FontWeight.BOLD, 32));
		Image image = new Image(getClass().getResource("/media/menu_button.png").toExternalForm());

		BackgroundImage bgimage = new BackgroundImage(
		    image,
		    BackgroundRepeat.NO_REPEAT,
		    BackgroundRepeat.NO_REPEAT,
		    BackgroundPosition.CENTER,
		    new BackgroundSize(313, 100, false, false, false, false)
		);

		startButton.setBackground(new Background(bgimage));
		startButton.setText(""); // Remove text, or overlay if desired
		startButton.setPrefSize(313, 100); // Match image size
		startButton.setTextFill(Color.WHITE);

		Font font = Font.font("Open Sans", FontWeight.MEDIUM, 18);

		Label textLabel = new Label("Enter your name");
		textLabel.setFont(Font.font("Georgia", FontWeight.BOLD, 22));
		textLabel.setVisible(false);
		TextField nameField = new TextField();
		nameField.setPrefWidth(200);

		nameField.setMaxSize(300, 50);
		nameField.setVisible(false);
		nameField.setFont(font);

		startButton.setOnAction(e -> {
			
			startButton.setVisible(false);
			nameField.setVisible(true);
			textLabel.setVisible(true);
			nameField.getStyleClass().add("text-field");
			startButton.getStyleClass().add("button:disabled");

			nameField.setOnKeyPressed(k -> {
				textLabel.setVisible(false);

				if (k.getCode().toString().equals("ENTER")) {
					bgMusicPlayer.stop();
					playerName = nameField.getText();
					nameField.setVisible(false);
					textLabel.setVisible(false);
					iconSelectionScene(primaryStage, playerName);
					
					/*try {
						gameScene(primaryStage, playerName);
					} catch (Exception e1) {
						e1.printStackTrace();
					}*/
				}
			});

		});

		// Pane
		StackPane root = new StackPane();
		root.getChildren().addAll(bgImageView, startButton, nameField,
				textLabel);

		// Main scene
		Scene scene = new Scene(root, WIDTH, HEIGHT);
		scene.getStylesheets().add(
				getClass().getResource("visuals.css").toExternalForm());

		// Main Stage
		primaryStage.setFullScreenExitHint("");
		primaryStage.setFullScreen(true);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public void gameScene(Stage primaryStage, String playerName)
			throws IOException, GameException {
		// Initialize the game
		Image bgImage = new Image("runeterra7.png");
		ImageView bgImageView = new ImageView(bgImage);
		bgImageView.setFitWidth(1980);
		bgImageView.setFitHeight(1080);
		bgImageView.setPreserveRatio(false);
		// Intialise the game
		game = new Game(playerName);
		players = game.getPlayers();
		player = players.get(0);
		ControlViewCards.loadFromResources();

		// Board layout
		boardCircles = new ArrayList<>();
		gameroot = new AnchorPane();
		gamePane.getChildren().add(gameroot);
		// Track
		// Translate the colour order of the marbles
		StartBoard.createTheColorOrder();
		/** boardCells **/
		
		AnchorPane boardCells = new AnchorPane();
		int startx = 880;
		int starty = 853;
		int sk = 25;
		StartBoard.createTrack(startx, starty, sk);
		/** for safezone position **/
		StartBoard.createSafeZone(sk);
		/** homecells positin **/
		homeIndexToPoint = new ArrayList<>();
		playMusic();
		for (int i = 0; i < 4; i++)
			homeIndexToPoint.add(new ArrayList<>());

		for (int i = 0; i < 4; i++)
			StartBoard.createHome(i, sk);

		boardCells.getChildren().addAll(track);
		gameroot.getChildren().add(boardCells);
	
		marblesPane = new AnchorPane();
		minionRed.setFill(new ImagePattern(new Image("/media/minion1.png")));
		minionBlue.setFill(new ImagePattern(new Image("/media/minion2.png")));
		minionYellow.setFill(new ImagePattern(new Image("/media/minion3.png")));
		minionGreen.setFill(new ImagePattern(new Image("/media/minion4.png")));
		
		gameroot.getChildren().add(minionRed);
		gameroot.getChildren().add(minionBlue);
		gameroot.getChildren().add(minionGreen);
		gameroot.getChildren().add(minionYellow);
		minionRed.setVisible(false);
		minionBlue.setVisible(false);
		minionYellow.setVisible(false);
		minionGreen.setVisible(false);

		firePit = new StackPane();
		gamePane.getChildren().add(firePit);
		//firePit.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
		firePit.setMaxSize(100, 200);
		firePit.setAlignment(Pos.CENTER);


		gamePane.getChildren().add(0, bgImageView);
		gamePane.setPadding(new Insets(30));

		firePit.toFront();

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				MarbleSelector.selectMarble(game.getPlayers().get(i)
						.getMarbles().get(j));
			}
		}
	
		updateCells.update();
		gamePane.getChildren().addAll(marblesPane);
		StartBoard.createPlayButton();
		TurnController.playButtonPressed();
		supercellificate();
		// bottomPlayer.setStyle("-fx-background-color: yellow;");

		// gamePane.getChildren().add(playerIcon);
		// StackPane.setAlignment(playerIcon, Pos.BOTTOM_LEFT);
		double playerBoxWidth = 150;
		double playerBoxHeight = 130;
		leftPlayer.setPrefSize(playerBoxWidth, playerBoxHeight);
		leftPlayer.setMinSize(playerBoxWidth, playerBoxHeight);
		leftPlayer.setMaxSize(playerBoxWidth, playerBoxHeight);
		leftPlayer.setAlignment(Pos.CENTER);
		rightPlayer.setPrefSize(playerBoxWidth, playerBoxHeight);
		rightPlayer.setMinSize(playerBoxWidth, playerBoxHeight);
		rightPlayer.setMaxSize(playerBoxWidth, playerBoxHeight);
		rightPlayer.setAlignment(Pos.CENTER);
		topPlayer.setPrefSize(playerBoxWidth, playerBoxHeight);
		topPlayer.setMinSize(playerBoxWidth, playerBoxHeight);
		topPlayer.setMaxSize(playerBoxWidth, playerBoxHeight);
		topPlayer.setAlignment(Pos.CENTER);
		bottomPlayer.setPrefSize(playerBoxWidth, playerBoxHeight);
		bottomPlayer.setMinSize(playerBoxWidth, playerBoxHeight);
		bottomPlayer.setMaxSize(playerBoxWidth, playerBoxHeight);
		bottomPlayer.setAlignment(Pos.CENTER);
		StackPane.setAlignment(leftPlayer, Pos.TOP_LEFT);
		StackPane.setMargin(leftPlayer, new Insets(150, 0, 0, 0)); // top, right, bottom, left
		StackPane.setAlignment(topPlayer, Pos.TOP_RIGHT);
		StackPane.setMargin(topPlayer, new Insets(0, 300, 0, 0));
		StackPane.setAlignment(rightPlayer, Pos.BOTTOM_RIGHT);
		StackPane.setMargin(rightPlayer, new Insets(0, 0, 300, 0));
		StackPane.setAlignment(bottomPlayer, Pos.BOTTOM_LEFT);
		StackPane.setMargin(bottomPlayer, new Insets(0, 0, 0, 350));

		rightPlayer.setRotate(-90);
		leftPlayer.setRotate(90);
		topPlayer.setRotate(180);
		gamePane.getChildren().addAll(topPlayer, bottomPlayer, rightPlayer,
				leftPlayer);
		
		distributeCards(players);
		deselectButton();
		createTurnLabel();
		
		
		VBox topLeft = createPlayerIndicator(players.get(1));
		StackPane.setAlignment(topLeft, Pos.TOP_LEFT);
		topLeft.setTranslateX(500);
		topLeft.setTranslateY(140);
		
		VBox topRight = createPlayerIndicator(players.get(2));
		StackPane.setAlignment(topRight, Pos.TOP_RIGHT);
		StackPane.setMargin(topRight, new Insets(10));
		topRight.setTranslateX(-500);
		topRight.setTranslateY(140);

		VBox bottomLeft = createPlayerIndicator(players.get(0));
		StackPane.setAlignment(bottomLeft, Pos.BOTTOM_LEFT);
		StackPane.setMargin(bottomLeft, new Insets(10));
		bottomLeft.setTranslateX(480);
		bottomLeft.setTranslateY(-160);

		VBox bottomRight = createPlayerIndicator(players.get(3));
		StackPane.setAlignment(bottomRight, Pos.BOTTOM_RIGHT);
		StackPane.setMargin(bottomRight, new Insets(10));
		bottomRight.setTranslateX(-500);
		bottomRight.setTranslateY(-160);
		
		
	
		gamePane.getChildren().addAll(topLeft, topRight, bottomRight,
				bottomLeft);
		
		GameScene = new Scene(gamePane, WIDTH, HEIGHT, Color.GREEN);
		GameScene.getStylesheets().add(
				getClass().getResource("visuals.css").toExternalForm());
		GameScene.setOnKeyPressed(e->{
			if (e.getCode() == KeyCode.Q) {
				System.out.println("q");
				try {
					Marble marble = players.get(0).getMarbles().get(0);
					game.getBoard().sendToBase(marble);
					players.get(0).getMarbles().remove(0);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					StartBoard.displayAlert("GameException",e1.getMessage());
				}
			}else if  (e.getCode() == KeyCode.W) {
				try {
					Marble marble = players.get(1).getMarbles().get(0);
					game.getBoard().sendToBase(marble);
					players.get(1).getMarbles().remove(0);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					StartBoard.displayAlert("GameException",e1.getMessage());
				}
			}else if  (e.getCode() == KeyCode.E) {
				try {
					Marble marble = players.get(2).getMarbles().get(0);
					game.getBoard().sendToBase(marble);
					players.get(2).getMarbles().remove(0);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					StartBoard.displayAlert("GameException",e1.getMessage());
				}
			}else if  (e.getCode() == KeyCode.R) {
				try {
					Marble marble = players.get(3).getMarbles().get(0);
					game.getBoard().sendToBase(marble);
					players.get(3).getMarbles().remove(0);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					StartBoard.displayAlert("GameException",e1.getMessage());
				}
			}
			updateCells.update();
		});
		temp = primaryStage;
		primaryStage.setFullScreenExitHint("");
		primaryStage.setScene(GameScene);
		primaryStage.setFullScreen(true);

	}
	
	public static void createTurnLabel(){
		turnLabel = new Label("🎯 Current: "+players.get(game.getCurrentPlayerIndex1()).getName()+"  ⏭ Next: "+players.get(game.getNextPlayerIndex1()).getName());
		turnLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
		turnLabel.setTextFill(Color.WHITE);
		turnLabel.setBackground(new Background(new BackgroundFill(Color.web("#2c2c2c"), new CornerRadii(5), Insets.EMPTY)));
		turnLabel.setPadding(new Insets(8));
		StackPane bottomRightPane = new StackPane(turnLabel);
		bottomRightPane.setPadding(new Insets(0));
		bottomRightPane.setAlignment(Pos.BOTTOM_RIGHT);
		StackPane.setAlignment(turnLabel, Pos.BOTTOM_RIGHT);
		turnLabel.setLayoutX(200);
		turnLabel.setLayoutY(1000);

		bottomRightPane.setMaxSize(Region.USE_PREF_SIZE,Region.USE_PREF_SIZE);
		
		StackPane.setMargin(turnLabel, new Insets(10, 150, 10, 10));
		gamePane.getChildren().add(turnLabel);
	}
	
	public static MediaPlayer mediaPlayer;
    public void playMusic() {
        Media media = new Media(new File("resources/media/Audio/back_theme_"+iconIndex+".mp3").toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.setVolume(0.4);
        mediaPlayer.play();
    }

    
	public static void updateTurnLabel(){
		turnLabel.setText("🎯 Current: "+players.get(game.getCurrentPlayerIndex1()).getName()+"  ⏭ Next: "+players.get(game.getNextPlayerIndex1()).getName());

	}
	public static void deselectButton() {
		deselectAll = new Button("deselect all");
		deselectAll.getStyleClass().add("desert-button");
		deselectAll.setTextFill(Color.BLACK);

		deselectAll.setLayoutX(700);
		deselectAll.setLayoutY(807);
		deselectAll.getStyleClass().add("deselect-btn");
		Pane fixedPositionPane2 = new Pane();
		fixedPositionPane2.setPickOnBounds(false);
		fixedPositionPane2.getChildren().add(deselectAll);

		gamePane.getChildren().add(fixedPositionPane2);
		deselectAll.setOnMouseClicked(e -> {
			player.deselectAll2();
			TurnController.resetScale();
		});
	}

	private static VBox createPlayerIndicator(Player player) {
		Color fxColor = Color.valueOf(player.getColour().toString());

		Circle circle = new Circle(50, fxColor);
		circle.setStroke(Color.DARKGRAY);
		circle.setStrokeWidth(2);
		
		Rectangle background = new Rectangle(120, 40);
	    background.setArcWidth(15);
	    background.setArcHeight(15);
        background.setFill(Color.DARKSLATEBLUE);

		Label nameLabel = new Label(player.getName());
		nameLabel.getStyleClass().add("name-label");

		nameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 36));
		nameLabel.setTextFill(fxColor);
		Random rand = new Random();
		
		switch(player.getName()){
		case "CPU 1":
			circle.setFill(new ImagePattern(new Image("/cpuIcons/icon5.png"), 0, 0, 1, 1, true));
			break;
		case "CPU 2":
			circle.setFill(new ImagePattern(new Image("/cpuIcons/icon6.png"), 0, 0, 1, 1, true));
			break;
		case "CPU 3":
			circle.setFill(new ImagePattern(new Image("/cpuIcons/icon7.png"), 0, 0, 1, 1, true));
			break;
		default:
			circle.setFill(new ImagePattern(playerIconImage.getImage(), 0, 0, 1, 1, true));
			break;
		}
		VBox box = new VBox(0, circle, nameLabel);
		box.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
		// box.setAlignment(Pos.CENTER);
		return box;
	}

	@SuppressWarnings("static-access")
	public static void distributeCards(ArrayList<Player> players)
			throws GameException {
		ArrayList<ImageView> player = viewCardsFor(players.get(0));
		for (int i = 0; i < player.size(); i++) {
			ImageView iv = player.get(i);
			CardSelector.makeSelectable(iv);
			iv.setMouseTransparent(false);
			bottomPlayer.getChildren().add(iv);
		}
		for (Card card : players.get(0).getHand()) {
		    ImageView cardView = ControlViewCards.cards.get(card);
		    String view;
		    if(card instanceof Standard){
		    	view = card.getName()+"\n"+card.getDescription()+"\n"+((Standard)card).getSuit().toString();
		    }else{
		    	view = card.getName()+"\n"+card.getDescription();
		    }
		    Tooltip tooltip = new Tooltip(view);

		    // Optional: Style the tooltip
		    tooltip.setStyle("-fx-font-size: 12px; -fx-background-color: black; -fx-text-fill: white;");

		    // Install the tooltip on the card view
		    Tooltip.install(cardView, tooltip);
		}
		// addPlayerHeader(leftPlayer, players.get(1));

		ArrayList<ImageView> cpu1 = viewCardsFor(players.get(1));

		for (int i = 0; i < cpu1.size(); i++) {
			ImageView iv = ControlViewCards.getBackCard("blue");

			iv.setFitWidth(100);
			iv.setPreserveRatio(true);
			iv.setMouseTransparent(false);

			leftPlayer.getChildren().add(iv);
		}
		leftPlayer.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
		rightPlayer.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
		topPlayer.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
		bottomPlayer.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);

		ArrayList<ImageView> cpu2 = viewCardsFor(players.get(2));
		// addPlayerHeader(topPlayer, players.get(2));

		Circle playerIcon = new Circle(40);
		playerIcon.setFill(Color.BLACK);
		for (int i = 0; i < cpu2.size(); i++) {
			ImageView iv = ControlViewCards.getBackCard("yellow");

			iv.setFitWidth(100);
			iv.setPreserveRatio(true);

			topPlayer.getChildren().add(iv);
		}
		ArrayList<ImageView> cpu3 = viewCardsFor(players.get(3));
		// addPlayerHeader(rightPlayer, players.get(3));
		for(ImageView im:cpu3){
			System.out.println(im);
		}
		for (int i = 0; i < cpu3.size(); i++) {
			ImageView iv = ControlViewCards.getBackCard("green");

			iv.setFitWidth(100);
			iv.setPreserveRatio(true);

			rightPlayer.getChildren().add(iv);
		}

	}

	public static void clearHboxesAndDistribute() throws GameException {
		topPlayer.getChildren().clear();
		rightPlayer.getChildren().clear();
		bottomPlayer.getChildren().clear();
		leftPlayer.getChildren().clear();
		distributeCards(players);
	}

	public static boolean containsNonInteger(String input) {
		return !input.matches("\\d+");
	}

	public static void promptForOneToSix() throws GameException {
		final int[] result = { -1 };

		// 1) Create modal dialog
		Stage dialog = new Stage(StageStyle.UTILITY);
		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.setTitle("Enter a number (1–6)");

		// 2) Build UI
		Label prompt = new Label("Please enter an integer from 1 to 6:");
		TextField input = new TextField();
		input.setMaxWidth(60);

		Button ok = new Button("OK");
		ok.setDefaultButton(true);
		ok.setOnAction(e -> {
			String text = input.getText().trim();
			try {
				int val = Integer.parseInt(text);
				if (val < 1 || val > 6) {
					throw new IllegalArgumentException(
							"Number must be between 1 and 6");
				}
				result[0] = val;
				dialog.close();
			} catch (Exception ex) {
				StartBoard.displayAlert("Invalid entry", ex.getMessage());
			}
		});

		Button cancel = new Button("Cancel");
		cancel.setCancelButton(true);
		cancel.setOnAction(e -> {
			dialog.close();
		});

		VBox root = new VBox(10, prompt, input, ok, cancel);
		root.setAlignment(Pos.CENTER);
		root.setPadding(new Insets(15));

		dialog.setScene(new Scene(root));
		dialog.showAndWait();

		game.editSplitDistance(result[0]);
	}

	public static int splitDistanceField() {
		splitDistancePane = new StackPane();
		TextField splitDistance = new TextField();
		splitDistance.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
		Label splitDistanceLabel = new Label("Enter split distance");
		splitDistancePane.getChildren().addAll(splitDistance,
				splitDistanceLabel);
		StackPane.setAlignment(splitDistance, Pos.BOTTOM_LEFT);
		splitDistancePane.setAlignment(Pos.BOTTOM_RIGHT);
		splitDistancePane
				.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
		gamePane.getChildren().add(splitDistancePane);
		splitDistance
				.setOnKeyPressed(e -> {
					if (e.getCode().toString().equals("ENTER")) {
						try {
							if (containsNonInteger(splitDistance.getText()))
								throw new Exception(
										"Split distance has to be integer");
							else {
								split = Integer.parseInt(splitDistance
										.getText());
							}
						} catch (Exception s) {
							StartBoard.displayAlert("Invalid move",
									s.getMessage());

						}
					}

				});
		gamePane.getChildren().remove(gamePane.getChildren().size() - 1);
		return split;
	}

	public static void makeInteractive(ImageView iv, ArrayList<ImageView> views) {
		iv.getProperties().put("wasClicked", false);
		iv.setCursor(Cursor.HAND);

		iv.setOnMouseClicked(e -> {
			boolean clicked = (Boolean) iv.getProperties().get("wasClicked");
			if (!clicked) {
				iv.setScaleX(1.1);
				iv.setScaleY(1.1);
				iv.getProperties().put("wasClicked", true);
			}
		});

		iv.setOnMouseEntered(e -> {
			boolean clicked = (Boolean) iv.getProperties().get("wasClicked");
			if (!clicked) {
				iv.setEffect(new DropShadow(10, Color.rgb(0, 170, 255, 0.8)));
				iv.setScaleX(1.1);
				iv.setScaleY(1.1);
			}
		});
		iv.setOnMouseExited(e -> {
			boolean clicked = (Boolean) iv.getProperties().get("wasClicked");
			if (!clicked) {
				iv.setEffect(null);
				iv.setScaleX(1);
				iv.setScaleY(1);
			}
		});
	}
	private void cleanupAndStartGame(MediaPlayer mediaPlayer, Stage stage, String playerName) {
	    if (mediaPlayer != null) {
	        mediaPlayer.stop();
	        mediaPlayer.dispose();
	    }
	    try {
	        gameScene(stage, playerName);
	    } catch (IOException | GameException e) {
	        e.printStackTrace();
	    }
	}


	public void playTransitionVideo(Stage primaryStage, String playerName) {
	    String videoPath = getClass().getResource("/video/icon_selected.mp4").toExternalForm();
	    Media media = new Media(videoPath);
	    MediaPlayer mediaPlayer = new MediaPlayer(media);
	    MediaView mediaView = new MediaView(mediaPlayer);

	    mediaView.setFitWidth(1920);
	    mediaView.setFitHeight(1080);

	    StackPane root = new StackPane(mediaView);
	    Scene videoScene = new Scene(root, 1920, 1080);
	    primaryStage.setScene(videoScene);
	    primaryStage.setFullScreen(true);
	    primaryStage.setFullScreenExitHint("");

	    // After video ends, go to game scene
	    mediaPlayer.setOnEndOfMedia(() -> {
	        cleanupAndStartGame(mediaPlayer, primaryStage, playerName);
	    });

	    mediaPlayer.play();  
	}



	public static ArrayList<ImageView> viewCardsFor(Player player) {
		ArrayList<ImageView> views = new ArrayList<>();
		ArrayList<Card> hand = player.getHand();
		for (int i = 0; i < hand.size(); i++) {
			ImageView iv = ControlViewCards.cards.get(hand.get(i));
			iv.setFitWidth(100);
			iv.setPreserveRatio(true);
			views.add(iv);
		}
		return views;
	}
	public void iconSelectionScene(Stage primaryStage, String playerName) {
	    Label promptLabel = new Label("Select your icon:");
	    promptLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
	    promptLabel.setTextFill(Color.WHITE);

	    ArrayList<ImageView> icons = new ArrayList<>();
	    icons.add(new ImageView(new Image("/playerIcons/icon1.png")));
	    icons.add(new ImageView(new Image("/playerIcons/icon2.png")));
	    icons.add(new ImageView(new Image("/playerIcons/icon3.png")));
	    icons.add(new ImageView(new Image("/playerIcons/icon4.png")));
	    icons.add(new ImageView(new Image("/playerIcons/icon5.png")));

	    for (int i = 0; i < icons.size(); i++) {
	        ImageView icon = icons.get(i);
	        icon.setFitWidth(80);
	        icon.setFitHeight(80);
	        icon.setUserData(String.valueOf(i));
	        icon.setCursor(Cursor.HAND);
	        icon.setOnMouseClicked(e -> {
	            try {
	                playerIconImage = icon;
	                iconIndex = (String) icon.getUserData();

	                
	                if (backgroundMediaPlayer != null) {
	                    backgroundMediaPlayer.stop();
	                    backgroundMediaPlayer.dispose();
	                    backgroundMediaPlayer = null;
	                }

	                playTransitionVideo(primaryStage, playerName);
	            } catch (Exception ex) {
	                System.out.println(ex.toString());
	            }
	        });
	    }

	    HBox iconBox = new HBox(20);
	    iconBox.getChildren().addAll(icons);
	    iconBox.setAlignment(Pos.CENTER);

	    VBox content = new VBox(30, promptLabel, iconBox);
	    content.setAlignment(Pos.CENTER);
	    content.setPadding(new Insets(40));

	    
	    Media media = new Media(getClass().getResource("/video/icon_selection.mp4").toExternalForm());
	    backgroundMediaPlayer = new MediaPlayer(media);  // assign to field
	    backgroundMediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Loop forever
	    backgroundMediaPlayer.setAutoPlay(true);

	    MediaView mediaView = new MediaView(backgroundMediaPlayer);
	    mediaView.setPreserveRatio(false);
	    mediaView.setFitWidth(1980);
	    mediaView.setFitHeight(1080);

	    StackPane root = new StackPane();
	    root.getChildren().addAll(mediaView, content); // Video in back, UI on top

	    Scene iconScene = new Scene(root, 1980, 1080);
	    primaryStage.setScene(iconScene);
	    primaryStage.setFullScreen(true);
	    primaryStage.setFullScreenExitHint("");
	    primaryStage.show();
	}

	

	public static void main(String[] args) {
		launch(args);
	}
}
