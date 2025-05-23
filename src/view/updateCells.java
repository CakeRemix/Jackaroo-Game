package view;

import java.util.ArrayList;

import model.player.Marble;
import model.player.Player;
import engine.Game;
import engine.board.Board;
import engine.board.Cell;
import engine.board.SafeZone;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

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

}