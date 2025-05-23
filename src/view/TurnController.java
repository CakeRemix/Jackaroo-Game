package view;

import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Queue;

import engine.*;
import engine.board.Board;
import exception.GameException;
import model.Colour;
import model.card.Card;
import model.card.Deck;
import model.card.standard.Ace;
import model.card.standard.Jack;
import model.card.standard.Seven;
import model.card.standard.Standard;
import model.card.standard.Suit;
import model.card.wild.Saver;
import model.player.Marble;
import model.player.Player;
import javafx.animation.FillTransition;
import javafx.animation.PauseTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.application.Platform;
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
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

public class TurnController {
	public static void playButtonPressed() {
		StartBoard.playButton.setOnMouseClicked(e -> onHumanPlay());
	}

	private static void onHumanPlay() {
		Card card = View.player.getSelectedCard();
		
		if (card instanceof Seven && View.player.getSelectedMarbles2() == 2) {
			try {
				View.promptForOneToSix();
			} catch (GameException ex) {
				StartBoard.displayAlert("Game Exception", ex.getMessage());
			}
		}
		Colour winner = View.game.checkWin();
		if (winner != null) {
			showWinnerPopup3(winner);
			return;
		}
		View.updateTurnLabel();
		if (!View.game.canPlayTurn()) {
			View.player.deselectAll();
			try {
				View.game.endPlayerTurn2();
			} catch (GameException f) {
				StartBoard.displayAlert("Game Exception", f.getMessage());
			}
			TurnController.resetScale();

			handleCpuSequence();
		} else {
			if (card == null)
				return;
			handlePlayerTurn();
			TurnController.resetScale();

			handleCpuSequence();
		}
	}

	private static void handlePlayerTurn() {
		try {
			View.game.playPlayerTurn();
		} catch (GameException e) {
			StartBoard.displayAlert("Game Exception", e.getMessage());
		}

		try {
			View.game.endPlayerTurn2();
		} catch (GameException e) {
			StartBoard.displayAlert("Game Exception", e.getMessage());
		}
	
		updateCells.update();
		Colour w = View.game.checkWin();
		if (w != null) {
			showWinnerPopup3(w);
			return;
		}
		if (Game.discardIndex != -1) {
			if (Game.discardIndex == 0) {
				ifDiscardedFromPlayer();
			} else {
				ifDiscardedFromCpu(Game.discardIndex);
			}
		}
		
		Game.discardIndex = -1;

		if (Board.isTrapInPath!=-1) {
			StartBoard.displayAlert("TrapCell", "Player have fallen in a trap cell.");
			playTrapShakeAnimation(View.boardCircles.get(Board.isTrapInPath));
			Board.isTrapInPath = -1;
		}
		System.out.println("Human hand now:");
		View.player.getHand().forEach(
				c -> System.out.println("  " + c.getImageCode()));
	}
	public static void resetScale(){
		Player player = View.player;
		for(Marble marble:player.getMarbles()){
			marble.getIcon().setScaleX(1);
			marble.getIcon().setScaleY(1);

		}
		 player = View.players.get(1);
		for(Marble marble:player.getMarbles()){
			marble.getIcon().setScaleX(1);
			marble.getIcon().setScaleY(1);

		}
		player = View.players.get(2);
		for(Marble marble:player.getMarbles()){
			marble.getIcon().setScaleX(1);
			marble.getIcon().setScaleY(1);

		}
		player = View.players.get(3);
		for(Marble marble:player.getMarbles()){
			marble.getIcon().setScaleX(1);
			marble.getIcon().setScaleY(1);

		}
		ArrayList<Marble> player2 = View.game.getBoard().getActionableMarbles();
		for(Marble marble:player2){
			marble.getIcon().setScaleX(1);
			marble.getIcon().setScaleY(1);

		}
	}
	public static void playTrapShakeAnimation(Circle playerToken) {
	    /*TranslateTransition shake = new TranslateTransition(Duration.millis(50), playerToken);
	    shake.setByX(10);
	    shake.setCycleCount(6);
	    shake.setAutoReverse(true);
	    shake.play();*/
		double x = playerToken.getCenterX();
		double y = playerToken.getCenterY();
		
		Circle newC = new Circle(12,View.CellColour);
		newC.setCenterX(x);
		newC.setCenterY(y);
		playerToken.disableProperty();
	    FillTransition flash = new FillTransition(Duration.millis(300), playerToken, Color.WHITE, Color.RED);
	    flash.setCycleCount(2);
	    flash.setAutoReverse(true);
	    flash.play();

	}
	private static void handleCpuSequence() {
		Queue<Integer> queue = new ArrayDeque<>();
		queue.add(1);
		queue.add(2);
		queue.add(3);
		playNextCpuTurn(queue);
	}

