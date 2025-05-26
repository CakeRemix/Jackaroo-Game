package view;

import java.util.ArrayList;

import model.Colour;
import model.player.Marble;
import model.player.Player;
import engine.Game;
import engine.board.Board;
import engine.board.Cell;
import engine.board.SafeZone;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Screen;
import javafx.util.Duration;

public class updateCells {


	public static void update() {

		View.marblesPane.getChildren().clear();

		View.trackCells = View.game.getBoard().getTrack();
		View.safeZones = View.game.getBoard().getSafeZones();
		View.homeZones = new ArrayList<>();

		ArrayList<Player> players = View.game.getPlayers();
		for (int i = 0; i < players.size(); i++) {
			View.homeZones.add(players.get(i).getMarbles());
		}
		for (int i = 0; i < View.homeZones.size(); i++) {
			for (int j = 0; j < View.homeZones.get(i).size(); j++) {
				if(View.homeZones.get(i).get(j) == null) continue;
				Pair position = View.homeIndexToPoint.get(i).get(j);
				Circle circle = View.homeZones.get(i).get(j).marbleIcon;
				circle.setCenterX(position.x);
				circle.setCenterY(position.y);
				View.marblesPane.getChildren().add(circle);
			}
		}
		for (int i = 0; i < View.trackCells.size(); i++) {
			if (View.trackCells.get(i).getMarble() == null)
				continue;
			
			Circle circle = View.trackCells.get(i).getMarble().marbleIcon;
			Pair position = View.indexToPoint.get(i);
			circle.setCenterX(position.x);
			circle.setCenterY(position.y);
			View.marblesPane.getChildren().add(circle);
		}
		for(int i = 0; i < View.safeZones.size(); i++){
			for(int j = 0; j < View.safeZones.get(i).getCells().size(); j++){
				if(View.safeZones.get(i).getCells().get(j).getMarble() == null) continue;
				Pair position = View.safeZoneCenter.get(i).get(j);
				Circle circle = View.safeZones.get(i).getCells().get(j).getMarble().marbleIcon;
				circle.setCenterX(position.x);
				circle.setCenterY(position.y);
				View.marblesPane.getChildren().add(circle);
			}
		}
		
	}
	public static void minionMove(Colour colour, ArrayList<Pair> path, Circle marble) {
	    if (path.size() < 2) return;
	    Circle minion;
	    switch(colour){
	    case RED:
	    	minion = View.minionRed;
	    	break;
	    case GREEN:
	    	minion = View.minionGreen;
	    	break;
	    case BLUE:
	    	minion = View.minionBlue;
	    	break;
	    case YELLOW:
	    	minion = View.minionYellow;
	    	break;
	    default:minion = View.minionBlue;
	    }
	    minion.setTranslateX(0);
	    minion.setTranslateY(0);

	    minion.setCenterX(path.get(0).x);
	    minion.setCenterY(path.get(0).y);
	    minion.setVisible(true);

	    marble.setVisible(false);

	    SequentialTransition fullSequence = new SequentialTransition();

	    for (int i = 1; i < path.size(); i++) {
	        Pair from = path.get(i-1);
	        Pair to = path.get(i);
	        double dx = to.x - from.x;
	        double dy = to.y - from.y;
	     // Flip horizontally if moving to a point above the screen center
	        double centerY = View.HEIGHT / 2.0;
	        minion.setScaleX(to.y > centerY ? -1 : 1);

/*
	        double angleRad = Math.atan2(dy, dx);
	        double angleDeg = Math.toDegrees(angleRad);

	        // Rotate minion toward direction
	        RotateTransition rotate = new RotateTransition(Duration.millis(200), minion);
	        rotate.setToAngle(angleDeg);
*/
	        // Move minion
	        TranslateTransition move = new TranslateTransition(Duration.millis(200), minion);
	        move.setByX(dx);
	        move.setByY(dy);

	        // Marble position update (after move)
	        PauseTransition updateMarble = new PauseTransition(Duration.millis(200));
	        int finalI = i; // required for lambda expression
	        updateMarble.setOnFinished(e -> {
	        	if (finalI + 1 < path.size()) {
	        	    marble.setCenterX(path.get(finalI + 1).x);
	        	    marble.setCenterY(path.get(finalI + 1).y);
	        	}
	        });

	        fullSequence.getChildren().addAll(move, updateMarble);
	    }

	    PauseTransition showMarble = new PauseTransition(Duration.millis(100));
	    showMarble.setOnFinished(e -> {
	        minion.setVisible(false);
	        marble.setVisible(true);
	    });

	    fullSequence.getChildren().add(showMarble);

	    fullSequence.play();
	}


}