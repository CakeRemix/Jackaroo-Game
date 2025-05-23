package view;

import java.io.IOException;
import java.util.*;

import model.Colour;
import model.card.*;
import model.card.standard.Ace;
import model.card.standard.Five;
import model.card.standard.Four;
import model.card.standard.Jack;
import model.card.standard.King;
import model.card.standard.Queen;
import model.card.standard.Seven;
import model.card.standard.Standard;
import model.card.standard.Suit;
import model.card.standard.Ten;
import model.card.wild.Burner;
import model.card.wild.Saver;
import model.card.wild.Wild;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class ControlViewCards {
	
	public static Map<Card, ImageView> cards = new HashMap<>();
	public static Map<ImageView,Card> inverted = new HashMap<>();

	/*
	public static ImageView viewCard(Card card){
		String code = card.getImageCode();
		
		Image img = cards.get(code);
		ImageView iv = new ImageView(img);
		iv.setFitWidth(100);
		iv.setPreserveRatio(true);
		return iv;
	}
	*/
	
	public static ImageView getBackCard(String path){
		return new ImageView(new Image("/cards/back_card_"+path+".png"));
	}
	
	public static void loadFromResources() throws IOException{
		
		ArrayList<Card> cardsPool = Deck.getCardsPool2();
		for(int i =0;i<cardsPool.size();i++){
			Card card = cardsPool.get(i);
			String path = card.getImageCode();
			ImageView cardImageView = new ImageView(new Image("/cards/"+path+".png"));
			cardImageView.setSmooth(true);
			
			cards.put(card, cardImageView);
		}
		for(int i = 0;i<View.player.getHand().size();i++){
			ImageView iv = new ImageView(new Image("/cards/"+View.player.getHand().get(i).getImageCode()+".png"));
			iv.setSmooth(true);
			cards.put(View.player.getHand().get(i),iv);
		}
		for(int i = 0;i<View.players.get(1).getHand().size();i++){
			ImageView iv1 = new ImageView(new Image("/cards/"+View.players.get(1).getHand().get(i).getImageCode()+".png"));
			iv1.setSmooth(true);
			cards.put(View.players.get(1).getHand().get(i),iv1);
		}
		for(int i = 0;i<View.players.get(2).getHand().size();i++){
			ImageView iv2 = new ImageView(new Image("/cards/"+View.players.get(2).getHand().get(i).getImageCode()+".png"));
			iv2.setSmooth(true);
			cards.put(View.players.get(2).getHand().get(i),iv2);
		
		}
		for(int i = 0;i<View.players.get(3).getHand().size();i++){
			ImageView iv3 = new ImageView(new Image("/cards/"+View.players.get(3).getHand().get(i).getImageCode()+".png"));
			iv3.setSmooth(true);
			cards.put(View.players.get(3).getHand().get(i),iv3);
		}
		
		for (Map.Entry<Card,ImageView> e : cards.entrySet()) {
			inverted.put(e.getValue(), e.getKey());
		}
	}
	public static void main(String[] args) throws IOException{
		//ControlViewCards.loadFromResources();
	}
}