	private static void playNextCpuTurn(Queue<Integer> cpuQueue) {
		if(cpuQueue.isEmpty()) {
			if(!View.game.canPlayTurn()){
				try{
					View.game.endPlayerTurn2();
				}catch(GameException e){
					StartBoard.displayAlert("Game Exception", e.getMessage());
				}
				cpuQueue.add(1);
				cpuQueue.add(2);
				cpuQueue.add(3);
				playNextCpuTurn(cpuQueue);
			}
			return;
		
		}
		Integer playerIdx = cpuQueue.poll();
		Colour w = View.game.checkWin();
		if (w != null) {
			showWinnerPopup3(w);
			
			return;
		}
		
		PauseTransition delay = new PauseTransition(Duration.seconds(1.3));
		delay.setOnFinished(evt -> {

			if (View.game.canPlayTurn()) {
				try {
					View.game.playPlayerTurn();
					View.game.endPlayerTurn2();
					
				} catch (GameException ex) {
					
					System.err.println("Error on CPU turn for player "
							+ playerIdx);
					StartBoard.displayAlert("Game Exception", ex.getMessage());
				}
				switch (playerIdx) {
				case 1:
					removeOneCardView(View.leftPlayer);
					break;
				case 2:
					removeOneCardView(View.topPlayer);
					break;
				case 3:
					removeOneCardView(View.rightPlayer);
					break;
				}
				if (Game.discardIndex != -1) {
					if (Game.discardIndex == 0) {
						ifDiscardedFromPlayer();
					} else {
						ifDiscardedFromCpu(Game.discardIndex);
					}
				}
			
				Game.discardIndex = -1;

				updateCells.update();

				if (Board.isTrapInPath!=-1) {
					System.out.println("TRAP for "
							+ View.players.get(playerIdx).getName());
					playTrapShakeAnimation(View.boardCircles.get(Board.isTrapInPath));
					Board.isTrapInPath = -1;
				}
				
			}else{ 

				try {
					View.game.endPlayerTurn2();
				} catch (GameException ex) {
					StartBoard.displayAlert("Game Exception", ex.getMessage());
				}
			}
			View.updateTurnLabel();
			TurnController.resetScale();

			playNextCpuTurn(cpuQueue);

		});
		delay.play();
	}

	public static void ifDiscardedFromPlayer() {
		for (int i = 0; i < View.bottomPlayer.getChildren().size(); i++) {
			ImageView discarded = (ImageView) View.bottomPlayer.getChildren()
					.get(i);
			Card discardedCard = ControlViewCards.inverted.get(discarded);
			if (!View.player.getHand().contains(discardedCard)) {
				View.bottomPlayer.getChildren().remove(i);
			}
		}
		Game.discardIndex = -1;
	}

	public static void ifDiscardedFromCpu(int index) {
		switch (index) {
		case 1:
			removeOneCardView(View.leftPlayer);
			break;
		case 2:
			removeOneCardView(View.topPlayer);
			break;
		case 3:
			removeOneCardView(View.rightPlayer);
			break;
		}
		Game.discardIndex = -1;
	}

	/*
	 * public static void playButtonPressed() throws GameException {
	 * 
	 * StartBoard.playButton.setOnMouseClicked(e -> {
	 * 
	 * Card card = View.player.getSelectedCard(); if (card instanceof Seven &&
	 * View.player.getSelectedMarbles2() == 2) { try { View.promptForOneToSix();
	 * } catch (GameException e1) { // TODO Auto-generated catch block
	 * System.out.println(e1.toString()); } }
	 * 
	 * System.out.println("pressed");
	 * 
	 * if (View.game.checkWin() == null) { if (!View.game.canPlayTurn()) {
	 * View.player.deselectAll(); } else { if (card == null) { return; }
	 * 
	 * try { View.game.playPlayerTurn(); } catch (Exception e1) { // TODO
	 * Auto-generated catch block System.out.println(e1.toString()); } // marble
	 * update
	 * 
	 * // StartBoard.displayAlert("", g.getMessage());
	 * 
	 * try { View.game.endPlayerTurn2(); } catch (Exception e1) { // TODO
	 * Auto-generated catch block System.out.println(e1.toString()); }
	 * 
	 * } System.out.println("player:"); for(int i
	 * =0;i<View.player.getHand().size();i++){
	 * System.out.println(View.player.getHand().get(i).getName()); }
	 * if(Board.isTrapInPath){ System.out.println("TRAAAAAAP CELL PLAYER");
	 * Board.isTrapInPath = false; } ArrayList<Integer> cpuTurns = new
	 * ArrayList<>(Arrays .asList(1, 2, 3)); try {
	 * playCpuTurnsSequentially(cpuTurns, 0); } catch (Exception e1) { // TODO
	 * Auto-generated catch block System.out.println(e1.toString()); }
	 * 
	 * } else { winnerWinnerChickenDinner(View.game.checkWin()); }
	 * 
	 * }) ; }
	 * 
	 * public static void playCpuTurnsSequentially(ArrayList<Integer> turns, int
	 * index) throws GameException { if (index >= turns.size()) return;
	 * 
	 * int i = turns.get(index);
	 * 
	 * PauseTransition pause = new PauseTransition(Duration.seconds(1));
	 * pause.setOnFinished(event -> { if (View.game.canPlayTurn()) { try {
	 * View.game.playPlayerTurn(); } catch (GameException e1) {
	 * System.out.println("Game Exception at turn " + i);
	 * System.out.println(e1.getMessage()); }
	 * 
	 * try { View.game.endPlayerTurn2(); } catch (Exception t) {
	 * System.out.println(t);
	 * System.out.println("Exception while removing card view"); }
	 * 
	 * 
	 * updateCells.update(); if(Board.isTrapInPath){
	 * System.out.println("TRAAAAAAP CELL"+View.players.get(i).getName());
	 * Board.isTrapInPath = false; } if (View.game.checkWin() != null) {
	 * winnerWinnerChickenDinner(View.game.checkWin()); } else { try {
	 * playCpuTurnsSequentially(turns, index + 1); } catch (GameException kf) {
	 * System.out.println(kf.toString()); } } }else{ try{
	 * View.game.endPlayerTurn2(); }catch(GameException m){
	 * System.out.println(m.toString()); } } switch (i) { case 1:
	 * removeOneCardView(View.leftPlayer); break; case 2:
	 * removeOneCardView(View.topPlayer); break; case 3:
	 * removeOneCardView(View.rightPlayer); break; }
	 * System.out.println(View.players.get(i).getName()); for(int j
	 * =0;j<View.players.get(i).getHand().size();j++){
	 * System.out.println(View.players.get(i).getHand().get(j).getName()); } });
	 * 
	 * pause.play(); // start the delay }
	 */
	public static void removeOneCardView(HBox handView) {
		try {
			 if (handView.getChildren().isEmpty()) return;
			    int last = handView.getChildren().size() - 1;
			    handView.getChildren().remove(last);
		} catch (Exception e) {
			StartBoard.displayAlert("Game Exception", e.getMessage());
			System.out.println(e.getMessage());
		}
	}

