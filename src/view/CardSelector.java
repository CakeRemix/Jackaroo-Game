package view;

import exception.GameException;
import exception.InvalidCardException;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import model.card.Card;
import model.player.*;
public class CardSelector {

	private static ImageView currentlySelected = null;
    
    public static void makeSelectable(ImageView iv) throws GameException{
    	  Card selected = ControlViewCards.inverted.get(iv);
          Player player = View.game.getPlayers().get(0);

         iv.setOnMouseClicked(e -> {
        	//View.players.get(0).canPlayTurn();
            if (e.getButton() != MouseButton.PRIMARY) return;
            
            if (iv == currentlySelected){
            	deselect(currentlySelected);
            	View.game.deselectAll();
            	//System.out.println(View.players.get(0).getSelectedCard());
            	return;
            }

            if (currentlySelected != null) {
                deselect(currentlySelected);
            }
            try{
            	//Backend
            player.selectCard(selected);
            //DiscardToFirePit.discardToFirePit(iv);
            select(iv);
            currentlySelected = iv;
            }catch(InvalidCardException zz){
            	StartBoard.displayAlert("Invalid Card", zz.getMessage());
            }
        });

        iv.setOnMouseEntered(e -> {
            if (iv != currentlySelected) {
                iv.setEffect(new DropShadow(10, Color.rgb(0,170,255,0.8)));
                iv.setScaleX(1.1);
                iv.setScaleY(1.1);
            }
        });

        iv.setOnMouseExited(e -> {
            if (iv != currentlySelected) {
                iv.setEffect(null);
                iv.setScaleX(1);
                iv.setScaleY(1);
            }
        });
    }

    private static void select(ImageView iv) {
        iv.setEffect(new DropShadow(20, Color.GOLD));
        iv.setScaleX(1.1);
        iv.setScaleY(1.1);
    }

    private static void deselect(ImageView iv) {
    	currentlySelected = null;
        iv.setEffect(null);
        iv.setScaleX(1);
        iv.setScaleY(1);
    }
}
