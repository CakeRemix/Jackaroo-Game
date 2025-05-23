package view;

import model.player.Marble;
import model.player.Player;

public class MarbleSelector {
	public static void selectMarble(Marble marbleSelectable) {
		double scale = 1.2;

		marbleSelectable.marbleIcon.setOnMouseClicked(e -> {
			marbleSelectable.marbleIcon.setScaleX(scale);
			marbleSelectable.marbleIcon.setScaleY(scale);

			try {
				View.player.selectMarble(marbleSelectable);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				System.out.println(e1.toString());
				View.player.deselectAll();
				
			}
		});

	}
}