	public static void showWinnerPopup3(Colour wc2) {
		StackPane overlay = new StackPane();
		overlay.setPrefSize(View.gamePane.getWidth(), View.gamePane.getHeight());
		overlay.setStyle("-fx-background-color: rgba(0, 0, 0, 0.6);");

		VBox sign = new VBox(12);
		sign.setAlignment(Pos.CENTER);
		sign.setPadding(new Insets(20));
		sign.setMaxWidth(360);
		sign.setStyle("-fx-background-color: peru;"
				+ "-fx-border-color: saddlebrown, burlywood;"
				+ "-fx-border-width: 6, 3;" + "-fx-border-radius: 12, 8;"
				+ "-fx-background-radius: 12;");
		sign.setEffect(new DropShadow(8, Color.rgb(30, 20, 10, 0.7)));

		try {
			ImageView hat = new ImageView(new Image("/ui/cowboy_hat.png"));
			hat.setFitWidth(50);
			hat.setPreserveRatio(true);
			sign.getChildren().add(hat);
		} catch (Exception ignored) {
		}

		Label title = new Label("🤠 Winner! 🤠");
		title.setFont(Font.font("Rockwell", FontWeight.BOLD, 32));
		sign.getChildren().add(title);

		String colorName = wc2.toString(); // e.g. "RED", "BLUE"
		Label colorLabel = new Label("Player Color: " + colorName);
		colorLabel.setFont(Font.font("Georgia", FontWeight.SEMI_BOLD, 20));

		Color wc = Color.valueOf(colorName);
		colorLabel
				.setTextFill(wc.grayscale().getBrightness() < 0.5 ? Color.WHITE
						: Color.BLACK);
		sign.getChildren().add(colorLabel);

		Region accent = new Region();
		accent.setPrefSize(300, 8);
		String hex = String.format("#%02X%02X%02X", (int) (wc.getRed() * 255),
				(int) (wc.getGreen() * 255), (int) (wc.getBlue() * 255));
		accent.setStyle("-fx-background-color: " + hex + ";"
				+ "-fx-background-radius: 4;");
		sign.getChildren().add(accent);

		Button exitBtn = new Button("Exit Game");
		exitBtn.setFont(Font.font("Georgia", FontWeight.BOLD, 18));
		exitBtn.setStyle("-fx-background-color: saddlebrown;"
				+ "-fx-text-fill: white;" + "-fx-background-radius: 6;"
				+ "-fx-padding: 8 16;");
		exitBtn.setOnAction(e -> Platform.exit());
		sign.getChildren().add(exitBtn);

		overlay.getChildren().add(sign);
		View.gamePane.getChildren().add(overlay);

		sign.setTranslateY(-View.gamePane.getHeight());
		TranslateTransition slide = new TranslateTransition(
				Duration.seconds(0.8), sign);
		slide.setToY(0);
		slide.setCycleCount(1);
		slide.play();
	}
}
