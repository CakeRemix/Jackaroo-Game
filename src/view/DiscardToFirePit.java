package view;

import java.util.ArrayList;

import model.Colour;
import model.card.Card;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Bounds;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class DiscardToFirePit {

	public static void discardToFirePit(ImageView iv) {
		// iv.translateXProperty();
		// iv.setTranslateY(-1000);
		ArrayList<Card> firePit = View.game.getFirePit();
		ImageView vi = ControlViewCards.cards
				.get(firePit.get(firePit.size() - 1));
		discardToFirePit2(vi);

		vi.setEffect(null);
		vi.setMouseTransparent(true);
	}

	public static void discardToFirePit2(ImageView cardView) {
		// Get target coordinates (must be absolute in the same parent)
		double fireX = View.firePit.getLayoutX();
		double fireY = View.firePit.getLayoutY();

		// Animate directly to fire pit position
		TranslateTransition move = new TranslateTransition(
				Duration.seconds(0.7), cardView);
		System.out.println(fireY);
		
		move.setToX(320);
		move.setToY(-fireY);
		// move.setInterpolator(Interpolator.EASE_BOTH);

		RotateTransition rotate = new RotateTransition(Duration.seconds(0.7),
				cardView);
		rotate.setByAngle(360);

		ScaleTransition scale = new ScaleTransition(Duration.seconds(0.7),
				cardView);
		scale.setToX(0.9);
		scale.setToY(0.9);
		int index = View.game.getCurrentPlayerIndex1();
		ParallelTransition animation = new ParallelTransition(move, rotate,
				scale);
		animation.setOnFinished(e -> {
			// Reset transforms (optional)
				cardView.setTranslateX(0);
				cardView.setTranslateY(0);
				cardView.setRotate(0);
				cardView.setScaleX(1.0);
				cardView.setScaleY(1.0);
				View.firePit.getChildren().clear();
				View.firePit.getChildren().add(cardView);
			});

		animation.play();
	}

	public static void animateCardToFirepit3(ImageView card, StackPane firepit) {
		// Get scene coordinates for both card and firepit
		Bounds cardBounds = card.localToScene(card.getBoundsInLocal());
		Bounds firepitBounds = firepit.localToScene(firepit.getBoundsInLocal());

		// Calculate movement difference
		double targetX = firepitBounds.getMinX() - cardBounds.getMinX();
		double targetY = firepitBounds.getMinY() - cardBounds.getMinY();

		// Create transitions
		TranslateTransition translate = new TranslateTransition(
				Duration.seconds(0.8), card);
		translate.setByX(targetX);
		translate.setByY(targetY);
		translate.setInterpolator(Interpolator.EASE_BOTH);

		RotateTransition rotate = new RotateTransition(Duration.seconds(0.8),
				card);
		rotate.setByAngle(360);

		ScaleTransition scale = new ScaleTransition(Duration.seconds(0.8), card);
		scale.setToX(0.9);
		scale.setToY(0.9);

		ParallelTransition animation = new ParallelTransition(translate,
				rotate, scale);
		animation.setOnFinished(e -> {
			// Optional: reset transform to make future animations clean
				card.setTranslateX(0);
				card.setTranslateY(0);
				card.setRotate(0);
				card.setScaleX(1.0);
				card.setScaleY(1.0);

				firepit.getChildren().clear();
				firepit.getChildren().add(card);
			});

		animation.play();
	}
}
