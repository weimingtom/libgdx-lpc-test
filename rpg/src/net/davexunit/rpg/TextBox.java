package net.davexunit.rpg;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Align;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.tablelayout.Table;

public class TextBox extends StyledTable
{
	private final Label label;
	//protected TextBoxStyle style;
	
	public TextBox() {
		this("");
	}
	
	public TextBox (String text) {
		this(text, getDefaultTexture());
	}

	public TextBox (String text, TableStyle style) {
		super(style);

		//this.style = style;
		this.label = new Label(text, new Label.LabelStyle(style.font, style.fontColor));
		
		label.x += this.style.padX;
		label.y -= this.style.padY;
		label.setAlignment(Align.TOP | Align.LEFT, Align.LEFT);
		label.setWrap(true);
	}
	
	public Label getLabel() {
		return label;
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		this.setBackground(style.background);
		validate();
		super.draw(batch, parentAlpha);
		/*label.x = style.padX;
		label.y = -style.padY;*/
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
	
	public void setStyle(TableStyle style) {
		this.style = style;
	}
	
	public void setXY(float x, float y) {
		setX(x);
		setY(y);
	}
	
	public void setX(float x) {
		this.x = x;
		this.label.x = x + this.style.padX; 
	}
	
	public void setY(float y) {
		this.y = y;
		this.label.y = y - this.style.padY;
	}
}
