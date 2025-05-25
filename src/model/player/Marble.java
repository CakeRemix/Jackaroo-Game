package model.player;

import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import model.Colour;


public class Marble {

    private final Colour colour;
    public  Circle marbleIcon;
    
    public Marble(Colour colour) {
        this.colour = colour;
        String path = "";
        switch(colour){
	        case RED: path = "red";break;
	        case GREEN: path = "green";break;
	        case BLUE: path = "blue";break;
	        case YELLOW: path = "yellow";break;
        }
        Image image = new Image("/stones/Gemstone_"+path+".png");
        marbleIcon = createMarble(getBaseColor(colour));
		marbleIcon.setFill(new ImagePattern(image));

    }
 public Colour getColour() {
        return colour;
    }

   

    // ─── EXTRAS ───────────────────────────────────────────────────────────────
 public Circle getIcon() {
        return marbleIcon;
    }

    public Color getBaseColor(Colour c) {
        return Color.valueOf(c.toString());
    }

    public Circle createMarble(Color baseColor) {
    	double radius = 20;
       

        Circle marble = new Circle(radius);
        DropShadow glow = new DropShadow();
        glow.setColor(baseColor.deriveColor(0, 1.0, 1.5, 0.8));
        glow.setRadius(12);
        glow.setSpread(0.5);
        marble.setOnMouseEntered(e -> marble.setEffect(glow));
        marble.setOnMouseExited(e  -> marble.setEffect(null));

        return marble;
    }
}