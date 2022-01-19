package ecs.components.graphics.drawables;

import java.util.LinkedList;
import java.util.List;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Text extends Drawable {

	private Label text = null;

	public Text() {
		this.text = new Label("EMPTY TEXT");
		this.text.setFont(new Font(4));
		this.text.setTextFill(Color.BLACK);
		this.text.setMouseTransparent(true);
	}

	public Text(String string) {
		this();
		this.text.setText(string);

	}

	public void setText(String s) {
		this.text.setText(s);
		if (this.entity != null) {
			this.notifyGraphics();
		}
	}

	@Override
	public List<Node> getShapes() {
		LinkedList<Node> retList = new LinkedList<>();
		retList.add(text);
		return retList;
	}

	@Override
	public void update(float deltaT) {
		this.text.setTranslateX(-this.text.getWidth() / 2);
		this.text.setTranslateY(-this.text.getHeight() / 2);
	}

}
