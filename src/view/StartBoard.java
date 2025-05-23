package view;

import java.util.ArrayList;
import java.util.HashMap;


import exception.GameException;
import model.Colour;
import model.player.Marble;
import model.player.Player;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.image.Image;
public class StartBoard {
	public static Image supercell ;
	public static Button playButton;
	public static void createPlayButton() throws GameException{
		/*
		playButton = new Button("playButton");
		playButton
		.setFont(Font.font("Cinzel Decorative", FontWeight.BOLD, 16));
		playButton.getStyleClass().add("cowboy-button");
		View.playPane = new AnchorPane(playButton);		
		playButton.setMaxSize(100,50);
		playButton.toFront();
		*/
		//playButton.setStyle("desert-button");
		playButton = new Button("PLAY");
		playButton.getStyleClass().add("play-button");
		//playButton.setTextFill(Color.BLACK);

		playButton.setLayoutX(1050);
		playButton.setLayoutY(800);
		Pane fixedPositionPane = new Pane();
		fixedPositionPane.setPickOnBounds(false); 
		fixedPositionPane.getChildren().add(playButton);
		fixedPositionPane.toFront();
		
		View.gamePane.getChildren().add(fixedPositionPane);
		
		

	}
	

	public static void createTheColorOrder() {

		for (Player p : View.game.getPlayers()) {
			Colour col = p.getColour();
			if (col == Colour.RED)
				View.colOrder.add(Color.RED);
			if (col == Colour.BLUE)
				View.colOrder.add(Color.BLUE);
			if (col == Colour.YELLOW)
				View.colOrder.add(Color.YELLOW);
			if (col == Colour.GREEN)
				View.colOrder.add(Color.GREEN);

		}
	}

	public static String toRgbString(Color c) {
		return String.format("rgb(%d,%d,%d)", (int) (c.getRed() * 255),
				(int) (c.getGreen() * 255), (int) (c.getBlue() * 255));
	}








	public static void createHome(int order, int sk) {
		int x = 0;
		int dx = 0;
		int dy = 0;
		if (order == 0) {
			x = 7;
			dx = -200;
		} else if (order == 1) {
			x = 4 + 25;
			dy = -155;
		} else if (order == 2) {
			x = 5 + 25 + 25;
			dx = 190;
		} else {
			x = 4 + 25 + 25 + 25;
			dy = 110;
		}

		int xa = View.indexToPoint.get(x).x + dx;
		int ya = View.indexToPoint.get(x).y + dy;
		Circle cir1 = new Circle(12, View.CellColour);
		cir1.setCenterX(xa);
		cir1.setCenterY(ya);
		cir1.setStroke(View.colOrder.get(order));
		cir1.setStrokeWidth(3);
		View.homeIndexToPoint.get(order).add(new Pair(xa, ya));
		View.track.add(cir1);

		ya += 2 * sk;
		cir1 = new Circle(12, View.CellColour);
		cir1.setCenterX(xa);
		cir1.setCenterY(ya);
		cir1.setStroke(View.colOrder.get(order));
		cir1.setStrokeWidth(3);
		View.homeIndexToPoint.get(order).add(new Pair(xa, ya));
		View.track.add(cir1);

		ya -= sk;
		xa += sk;
		cir1 = new Circle(12, View.CellColour);
		cir1.setCenterX(xa);
		cir1.setCenterY(ya);
		cir1.setStroke(View.colOrder.get(order));
		cir1.setStrokeWidth(3);
		View.homeIndexToPoint.get(order).add(new Pair(xa, ya));
		View.track.add(cir1);

		xa -= 2 * sk;
		cir1 = new Circle(12, View.CellColour);
		cir1.setCenterX(xa);
		cir1.setCenterY(ya);
		cir1.setStroke(View.colOrder.get(order));
		cir1.setStrokeWidth(3);
		View.homeIndexToPoint.get(order).add(new Pair(xa, ya));
		View.track.add(cir1);

	}

	public static void displayAlert(String title, String message) {
		Stage alertStage = new Stage();
		alertStage.setTitle(title);
		
		String imagePath = "/boardI/boardBackGround.png";
		alertStage.initModality(Modality.WINDOW_MODAL);
		alertStage.initOwner(View.temp);
		alertStage.setTitle("Error");
		
		Label label = new Label(message);
		Button closeButton = new Button("Continue");
		closeButton.setOnAction(event -> alertStage.close());

		BorderPane pane = new BorderPane();
		pane.setTop(label);
		pane.setCenter(closeButton);

		Scene scene = new Scene(pane, 500, 100);
		alertStage.setScene(scene);
		alertStage.show();
	}

