package view;

import java.util.ArrayList;

import model.card.Card;
import javafx.scene.image.ImageView;

public class DiscardToFirePit {
	
	public static void discardToFirePit(ImageView iv){
		//iv.translateXProperty();
		//iv.setTranslateY(-1000);
		ArrayList<Card> firePit = View.game.getFirePit();
		ImageView vi = ControlViewCards.cards.get(firePit.get(firePit.size()-1));
		
		View.firePit.getChildren().clear();
		View.firePit.getChildren().add(vi);
		vi.setEffect(null);
		vi.setMouseTransparent(true);
	}
}
