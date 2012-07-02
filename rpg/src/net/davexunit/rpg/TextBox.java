package net.davexunit.rpg;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Align;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.tablelayout.Table;

public class TextBox extends Table 
{
	private final Label label;
	private TextBoxStyle style;
	
	public TextBox (String text, TextBoxStyle style) {
		super();

		this.style = style;
		this.label = new Label(text, new Label.LabelStyle(style.font, style.fontColor));
		
		label.setAlignment(Align.TOP | Align.LEFT, Align.LEFT);
		label.setWrap(true);
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		this.setBackground(style.background);
		validate();
		super.draw(batch, parentAlpha);
		label.x = style.padX;
		label.y = -style.padY;
		label.width = width - style.padX;
		label.height = height - style.padY;
		label.draw(batch, parentAlpha);
	}

	@Override
	public Actor hit(float x, float y) {
		return null;
	}
	
	public void setText(String text) {
		label.setText(text);
	}
	
	public static class TextBoxStyle {
		public NinePatch background;
		public BitmapFont font;
		public Color fontColor;
		public int padX;
		public int padY;
		
		public TextBoxStyle() {
			this.background = null;
			this.font = null;
			this.fontColor = null;
			this.padX = 0;
			this.padY = 0;
		}
	}
}