	public static void createSafeZone(int sk) {
		for (int i = 0; i < 4; i++)
			View.safeZoneCenter.add(new ArrayList<>());
		for (int pos = 23; pos < 100; pos += 25) {
			Pair p = View.indexToPoint.get(pos);
			if (pos == 23) {
				int xx = p.x;
				int yy = p.y;
				for (int j = 1; j <= 4; j++) {
					xx += sk;
					View.safeZoneCenter.get(1).add(new Pair(xx, yy));
					Circle x = new Circle(12, View.CellColour);
					Colour col = View.game.getPlayers().get(1).getColour();

					x.setStroke(View.colOrder.get(1));
					x.setStrokeWidth(3);
					x.setCenterX(xx);
					x.setCenterY(yy);
					View.track.add(x);
				}
			} else if (pos == 23 + 25) {
				int xx = p.x;
				int yy = p.y;
				for (int j = 1; j <= 4; j++) {
					yy += sk;
					View.safeZoneCenter.get(2).add(new Pair(xx, yy));
					Circle x = new Circle(12, View.CellColour);
					x.setStroke(View.colOrder.get(2));
					x.setStrokeWidth(3);
					x.setCenterX(xx);
					x.setCenterY(yy);
					View.track.add(x);
				}
			} else if (pos == 23 + 25 + 25) {
				int xx = p.x;
				int yy = p.y;
				for (int j = 1; j <= 4; j++) {
					xx -= sk;
					View.safeZoneCenter.get(3).add(new Pair(xx, yy));
					Circle x = new Circle(12, View.CellColour);
					x.setStroke(View.colOrder.get(3));
					x.setStrokeWidth(3);
					x.setCenterX(xx);
					x.setCenterY(yy);
					View.track.add(x);
				}
			} else {
				int xx = p.x;
				int yy = p.y;
				for (int j = 1; j <= 4; j++) {
					yy -= sk;
					View.safeZoneCenter.get(0).add(new Pair(xx, yy));
					Circle x = new Circle(12, View.CellColour);
					x.setStroke(View.colOrder.get(0));
					x.setStrokeWidth(3);
					x.setCenterX(xx);
					x.setCenterY(yy);
					View.track.add(x);
				}
			}
		}
	}
	public static void createTrack(int startx, int starty, int sk) {
		supercell = new Image("/media/cell1.png");
		Circle s = new Circle(12, View.CellColour);

		starty -= sk;
		s.setCenterY(starty);
		s.setCenterX(startx);
		View.track.add(s);
		View.boardCircles.add(s);
		View.pointToIndex.put(new Pair(startx, starty),
				View.indexToPoint.size());
		View.indexToPoint.add(new Pair(startx, starty));
		int sqsk = (int) ((1 / Math.sqrt(2)) * sk);
		for (int i = 0; i < 8; i++) {
			Circle x = new Circle(12, View.CellColour);
			View.boardCircles.add(x);

			starty -= sk;
			x.setCenterY(starty);
			x.setCenterX(startx);
			View.track.add(x);
			View.pointToIndex.put(new Pair(startx, starty),
					View.indexToPoint.size());
			View.indexToPoint.add(new Pair(startx, starty));

		}

		for (int i = 0; i < 5; i++) {
			Circle x = new Circle(12, View.CellColour);
			View.boardCircles.add(x);

			startx -= sqsk;
			starty -= sqsk;
			x.setCenterY(starty);
			x.setCenterX(startx);
			View.track.add(x);
			View.pointToIndex.put(new Pair(startx, starty),
					View.indexToPoint.size());
			View.indexToPoint.add(new Pair(startx, starty));
		}
		for (int i = 0; i < 8; i++) {
			Circle x = new Circle(12, View.CellColour);
			startx -= sk;
			View.boardCircles.add(x);

			x.setCenterY(starty);
			x.setCenterX(startx);
			View.track.add(x);
			View.pointToIndex.put(new Pair(startx, starty),
					View.indexToPoint.size());
			View.indexToPoint.add(new Pair(startx, starty));
		}
		for (int i = 0; i < 4; i++) {
			Circle x = new Circle(12, View.CellColour);
			starty -= sk;
			View.boardCircles.add(x);

			x.setCenterY(starty);
			x.setCenterX(startx);
			View.track.add(x);
			View.pointToIndex.put(new Pair(startx, starty),
					View.indexToPoint.size());
			View.indexToPoint.add(new Pair(startx, starty));
		}
		for (int i = 0; i < 8; i++) {
			Circle x = new Circle(12, View.CellColour);
			startx += sk;
			View.boardCircles.add(x);

			x.setCenterY(starty);
			x.setCenterX(startx);
			View.track.add(x);
			View.pointToIndex.put(new Pair(startx, starty),
					View.indexToPoint.size());
			View.indexToPoint.add(new Pair(startx, starty));
		}
		for (int i = 0; i < 5; i++) {
			Circle x = new Circle(12, View.CellColour);
			View.boardCircles.add(x);

			startx += sqsk;
			starty -= sqsk;
			x.setCenterY(starty);
			x.setCenterX(startx);
			View.track.add(x);
			View.pointToIndex.put(new Pair(startx, starty),
					View.indexToPoint.size());
			View.indexToPoint.add(new Pair(startx, starty));
		}
		for (int i = 0; i < 8; i++) {
			Circle x = new Circle(12, View.CellColour);
			starty -= sk;
			View.boardCircles.add(x);

			x.setCenterY(starty);
			x.setCenterX(startx);
			View.track.add(x);
			View.pointToIndex.put(new Pair(startx, starty),
					View.indexToPoint.size());
			View.indexToPoint.add(new Pair(startx, starty));
		}
		for (int i = 0; i < 4; i++) {
			Circle x = new Circle(12, View.CellColour);
			startx += sk;
			View.boardCircles.add(x);

			x.setCenterY(starty);
			x.setCenterX(startx);
			View.track.add(x);
			View.pointToIndex.put(new Pair(startx, starty),
					View.indexToPoint.size());
			View.indexToPoint.add(new Pair(startx, starty));
		}
		for (int i = 0; i < 8; i++) {
			Circle x = new Circle(12, View.CellColour);
			starty += sk;
			View.boardCircles.add(x);

			x.setCenterY(starty);
			x.setCenterX(startx);
			View.track.add(x);
			View.pointToIndex.put(new Pair(startx, starty),
					View.indexToPoint.size());
			View.indexToPoint.add(new Pair(startx, starty));
		}
		for (int i = 0; i < 5; i++) {
			Circle x = new Circle(12, View.CellColour);
			View.boardCircles.add(x);

			startx += sqsk;
			starty += sqsk;
			x.setCenterY(starty);
			x.setCenterX(startx);
			View.track.add(x);
			View.pointToIndex.put(new Pair(startx, starty),
					View.indexToPoint.size());
			View.indexToPoint.add(new Pair(startx, starty));
		}
		for (int i = 0; i < 8; i++) {
			Circle x = new Circle(12, View.CellColour);
			startx += sk;
			View.boardCircles.add(x);

			x.setCenterY(starty);
			x.setCenterX(startx);
			View.track.add(x);
			View.pointToIndex.put(new Pair(startx, starty),
					View.indexToPoint.size());
			View.indexToPoint.add(new Pair(startx, starty));
		}
		for (int i = 0; i < 4; i++) {
			Circle x = new Circle(12, View.CellColour);
			starty += sk;
			View.boardCircles.add(x);

			x.setCenterY(starty);
			x.setCenterX(startx);
			View.track.add(x);
			View.pointToIndex.put(new Pair(startx, starty),
					View.indexToPoint.size());
			View.indexToPoint.add(new Pair(startx, starty));
		}
		for (int i = 0; i < 8; i++) {
			Circle x = new Circle(12, View.CellColour);
			startx -= sk;
			View.boardCircles.add(x);

			x.setCenterY(starty);
			x.setCenterX(startx);
			View.track.add(x);
			View.pointToIndex.put(new Pair(startx, starty),
					View.indexToPoint.size());
			View.indexToPoint.add(new Pair(startx, starty));
		}
		for (int i = 0; i < 5; i++) {
			Circle x = new Circle(12, View.CellColour);
			View.boardCircles.add(x);

			startx -= sqsk;
			starty += sqsk;
			x.setCenterY(starty);
			x.setCenterX(startx);
			View.track.add(x);
			View.pointToIndex.put(new Pair(startx, starty),
					View.indexToPoint.size());
			View.indexToPoint.add(new Pair(startx, starty));
		}
		for (int i = 0; i < 8; i++) {
			Circle x = new Circle(12, View.CellColour);
			View.boardCircles.add(x);

			starty += sk;

			x.setCenterY(starty);
			x.setCenterX(startx);
			View.track.add(x);
			View.pointToIndex.put(new Pair(startx, starty),
					View.indexToPoint.size());
			View.indexToPoint.add(new Pair(startx, starty));
		}
		for (int i = 0; i < 3; i++) {
			Circle x = new Circle(12, View.CellColour);
			View.boardCircles.add(x);

			startx -= sk;

			x.setCenterY(starty);
			x.setCenterX(startx);
			View.track.add(x);
			View.pointToIndex.put(new Pair(startx, starty),
					View.indexToPoint.size());
			View.indexToPoint.add(new Pair(startx, starty));
		}
		for(int i =0;i<View.boardCircles.size();i++){
			View.boardCircles.get(i).setStroke(Color.BLANCHEDALMOND);
		}
	}
}
